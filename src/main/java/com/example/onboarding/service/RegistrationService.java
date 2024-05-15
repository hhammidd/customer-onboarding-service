package com.example.onboarding.service;

import com.example.onboarding.dto.RegistrationDto;
import com.example.onboarding.dto.CustomerDto;
import com.example.onboarding.exception.CountryRestrictionException;
import com.example.onboarding.exception.UnderageException;
import com.example.onboarding.exception.UsernameExistsException;
import com.example.onboarding.model.Account;
import com.example.onboarding.model.Customer;
import com.example.onboarding.repository.AccountRepository;
import com.example.onboarding.repository.CustomerRepository;
import com.example.onboarding.util.IBANGenerator;
import com.example.onboarding.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Service
public class RegistrationService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public RegistrationService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public RegistrationDto registerCustomer(CustomerDto registrationDto) throws UsernameExistsException, CountryRestrictionException, UnderageException {
        validateCustomerData(registrationDto);

        // Create a new customer from the DTO
        String randomPassword = PasswordGenerator.generateRandomPassword();
        Customer newCustomer = mapDtoToCustomer(registrationDto, randomPassword);
        customerRepository.save(newCustomer);

        // Create a new account and assign a generated IBAN
        Account newAccount = mapDtoToAccount(newCustomer.getId());
        accountRepository.save(newAccount);

        return new RegistrationDto(newCustomer.getUsername(), randomPassword);
    }

    private Account mapDtoToAccount(Long custometId) {
        Account newAccount = new Account();
        newAccount.setCustomerId(custometId);
        newAccount.setIban(IBANGenerator.generateNLIBAN());
        newAccount.setAccountType("Checking");
        newAccount.setBalance(0.0);
        newAccount.setCurrency("EUR");
        return newAccount;
    }

    private void validateCustomerData(CustomerDto customerDto) throws UsernameExistsException, CountryRestrictionException,
            UnderageException {
        String country = customerDto.getCountry();
        if (!isValidCountry(country)) {
            throw new CountryRestrictionException("Only customers from the Netherlands and Belgium are allowed to register.");
        }

        if (customerDto == null) {
            throw new IllegalArgumentException("Registration data cannot be null");
        }

        if (customerRepository.findByUsername(customerDto.getUsername()).isPresent()) {
            throw new UsernameExistsException("Username already exists: " + customerDto.getUsername());
        }

        LocalDate dateOfBirth = customerDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (18 > Period.between(dateOfBirth, LocalDate.now()).getYears()) {
            throw new UnderageException("Customer must be at least 18 years old.");
        }
        validateRegistrationData(customerDto);
    }

    private Customer mapDtoToCustomer(CustomerDto customerDto, String randomPassword) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setAddress(customerDto.getAddress());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setIdDocument(customerDto.getIdDocument());
        customer.setUsername(customerDto.getUsername());
        // Handling IBAN and password generation internally within the entity or here
        customer.setPassword(generatePassword(randomPassword)); // Stub for password generation

        return customer;
    }

    private String generatePassword(String generatedPassword) {
        return passwordEncoder.encode(generatedPassword);
    }
    private void validateRegistrationData(CustomerDto customerDto) {
        // Validate all required fields are present and valid
        if (customerDto.getUsername() == null || customerDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username is required and cannot be empty");
        }
        // Additional validations can be added here
    }

    // Method to validate if the country is allowed, Can be added to DB and modify it , Requirement says not possible
    private boolean isValidCountry(String country) {
        return "Netherlands".equalsIgnoreCase(country) || "Belgium".equalsIgnoreCase(country);
    }
}
