package com.airtribe.meditrack.bill.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.repository.InvoiceRepository;

public class BillUtility {
	private BillingStrategy strategy;

	public BillUtility(BillingStrategy strategy) {
		this.strategy = strategy;
	}

	public void setStrategy(BillingStrategy strategy) {
		this.strategy = strategy;
	}

	public double executeStrategy(double baseAmount) {
		return strategy.calculateBill(baseAmount);
	}

	public boolean processPayment(String paymentType, double amount) {
		PaymentStratergy paymentStrategy = BillingStrategyFactory.getPaymentStrategy(paymentType);
		return paymentStrategy.processPayment(amount);
	}
	
	public boolean generateBill(String paymentType, double amount) {
		PaymentStratergy paymentStrategy = BillingStrategyFactory.getPaymentStrategy(paymentType);
		return paymentStrategy.processPayment(amount);
	}
	
	public void saveInvoice(Bill bill) {
		  new InvoiceRepository().saveInvoice(bill);
	}
	
	public Bill getInvoice(String invoiceNumber) {
		return new InvoiceRepository().getInvoice(invoiceNumber);
	}
	
}