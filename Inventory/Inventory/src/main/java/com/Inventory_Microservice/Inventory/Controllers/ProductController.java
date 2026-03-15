package com.Inventory_Microservice.Inventory.Controllers;


import com.Inventory_Microservice.Inventory.Entities.Product;
import com.Inventory_Microservice.Inventory.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/inventory/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/create")
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) {
        try {
            productService.AddProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Product>> fetchAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id.intValue());
            return ResponseEntity.ok(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> modifyProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            product.setId(id.intValue());
            productService.updateProduct(product);
            return ResponseEntity.ok("Product updated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found to update");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id.intValue());
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found to delete");
        }
    }

    @GetMapping("/{id}/price")
    public double fetchProductPriceById(@PathVariable int id) {
       Optional<Product>product= Optional.ofNullable(productService.getProductById(id));
        if (product.isPresent()) {
            return product.get().getPrice();
        }else{
            throw new RuntimeException("product not found");
        }
    }



}
