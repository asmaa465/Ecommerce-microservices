package com.Shop_Microservice.Shop.Repositories;

import com.Shop_Microservice.Shop.Entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

}
