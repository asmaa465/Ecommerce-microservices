package com.Shop_Microservice.Shop.Repositories;

import com.Shop_Microservice.Shop.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
