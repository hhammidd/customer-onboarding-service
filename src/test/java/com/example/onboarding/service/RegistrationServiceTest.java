package com.example.onboarding.service;

import com.example.onboarding.dto.CustomerDto;
import com.example.onboarding.exception.UnderageException;
import com.example.onboarding.model.Customer;
import com.example.onboarding.repository.AccountRepository;
import com.example.onboarding.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegistrationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // Initialize mocks and inject into the registrationService
    }

    @Test
    public void registerCustomer_UnderageException() {
        // Setup
        CustomerDto registrationDto = new CustomerDto();
        registrationDto.setName("Jane Doe");
        registrationDto.setAddress("124 Main St");
        registrationDto.setDateOfBirth(new Date(System.currentTimeMillis() - 20000000000L)); // More than 30 years back
        registrationDto.setIdDocument("ID1234589");
        registrationDto.setUsername("jane_doe");
        registrationDto.setCountry("Netherlands");

        when(customerRepository.findByUsername(registrationDto.getUsername())).thenReturn(Optional.of(new Customer()));

        // Execute & Assert
        assertThrows(UnderageException.class, () -> {
            registrationService.registerCustomer(registrationDto);
        });
    }
}