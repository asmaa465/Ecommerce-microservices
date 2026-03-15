package com.Inventory_Microservice.Inventory.Services;


import com.Inventory_Microservice.Inventory.Entities.Inventory;
import com.Inventory_Microservice.Inventory.Entities.Product;
import com.Inventory_Microservice.Inventory.Repositories.InventoryRepository;
import com.Inventory_Microservice.Inventory.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;


    public void createInventory(int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));

        Optional<Inventory> inventory1 = inventoryRepository.findByProductId(productId);
        if (inventory1.isPresent()) {
            throw new IllegalStateException("Inventory already exists for this product");
        }

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(quantity);
        inventory.setName("Default store");
        inventoryRepository.save(inventory);
    }


    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(int id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("there is Inventory that does not exist"));

    }

    public void deleteInventoryById(int id) {
        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException("Inventory not found");
        }
        inventoryRepository.deleteById(id);
    }


    public Boolean IsProductAvailable(int product_id) {
        Optional<Inventory> inventory1 = inventoryRepository.findByProductId(product_id);
        return inventory1.map(inv -> inv.getQuantity() > 0).orElse(false);
    }

    public void reduceStock(int productId, int quantity) {
        Optional<Inventory> inventory1 = inventoryRepository.findByProductId(productId);
        if (inventory1.isPresent()) {
            Inventory inventory = inventory1.get();
            if (inventory.getQuantity() > 0) {
                if (inventory.getQuantity() - quantity >= 0) {
                    inventory1.get().setQuantity(inventory.getQuantity() - quantity);
                    inventoryRepository.save(inventory1.get());
                } else {
                    throw new IllegalStateException("not enough stock");

                }
            } else {
                throw new IllegalStateException("product not enough");
            }

        } else {
            throw new IllegalStateException("inventory is not available");
        }
    }

    public double getProductQuantity(int productId) {
        Optional<Inventory> inventory1 = inventoryRepository.findByProductId(productId);
        if (inventory1.isPresent()) {
            return inventory1.get().getQuantity();
        } else {
            throw new IllegalStateException("product is not available");
        }
    }



    public String SetProductQuantity(int productId, int quantity) {
        Optional<Inventory> inventory1 = inventoryRepository.findById(productId);
        if (inventory1.isPresent()) {
            inventory1.get().setQuantity(quantity);
            inventoryRepository.save(inventory1.get());
            return "updated  product successfully";

        }
        return "failed";

    }

}







