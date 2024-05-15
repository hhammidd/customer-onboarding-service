package com.example.onboarding.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(nullable = false, unique = true, length = 22)
    private String iban;

    @Column(name = "account_type", length = 100)
    private String accountType;

    @Column(nullable = false)
    private Double balance;

    @Column(length = 3)
    private String currency;
}