package com.Wallet_Microservice.Wallet.Services;

import com.Wallet_Microservice.Wallet.Entities.Transaction;
import com.Wallet_Microservice.Wallet.Entities.User;
import com.Wallet_Microservice.Wallet.Entities.Wallet;
import com.Wallet_Microservice.Wallet.Repositories.UserRepository;
import com.Wallet_Microservice.Wallet.Repositories.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;



    public boolean add_wallet(Integer user_id, Wallet wallet) {
        Optional<User> user_exist=userRepository.findById(user_id);
        if(user_exist.isEmpty()){
           return false;
        }
        User user=user_exist.get();
        if(user.getWallet()!=null) {
           return false;
        }
        wallet.setOwner(user);

        walletRepository.save(wallet);
         return true;

    }

    @Transactional
    public void withdrawal_op(int user_id, double amount) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Wallet wallet = Optional.ofNullable(user.getWallet())
                .orElseThrow(() -> new IllegalStateException("Wallet not found"));

        if (wallet.getAmount() < amount) {
            throw new IllegalStateException("Not enough balance");
        }

        wallet.setAmount(wallet.getAmount() - amount);

        Transaction transaction = new Transaction(amount, "WITHDRAW", wallet);
        wallet.getTransactions().add(transaction);

        walletRepository.save(wallet);

    }

    @Transactional
    public void Deposit_op(Integer user_id, double amount) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Wallet wallet = user.getWallet();
        if (wallet == null) {
            wallet = new Wallet(user, 0.0);
            user.setWallet(wallet);
        }

        wallet.setAmount(wallet.getAmount() + amount);

        Transaction transaction = new Transaction(amount, "DEPOSIT", wallet);
        wallet.getTransactions().add(transaction);

        walletRepository.save(wallet);



    }

    public List<Transaction> display_transactions(Integer user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Wallet wallet = Optional.ofNullable(user.getWallet())
                .orElseThrow(() -> new IllegalStateException("Wallet not found"));

        return wallet.getTransactions();
    }
    public void DeleteWallet(int user_id  ) {
        Optional<User> user_exist=userRepository.findById(user_id);
        if(!user_exist.isEmpty()){
            User user=user_exist.get();
            Optional<Wallet> wallet=walletRepository.findByOwner(user);
            if(wallet.isPresent()){
                walletRepository.delete(wallet.get());
            }
        }
    }

    public Boolean pay(int user_id, double amount) {
        try {
            withdrawal_op(user_id, amount);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
