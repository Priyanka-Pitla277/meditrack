package com.airtribe.meditrack.bill.service;

//factory for BillingStrategy
public class BillingStrategyFactory {
	public static BillingStrategy getBillingStrategy(String strategyType) {
		switch (strategyType.toLowerCase()) {
		case "standard":
			return new StandardBilling();
		case "discounted":
			return new DiscountedBilling();
		case "premium":
			return new PremiumBilling();
		default:
			throw new IllegalArgumentException("Unknown strategy type");
		}
	}
	
	public static PaymentStratergy getPaymentStrategy(String paymentType) {
		switch (paymentType) {
		case "UPI":
			return new UPIPaymentStartegy();
		case "Credit Card":
			return new CCPaymentStrategy();
		case "Insurance":
			return new InsuranceClaimStrategy();
		default:
			throw new IllegalArgumentException("Unknown strategy type");
		}
	}
}