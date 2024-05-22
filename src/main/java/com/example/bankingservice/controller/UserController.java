package com.example.bankingservice.controller;

import com.example.bankingservice.model.User;
import com.example.bankingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user) {
        if (userService.existsByLogin(user.getLogin()) ||
                userService.existsByEmail(user.getEmail()) ||
                userService.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Login, email or phone number is already taken");
        }
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{id}/contact")
    public User updateUserContactInfo(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUserContactInfo(id, userDetails.getEmail(), userDetails.getPhoneNumber());
    }
}
