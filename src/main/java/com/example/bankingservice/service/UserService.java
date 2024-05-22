package com.example.bankingservice.service;

import com.example.bankingservice.model.User;
import com.example.bankingservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class  UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean existsByLogin(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Transactional
    public User updateUserContactInfo(Long userId, String newEmail, String newPhoneNumber) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (newEmail != null && !newEmail.isEmpty() && userRepository.findByEmail(newEmail).isEmpty()) {
            user.setEmail(newEmail);
        }

        if (newPhoneNumber != null && !newPhoneNumber.isEmpty() && userRepository.findByPhoneNumber(newPhoneNumber).isEmpty()) {
            user.setPhoneNumber(newPhoneNumber);
        }

        return userRepository.save(user);
    }
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
