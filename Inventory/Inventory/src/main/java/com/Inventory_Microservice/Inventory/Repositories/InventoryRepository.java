package com.Inventory_Microservice.Inventory.Repositories;

import com.Inventory_Microservice.Inventory.Entities.Inventory;
import com.Inventory_Microservice.Inventory.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
           Optional<Inventory> findByProductId(int product_id);
}
