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

    // Retrieves an account by IBAN
    public Optional<Account> getAccountByIban(String iban) {
        return accountRepository.findByIban(iban);
    }

    // Retrieves an account by customer ID
    public Optional<Account> getAccountByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    // Create or update an account
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

//    public String generateIBAN() {
//        String countryCode = "NL";
//        String bankCode = "ABCD"; // Example bank code
//        String accountNumber = generateRandomAccountNumber(); // Generate random account number
//        String checkDigits = generateCheckDigits(countryCode, bankCode, accountNumber);
//        return countryCode + checkDigits + bankCode + accountNumber;
//    }
}