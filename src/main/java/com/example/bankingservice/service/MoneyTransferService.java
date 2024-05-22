package com.example.bankingservice.service;

package com.example.bankingservice.service;

import com.example.bankingservice.model.BankAccount;
import com.example.bankingservice.repository.BankAccountRepository;
import com.example.bankingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoneyTransferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Transactional
    public void transferMoney(Long fromUserId, Long toUserId, double amount) {
        if (fromUserId.equals(toUserId)) {
            throw new RuntimeException("Cannot transfer money to the same account");
        }

        BankAccount fromAccount = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("From account not found"))
                .getBankAccount();

        BankAccount toAccount = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("To account not found"))
                .getBankAccount();

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
    }
}
