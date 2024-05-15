package com.example.onboarding.util;

import com.example.onboarding.dto.CustomerDto;

import java.util.Calendar;

public class CustomerDtoBuilder {
    public static CustomerDto createValidCustomer() {
        CustomerDto customer = new CustomerDto();
        customer.setName("John Doe");
        customer.setAddress("1234 Test Ave, Test City, TC 12345");
        customer.setUsername("johnDoe123");
        customer.setCountry("Netherlands");

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        customer.setDateOfBirth(cal.getTime());

        customer.setIdDocument("AB1234567");
        return customer;
    }
}
