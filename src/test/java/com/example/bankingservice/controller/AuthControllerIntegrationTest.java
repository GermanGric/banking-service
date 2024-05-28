package com.example.bankingservice.controller;

import com.example.bankingservice.model.AuthRequest;
import com.example.bankingservice.model.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private AuthRequest authRequest;

//    @BeforeEach
//    public void setUp() {
//        authRequest = new AuthRequest("testuser", "testpassword");
//    }

    @Test
    public void authenticate_ShouldReturnJwtToken() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\", \"password\":\"testpassword\"}")
        ).andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println("JWT Token: " + response); // Отладочная информация

        // Проверка, что токен содержится в ответе
        assert(response.contains("jwtToken"));
    }
}
