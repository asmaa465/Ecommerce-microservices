package com.Shop_Microservice.Shop.Controllers;

import com.Shop_Microservice.Shop.Entities.ShoppingCart;
import com.Shop_Microservice.Shop.Services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        List<ShoppingCart> carts = shoppingCartService.getAllShoppingCart();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable int id) {
        ShoppingCart cart = shoppingCartService.getShoppingCartById(id);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createShoppingCart(@PathVariable int userId) {
        String result = shoppingCartService.addShoppingCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result); // 201 CREATED
    }

    @DeleteMapping("/{shoppingCartId}")
    public ResponseEntity<String> removeShoppingCart(@PathVariable int shoppingCartId) {
        String result = shoppingCartService.removeShoppingCart(shoppingCartId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{shoppingCartId}/add-item")
    public ResponseEntity<String> addCartItem(
            @PathVariable int shoppingCartId,
            @RequestParam int productId,
            @RequestParam int quantity) {
        String result = shoppingCartService.AddCartItem(shoppingCartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(result); // 201 CREATED
    }

    @DeleteMapping("/{shoppingCartId}/remove-item/{cartItemId}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable int shoppingCartId,
            @PathVariable int cartItemId) {
        String result = shoppingCartService.removeCartItem(shoppingCartId, cartItemId);
        return ResponseEntity.ok(result);
    }
}

