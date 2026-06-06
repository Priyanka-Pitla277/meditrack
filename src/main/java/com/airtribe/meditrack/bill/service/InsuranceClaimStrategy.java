package com.airtribe.meditrack.bill.service;

public class InsuranceClaimStrategy implements PaymentStratergy{

	@Override
	public boolean processPayment(double amount) {
		System.out.println("Insurance process success for the amount:" +amount);
		return true;
	}

}
