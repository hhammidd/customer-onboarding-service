package com.example.onboarding.controller;

import com.example.onboarding.dto.CustomerDto;
import com.example.onboarding.dto.RegistrationDto;
import com.example.onboarding.exception.CountryRestrictionException;
import com.example.onboarding.exception.UnderageException;
import com.example.onboarding.exception.UsernameExistsException;
import com.example.onboarding.model.Customer;
import com.example.onboarding.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final RegistrationService registrationService;

    @Autowired
    public CustomerController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Operation(summary = "Register a new customer",
            description = "Registers a new customer with their personal details and returns the created customer profile along with an auto-generated password. The password must be changed upon first login.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer registered successfully",
                            content = @Content(schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerDto customerDto) {
        try {
            RegistrationDto registered = registrationService.registerCustomer(customerDto);
            return ResponseEntity.ok(new RegistrationDto(registered.getUsername(), registered.getPassword()));
        } catch (UsernameExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        } catch (UnderageException | CountryRestrictionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}