package com.Shop_Microservice.Shop.Repositories;

import com.Shop_Microservice.Shop.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
        List<Order> findByuserId(int userId);
        Optional<Order> findByorderID(int orderID);
}
