package com.Wallet_Microservice.Wallet.Controllers;



import com.Wallet_Microservice.Wallet.Entities.Transaction;
import com.Wallet_Microservice.Wallet.Entities.Wallet;
import com.Wallet_Microservice.Wallet.Services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(path="/{id}/WalletCreation")
    public ResponseEntity<String> WalletCreation(@PathVariable int id, @RequestBody Wallet wallet) {
        try {
            if (walletService.add_wallet(id, wallet)) {
                return ResponseEntity.ok("Wallet added successfully");
            } else {
                return ResponseEntity.badRequest().body("Wallet not added (user missing or already has a wallet)");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping(path="/{id}/Withdrawal/{amount}")
    public void Withdraw(@PathVariable int id, @PathVariable double amount) {
        walletService.withdrawal_op(id,amount);
    }
    @PostMapping(path="/{id}/Deposit/{amount}")
    public void deposit(@PathVariable int id, @PathVariable double amount) {
        walletService.Deposit_op(id,amount);
    }
    @GetMapping(path="/{id}/Transaction")
    public List<Transaction> Transactions(@PathVariable int id) {
       return  walletService.display_transactions(id);
    }
   @PostMapping("/{id}/pay/{amount}")
    public boolean pay(@PathVariable int id, @PathVariable double amount) {
        return walletService.pay(id, amount);
   }


}
