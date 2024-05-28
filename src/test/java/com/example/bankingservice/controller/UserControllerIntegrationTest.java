package com.example.bankingservice.controller;

import com.example.bankingservice.model.User;
import com.example.bankingservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll(); // Очистка базы данных перед каждым тестом

        // Создание пользователя
        User user = new User();
        user.setLogin("testuser");
        user.setPassword(passwordEncoder.encode("testpassword"));
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("1234567890");
        userRepository.save(user);
    }

    @Test
    public void getUser_ShouldReturnOk() throws Exception {
        // Выполнение запроса
        MvcResult result = mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Анализ ответа
        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response: " + responseBody);
        assertThat(responseBody).contains("testuser");
    }

    @Test
    public void registerUser_ShouldReturnOk() throws Exception {
        String newUserRequest = "{\"login\":\"newuser\", \"password\":\"newpassword\", \"email\":\"newuser@example.com\", \"phoneNumber\":\"1234567890\"}";

        // Выполнение запроса
        MvcResult result = mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserRequest))
                .andExpect(status().isOk())
                .andReturn();

        // Анализ ответа
        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response: " + responseBody);
        assertThat(responseBody).contains("newuser");
    }
}
