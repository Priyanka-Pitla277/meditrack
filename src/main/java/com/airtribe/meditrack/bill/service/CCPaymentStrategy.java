package com.airtribe.meditrack.bill.service;

public class CCPaymentStrategy implements PaymentStratergy{

	@Override
	public boolean processPayment(double amount) {
		// TODO Auto-generated method stub
		System.out.println("CC payemnt success for the amount:" +amount);
		return true;
	}

}
