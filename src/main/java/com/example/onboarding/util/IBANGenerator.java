package com.example.onboarding.util;

import java.util.Random;

public class IBANGenerator {
    private static final Random random = new Random();

    public static String generateNLIBAN() {
        // Bank code for example purposes, should be specific to the bank
        String bankCode = "BANK";
        // Randomly generated account number - ensure this number does not violate any actual account number rules
        String accountNumber = String.format("%010d", random.nextInt(1_000_000_000));
        // NL for Netherlands, 00 as placeholder check digits
        String countryCode = "NL";
        String checkDigits = "00";  // Placeholder, should calculate actual check digits based on the entire number

        return countryCode + checkDigits + bankCode + accountNumber;
    }
}
