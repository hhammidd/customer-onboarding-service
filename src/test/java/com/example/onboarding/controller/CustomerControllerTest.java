package com.example.onboarding.controller;

import com.example.onboarding.dto.CustomerDto;
import com.example.onboarding.dto.RegistrationDto;
import com.example.onboarding.exception.CountryRestrictionException;
import com.example.onboarding.exception.UnderageException;
import com.example.onboarding.exception.UsernameExistsException;
import com.example.onboarding.service.RegistrationService;
import com.example.onboarding.util.CustomerDtoBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles({"junit", "local", "tst"})
@TestPropertySource("classpath:/database-test.properties")
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Test
    public void registerCustomer_Success() throws Exception {
        CustomerDto customerDto = CustomerDtoBuilder.createValidCustomer();
        RegistrationDto registrationDto = new RegistrationDto("username", "generatedPassword");

        given(registrationService.registerCustomer(any(CustomerDto.class))).willReturn(registrationDto);

        // Act & Assert
        mockMvc.perform(post("/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    public void registerCustomer_UsernameExistsException() throws Exception {
        CustomerDto customerDto = new CustomerDto();  // Populate the fields as necessary
        given(registrationService.registerCustomer(any(CustomerDto.class)))
                .willThrow(new UsernameExistsException("Username already exists."));

        mockMvc.perform(post("/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"existingUser\" }")) // Simplified JSON content for clarity
                .andExpect(status().isConflict())
                .andExpect(content().string("Username already exists."));
    }

    @Test
    public void registerCustomer_UnderageException() throws Exception {
        given(registrationService.registerCustomer(any(CustomerDto.class)))
                .willThrow(new UnderageException("User is underage."));

        mockMvc.perform(post("/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"dateOfBirth\": \"2010-01-01\" }"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("User is underage."));
    }

    @Test
    public void registerCustomer_CountryRestrictionException() throws Exception {
        given(registrationService.registerCustomer(any(CustomerDto.class)))
                .willThrow(new CountryRestrictionException("Country not allowed."));

        mockMvc.perform(post("/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"country\": \"NonAllowedCountry\" }"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Country not allowed."));
    }
}