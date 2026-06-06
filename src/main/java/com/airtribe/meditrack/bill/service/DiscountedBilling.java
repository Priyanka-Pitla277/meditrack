package com.airtribe.meditrack.bill.service;

public class DiscountedBilling implements BillingStrategy {
    public double calculateBill(double baseAmount) {
        return baseAmount * 0.9; // 10% discount
    }
}
