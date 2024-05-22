package com.example.bankingservice.scheduler;

import com.example.bankingservice.model.BankAccount;
import com.example.bankingservice.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankAccountInterestScheduler {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Scheduled(fixedRate = 60000)
    public void addInterest() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            double newBalance = account.getBalance() * 1.05;
            double maxBalance = account.getInitialDeposit() * 2.07;
            if (newBalance > maxBalance) {
                newBalance = maxBalance;
            }
            account.setBalance(newBalance);
            bankAccountRepository.save(account);
        }
    }
}
