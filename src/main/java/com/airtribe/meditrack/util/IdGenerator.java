package com.airtribe.meditrack.util;

import java.security.SecureRandom;

public class IdGenerator {
	// Removed look-alike characters (O, 0, I, 1) to prevent clinic staff confusion
    private static final String CHAR_POOL = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int ID_LENGTH = 5;
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomId() {
        StringBuilder idBuilder = new StringBuilder(ID_LENGTH);
        
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(CHAR_POOL.length());
            idBuilder.append(CHAR_POOL.charAt(randomIndex));
        }
        
        return idBuilder.toString();
    }

    // Quick test execution
//    public static void main(String[] args) {
//        System.out.println("Generated Patient ID 1: " + generateRandomId()); // Example: "K7X9P"
//        System.out.println("Generated Patient ID 2: " + generateRandomId()); // Example: "B3R2M"
//        System.out.println("Generated Patient ID 3: " + generateRandomId()); // Example: "W4NL8"
//    }
}
