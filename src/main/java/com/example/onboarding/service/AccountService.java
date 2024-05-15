package com.example.onboarding.service;

import com.example.onboarding.model.Account;
import com.example.onboarding.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Retrieves an account by customer ID
    public Optional<Account> getAccountByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

}