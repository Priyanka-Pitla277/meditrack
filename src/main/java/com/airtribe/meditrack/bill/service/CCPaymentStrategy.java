package com.airtribe.meditrack.bill.service;

public class CCPaymentStrategy implements PaymentStratergy{

	@Override
	public boolean processPayment(double amount) {
		System.out.println("CC payemnt success for the amount:" +amount);
		return true;
	}

}
