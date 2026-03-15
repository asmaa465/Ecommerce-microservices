package com.Shop_Microservice.Shop.Feigns;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@FeignClient(name="INVENTORY")
public interface InventoryClient {
    @PostMapping("/inventory/isAvailable/{id}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable int id);

    @PutMapping("/inventory/reduce/{productId}")
    public ResponseEntity<?> reduceStock(@PathVariable int productId, @RequestParam int quantity) ;
    @GetMapping("/inventory/ProductQuantity/{productId}")
    public double GetQuantityofProduct(@PathVariable int productId);

    @GetMapping("/inventory/products/{id}/price")
    public double fetchProductPriceById(@PathVariable int id);

}
