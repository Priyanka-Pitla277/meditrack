package com.airtribe.meditrack.bill.service;

public class UPIPaymentStartegy implements PaymentStratergy{

	@Override
	public boolean processPayment(double amount) {
		System.out.println("UPI payemnt success for the amount:" +amount);
		return true;	}

}
