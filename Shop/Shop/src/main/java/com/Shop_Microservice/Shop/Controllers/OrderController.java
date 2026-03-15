package com.Shop_Microservice.Shop.Controllers;

import com.Shop_Microservice.Shop.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;




    @PostMapping("/{orderId}/add-item")
    public ResponseEntity<?> addOrderItem(
            @PathVariable int orderId,
            @RequestParam int quantity,
            @RequestParam int productId) {
        boolean result = orderService.addOrderItem(orderId, quantity, productId);
        return result ? ResponseEntity.ok("Item added successfully")
                : ResponseEntity.badRequest().body("Order not found");
    }

    @DeleteMapping("/{orderId}/remove-item/{orderItemId}")
    public ResponseEntity<?> removeOrderItem(
            @PathVariable int orderId,
            @PathVariable int orderItemId) {
        boolean result = orderService.removeOrderItem(orderId, orderItemId);
        return result ? ResponseEntity.ok("Item removed successfully")
                : ResponseEntity.badRequest().body("Item or order not found");
    }


    @PostMapping("/from-cart/{shoppingCartId}")
    public ResponseEntity<?> createOrderFromCart(@PathVariable int shoppingCartId) {
        try {
            boolean result = orderService.CreateOrderFromCart(shoppingCartId);
            return result ? ResponseEntity.ok("Order created from cart successfully")
                    : ResponseEntity.badRequest().body("Could not create order from cart");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<?> payOrder(@PathVariable int orderId) {
        try {
            boolean result = orderService.PayOrder(orderId);
            return result ? ResponseEntity.ok("Payment successful")
                    : ResponseEntity.badRequest().body("Payment failed");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

