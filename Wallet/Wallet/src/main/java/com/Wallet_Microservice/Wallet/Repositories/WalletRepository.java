package com.Wallet_Microservice.Wallet.Repositories;

import com.Wallet_Microservice.Wallet.Entities.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Wallet_Microservice.Wallet.Entities.User;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
       Optional<Wallet> findById(int id);
       Optional<Wallet> findByOwner(User owner);



}

