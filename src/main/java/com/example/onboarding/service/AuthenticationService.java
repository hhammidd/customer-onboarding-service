package com.example.onboarding.service;

import com.example.onboarding.model.Customer;
import com.example.onboarding.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer != null && passwordEncoder.matches(password, customer.get().getPassword())) {
            return true; // can be a flag as active or not
        }
        return false;
    }

    public AuthenticationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}