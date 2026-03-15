package com.Inventory_Microservice.Inventory.Services;

import com.Inventory_Microservice.Inventory.Entities.Product;
import com.Inventory_Microservice.Inventory.Repositories.ProductRepository;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public String AddProduct(Product product) {
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (productOptional.isPresent()) {
              throw new IllegalStateException("Product already exists");
        } else {
            productRepository.save(product);
            return "Product added successfully";
        }
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
   public String updateProduct(Product product) {
        Product productToUpdate = productRepository.findById(product.getId()).get();
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productRepository.save(productToUpdate);
        return "product updated successfully";
   }

  public double getProductPrice(int id) {
        return productRepository.findById(id).get().getPrice();
  }

}
