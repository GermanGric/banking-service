package com.example.bankingservice.controller;

import com.example.bankingservice.model.User;
import com.example.bankingservice.service.MoneyTransferService;
import com.example.bankingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.*;

public class BankAccountControllerTests {

    @Mock
    private MoneyTransferService moneyTransferService;

    @Mock
    private UserService userService;

    private BankAccountController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new BankAccountController(moneyTransferService, userService);
    }

    @Test
    public void testTransferMoney() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("testuser");

        when(userService.getUserByLogin("testuser")).thenReturn(java.util.Optional.of(mockUser));

        UserDetails mockUserDetails = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password("password")
                .authorities("USER")
                .build();

        controller.transferMoney(mockUserDetails, 2L, 50.0);

        verify(moneyTransferService, times(1)).transferMoney(1L, 2L, 50.0);
    }

    @Test
    public void testTransferMoney_UserNotFound() {
        when(userService.getUserByLogin("testuser")).thenThrow(new UsernameNotFoundException("User not found"));

        UserDetails mockUserDetails = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password("password")
                .authorities("USER")
                .build();

        try {
            controller.transferMoney(mockUserDetails, 2L, 50.0);
        } catch (RuntimeException e) {
        }

        verify(moneyTransferService, times(0)).transferMoney(anyLong(), anyLong(), anyDouble());
    }
}

