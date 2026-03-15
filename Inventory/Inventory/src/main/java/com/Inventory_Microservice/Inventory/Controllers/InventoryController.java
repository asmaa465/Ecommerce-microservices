package com.Inventory_Microservice.Inventory.Controllers;

import com.Inventory_Microservice.Inventory.Entities.Inventory;
import com.Inventory_Microservice.Inventory.Entities.Product;
import com.Inventory_Microservice.Inventory.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/CreateInventory")
    public ResponseEntity<?> addInventory(@RequestParam int productId, @RequestParam int quantity) {
        try {
            inventoryService.createInventory(productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Inventory created successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }



    @GetMapping
    public ResponseEntity<List<Inventory>> fetchAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> fetchInventoryById(@PathVariable int id) {
        try {
            Inventory inventory = inventoryService.getInventoryById(id);
            return ResponseEntity.ok(inventory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeInventory(@PathVariable int id) {
        try {
            inventoryService.deleteInventoryById(id);
            return ResponseEntity.ok("Inventory deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


   
    @PostMapping("/isAvailable/{id}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable int id) {
        Boolean available = inventoryService.IsProductAvailable(id);
        return ResponseEntity.ok(available);
    }


    @PutMapping("/reduce/{productId}")
    public ResponseEntity<?> reduceStock(@PathVariable int productId, @RequestParam int quantity) {
        try {
            inventoryService.reduceStock(productId, quantity);
            return ResponseEntity.ok("Stock reduced successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/ProductQuantity/{productId}")
    public double GetQuantityofProduct(@PathVariable int productId) {
            double qty = inventoryService.getProductQuantity(productId);
            return qty;
    }



    @PutMapping("/{inventoryId}/setQuantity/{quantity}")
    public ResponseEntity<String> setProductQuantity(@PathVariable int inventoryId, @PathVariable int quantity) {
        String result = inventoryService.SetProductQuantity(inventoryId, quantity);
        if (result.equals("failed")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }


}
