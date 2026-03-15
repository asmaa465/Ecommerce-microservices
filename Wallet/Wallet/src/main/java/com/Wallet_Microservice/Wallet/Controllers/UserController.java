package com.Wallet_Microservice.Wallet.Controllers;


import com.Wallet_Microservice.Wallet.Entities.User;
import com.Wallet_Microservice.Wallet.Repositories.UserRepository;
import com.Wallet_Microservice.Wallet.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Wallet_Microservice.Wallet.JWT.*;

@RestController
@RequestMapping("wallet/User")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  JwtUtil jwt;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping(path="/user/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userServices.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error message :"+ex.getMessage());
        }

    }

    @PostMapping("/user/login")
    public ResponseEntity<?> Login(@RequestParam String email, @RequestParam String password) {
        if(userServices.check_Login(email, password)){
              String token = jwt.generateToken(email);
              return ResponseEntity.status(HttpStatus.OK).body(token);
        }else{
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("error login");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        try{
            User user =userServices.getUserbyId(id);
            return ResponseEntity.ok(user);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().body("error message :"+ex.getMessage());
        }
    }

    @GetMapping("/usercheck/{id}")
    public ResponseEntity<?> check_exist_user(@PathVariable int id) {
        try{
            User user =userServices.getUserbyId(id);
            return ResponseEntity.ok(true);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().body("error message :"+ex.getMessage());
        }
    }

}
