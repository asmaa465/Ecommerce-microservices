package com.Shop_Microservice.Shop.Feigns;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="WALLET")
public interface WalletClient {

    @PostMapping(path="/api/{id}/Deposit/{amount}")
    public void deposit(@PathVariable int id, @PathVariable double amount);

    @PostMapping("/wallet/wallet/{id}/pay/{amount}")
    public boolean pay(@PathVariable int id, @PathVariable double amount);

    @GetMapping("/wallet/User/usercheck/{id}")
    public ResponseEntity<?> check_exist_user(@PathVariable int id);

}
