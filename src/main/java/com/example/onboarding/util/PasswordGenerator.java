package com.example.onboarding.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = UPPER_CASE.toLowerCase();
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+";
    private static final int PASSWORD_LENGTH = 12;  // You can customize this

    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomPassword() {
        String str = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHARACTERS;
        List<Character> pwdChars = new ArrayList<>(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            pwdChars.add(str.charAt(random.nextInt(str.length())));
        }
        Collections.shuffle(pwdChars);
        StringBuilder sb = new StringBuilder();
        for (char ch : pwdChars) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
