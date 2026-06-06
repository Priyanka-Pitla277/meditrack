package com.airtribe.meditrack.util;

import java.time.Year;

public class InvoiceGenerator {
    public static String generateInvoiceNumber() {
        // Fetch current year dynamically
        String year = String.valueOf(Year.now().getValue());
        
        // Convert current millisecond timestamp to unique Hexadecimal
        String uniqueBlock = Long.toHexString(System.currentTimeMillis()).toUpperCase();
        
        return "INV-" + year + "-" + uniqueBlock;
    }
}