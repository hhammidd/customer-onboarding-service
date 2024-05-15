package com.example.onboarding.controller;

import com.example.onboarding.model.Account;
import com.example.onboarding.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void getAccountByCustomerId_Found() throws Exception {
        Long customerId = 1L;
        Account account = new Account();
        account.setAccountId(1L);
        account.setCustomerId(customerId);
        account.setIban("NL91ABNA0417164300");
        account.setAccountType("Savings");
        account.setBalance(1000.00);
        account.setCurrency("EUR");

        when(accountService.getAccountByCustomerId(any(Long.class))).thenReturn(Optional.of(account));

        mockMvc.perform(get("/accounts/customer/{customerId}", customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.iban").value("NL91ABNA0417164300"))
                .andExpect(jsonPath("$.accountType").value("Savings"))
                .andExpect(jsonPath("$.balance").value(1000.00))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    public void getAccountByCustomerId_NotFound() throws Exception {
        Long customerId = 1L;

        when(accountService.getAccountByCustomerId(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/accounts/customer/{customerId}", customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}