package com.Inventory_Microservice.Inventory.Repositories;

import com.Inventory_Microservice.Inventory.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
      Optional<Product> findByname(String name);

}
