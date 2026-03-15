package com.Shop_Microservice.Shop.Repositories;

import com.Shop_Microservice.Shop.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
