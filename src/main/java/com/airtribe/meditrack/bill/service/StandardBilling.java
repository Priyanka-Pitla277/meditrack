package com.airtribe.meditrack.bill.service;


public class StandardBilling implements BillingStrategy {
    public double calculateBill(double baseAmount) {
        return baseAmount; // No modification for standard billing
    }
}
