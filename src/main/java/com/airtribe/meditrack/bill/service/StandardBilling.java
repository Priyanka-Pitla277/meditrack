package com.airtribe.meditrack.bill.service;

import com.airtribe.meditrack.constants.Constants;

public class StandardBilling implements BillingStrategy {
    public double calculateBill(double baseAmount) {
        return baseAmount+Constants.TAX_AMOUNT;
    }
}
