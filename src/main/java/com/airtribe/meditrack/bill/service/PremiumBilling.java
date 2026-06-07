package com.airtribe.meditrack.bill.service;

import com.airtribe.meditrack.constants.Constants;

public class PremiumBilling implements BillingStrategy {
    public double calculateBill(double baseAmount) {
        return baseAmount * Constants.PREMIUM;
    }
}
