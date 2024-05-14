package com.example.onboarding.service;

import com.example.onboarding.model.Customer;
import com.example.onboarding.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final Map<String, String> tokenStorage = new HashMap<>();

    public AuthenticationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String authenticate(String username, String password) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            String token = UUID.randomUUID().toString();
            tokenStorage.put(token, username); // Store token associated with username
            return token;
        }
        return null; // Authentication failed
    }

    public boolean verifyToken(String token) {
        return tokenStorage.containsKey(token);
    }
}