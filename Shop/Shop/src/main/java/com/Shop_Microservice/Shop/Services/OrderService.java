package com.Shop_Microservice.Shop.Services;

import com.Shop_Microservice.Shop.Entities.CartItem;
import com.Shop_Microservice.Shop.Entities.Order;
import com.Shop_Microservice.Shop.Entities.OrderItem;
import com.Shop_Microservice.Shop.Entities.ShoppingCart;
import com.Shop_Microservice.Shop.Feigns.InventoryClient;
import com.Shop_Microservice.Shop.Feigns.WalletClient;
import com.Shop_Microservice.Shop.Repositories.OrderItemRepository;
import com.Shop_Microservice.Shop.Repositories.OrderRepository;
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
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private WalletClient walletClient;
    @Autowired
    private InventoryClient inventoryClient;




    @Transactional
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
    @Retry(name = "inventoryService")
    @Bulkhead(name = "inventoryService", type = Bulkhead.Type.SEMAPHORE)
    public Boolean addOrderItem(int orderId, int quantity, int productId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return false;
        }
        Order order = optionalOrder.get();
        double productprice=inventoryClient.fetchProductPriceById(productId);
        OrderItem orderItem = new OrderItem(quantity, productId, productprice);
        orderItem.setOrder(order);
        order.getOrderItemList().add(orderItem);
        order.setTotalPrice(order.getTotalPrice()+productprice);
        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        return true;
    }

    public Boolean removeOrderItem(int orderId, int orderItemId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return false;
        }
        Order order = orderOptional.get();
        OrderItem itemToRemove = order.getOrderItemList()
                .stream()
                .filter(item -> item.getItemId()== orderItemId)
                .findFirst()
                .orElse(null);

        if (itemToRemove == null) {
            return false;
        }

        order.getOrderItemList().remove(itemToRemove);
        itemToRemove.setOrder(null);
        orderRepository.save(order);
        orderItemRepository.delete(itemToRemove);
        return true;
    }

    @Transactional
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
    @Retry(name = "inventoryService")
    @Bulkhead(name = "inventoryService", type = Bulkhead.Type.SEMAPHORE)
    public Boolean CreateOrderFromCart(int shoppingCartId) {
        Optional<ShoppingCart>shoppingCart=shoppingCartRepository.findById(shoppingCartId);
        if (!shoppingCart.isPresent()) {
            throw new RuntimeException("no shopping cart found");
        }

        List<CartItem>items=shoppingCart.get().getCartItems();
        List<OrderItem>orderItems=new ArrayList<>();
        double totalPrice=0;
        int userId=shoppingCart.get().getUserId();

        Order order=new Order(userId,"pending",totalPrice,orderItems);
        for(CartItem cartItem:items){
            double price = inventoryClient.fetchProductPriceById(cartItem.getProductid());
            OrderItem orderItem=new OrderItem(cartItem.getQuantity(),cartItem.getProductid(),price);
            orderItems.add(orderItem);
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            totalPrice += price * cartItem.getQuantity();

        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return true;

    }
    @Transactional
    @CircuitBreaker(name = "walletService", fallbackMethod = "walletFallback")
    @Retry(name = "walletService")
    @Bulkhead(name = "walletService", type = Bulkhead.Type.SEMAPHORE)
    public Boolean PayOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("no order found");
        }

        Order order = orderOptional.get();
        if ("paid".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order is already paid");
        }

        int userId=order.getUserId();

        boolean paymentSuccess = walletClient.pay(userId,order.getTotalPrice());

        if (paymentSuccess) {
            order.setStatus("paid");
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public double inventoryFallback(int productId, Throwable t) {
        System.out.println("Inventory service is unavailable: " + t.getMessage());
        return 0.0;
    }

    public Boolean walletFallback(int orderId, Throwable t) {
        System.out.println("Wallet service is unavailable: " + t.getMessage());

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("payment_failed");
            orderRepository.save(order);
        }

        return false;
    }



}








