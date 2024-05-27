package com.example.bankingservice.controller;

import com.example.bankingservice.service.MoneyTransferService;
import com.example.bankingservice.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    private final MoneyTransferService transferService;
    private final UserService userService;

    public BankAccountController(MoneyTransferService transferService, UserService userService) {
        this.transferService = transferService;
        this.userService = userService;
    }

    @PostMapping("/transfer")
    public void transferMoney(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam Long toUserId,
                              @RequestParam double amount) {

        Long fromUserId = userService.getUserByLogin(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        transferService.transferMoney(fromUserId, toUserId, amount);
    }
}
