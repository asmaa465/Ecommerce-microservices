package com.Shop_Microservice.Shop.Services;


import com.Shop_Microservice.Shop.Entities.CartItem;
import com.Shop_Microservice.Shop.Entities.ShoppingCart;
import com.Shop_Microservice.Shop.Feigns.InventoryClient;
import com.Shop_Microservice.Shop.Feigns.WalletClient;
import com.Shop_Microservice.Shop.Repositories.CartItemRepository;
import com.Shop_Microservice.Shop.Repositories.ShoppingCartRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
      @Autowired
      private ShoppingCartRepository shoppingCartRepository;
      @Autowired
      private InventoryClient inventoryClient;
      @Autowired
      private WalletClient walletClient;



      public ShoppingCart getShoppingCartById(int id) {
         Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(id);
         return shoppingCart.orElse(null);
      }
      public List<ShoppingCart> getAllShoppingCart() {
          return shoppingCartRepository.findAll();
      }

      @Transactional
      public String addShoppingCart(int userId) {
          List<CartItem>newCartItems = new ArrayList<>();
          ResponseEntity<?>check=walletClient.check_exist_user(userId);
          if (check.getStatusCode().is2xxSuccessful())
           {
              ShoppingCart shoppingcart = new ShoppingCart(userId, newCartItems);
              shoppingCartRepository.save(shoppingcart);
              return "created successfully";
          }
          return "creat failed";
      }
      public String removeShoppingCart(int shoppingCartId) {
          shoppingCartRepository.deleteById(shoppingCartId);
          return "deleted successfully";
      }

     public String removeCartItem(int shoppingCartId,int cartItemId) {
          Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(shoppingCartId);
         shoppingCart.get().getCartItems()
                 .removeIf(item -> item.getId() == cartItemId);
         shoppingCartRepository.save(shoppingCart.get());
          return "removed successfully";
     }


    @Transactional
    @CircuitBreaker(name="inventoryService", fallbackMethod = "addCartItemFallback")
    @Retry(name="inventoryService")
    @Bulkhead(name = "inventoryService", type = Bulkhead.Type.SEMAPHORE)
    public String AddCartItem(int shoppingCartId, int productId, int quantity) {
        double real_quantity = inventoryClient.GetQuantityofProduct(productId);
        if(real_quantity < quantity) {
            throw new RuntimeException("product not enough");
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));


        CartItem cartItem = new CartItem(quantity,productId);
        cartItem.setShoppingCart(shoppingCart);


        shoppingCart.getCartItems().add(cartItem);



        shoppingCartRepository.save(shoppingCart);

        return "added successfully";
    }

    public String addCartItemFallback(int shoppingCartId, int productId, int quantity, Throwable t) {
        t.printStackTrace();
        return "Could not verify inventory, please try again later";
    }







}
