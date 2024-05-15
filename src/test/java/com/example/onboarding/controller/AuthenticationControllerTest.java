package com.example.onboarding.controller;

import com.example.onboarding.service.AuthenticationService;
import com.example.onboarding.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void generateToken_Success() throws Exception {
        try (MockedStatic<JwtUtil> mocked = Mockito.mockStatic(JwtUtil.class)) {
            // Setup
            when(authenticationService.authenticate("user", "pass")).thenReturn(true);
            when(JwtUtil.generateToken("user")).thenReturn("generatedToken");

            // Execute & Assert
            mockMvc.perform(post("/auth/token")
                            .param("username", "user")
                            .param("password", "pass"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("generatedToken"));
        }
    }

    @Test
    public void generateToken_Failure() throws Exception {
        // Setup
        try (MockedStatic<JwtUtil> mocked = Mockito.mockStatic(JwtUtil.class)) {
            when(authenticationService.authenticate("user", "wrongpass")).thenReturn(false);

            // Execute & Assert
            mockMvc.perform(post("/auth/token")
                            .param("username", "user")
                            .param("password", "wrongpass"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string("Invalid credentials"));
        }
    }

    @Test
    public void logon_Success() throws Exception {
        // Setup
        String validToken = "Bearer generatedToken";
        try (MockedStatic<JwtUtil> mocked = Mockito.mockStatic(JwtUtil.class)) {
            when(JwtUtil.validateToken("generatedToken")).thenReturn(true);
            mockMvc.perform(post("/auth/logon")
                            .header("Authorization", validToken))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Success response"));
        }
    }

    @Test
    public void logon_InvalidToken() throws Exception {
        // Setup
        String invalidToken = "Bearer invalidToken";
        try (MockedStatic<JwtUtil> mocked = Mockito.mockStatic(JwtUtil.class)) {
            when(JwtUtil.validateToken("invalidToken")).thenReturn(false);

            // Execute & Assert
            mockMvc.perform(post("/auth/logon")
                            .header("Authorization", invalidToken))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string("Invalid token"));
        }
    }
}