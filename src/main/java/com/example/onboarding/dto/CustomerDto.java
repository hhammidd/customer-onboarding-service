package com.example.onboarding.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {
    private String name;
    private String address;
    private Date dateOfBirth;
    private String idDocument;
    private String username;
    private String country;
}
