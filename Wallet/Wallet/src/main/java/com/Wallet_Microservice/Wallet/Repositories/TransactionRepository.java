package com.Wallet_Microservice.Wallet.Repositories;

import com.Wallet_Microservice.Wallet.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findBywalletId(Long id);
}
