package com.Wallet_Microservice.Wallet.Services;

import com.Wallet_Microservice.Wallet.Entities.User;
import com.Wallet_Microservice.Wallet.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    public void register(User user) {
        Optional<User> user1=userRepository.findByEmail(user.getEmail());
        if(user1.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean check_Login(String email, String password) {
        if(encoder.matches(password, userRepository.findByEmail(email).get().getPassword())) {
            return true;
        }

        return false;
    }

    public User getUserbyId(int userId) {
          Optional<User> user=userRepository.findById(userId);
          if(user.isPresent()) {
              return user.get();
          }
          return null;
    }
}
