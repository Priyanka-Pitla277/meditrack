package com.airtribe.meditrack.bill.service;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.notification.service.NotificationSystem;
import com.airtribe.meditrack.notification.service.NotificationSystemFactory;
import com.airtribe.meditrack.repository.InvoiceRepository;
import com.airtribe.meditrack.util.InvoiceGenerator;

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

	public void processPaymentAndgenerateBill(String strategy, String paymentType, double amount, Appointment apt) {
		if (processPayment(paymentType, amount)) {
			double discountAmount = strategy.equalsIgnoreCase("discounted")?Constants.DISCOUNT:0.0;
			double taxAmount = strategy.equalsIgnoreCase("standard")?Constants.TAX_AMOUNT:0.0;
			apt.setStatus(AppointmentStatus.CONFIRMED);
			BillSummary bill = generateBill(paymentType, amount, apt, discountAmount,taxAmount);
			notifyUser(apt, bill);
			saveInvoice(bill);
		} else {
			System.out.println("payment failed");
			return;
		}
	}
	
	
	public boolean processPayment(String paymentType, double amount) {
		PaymentStratergy paymentStrategy = BillingStrategyFactory.getPaymentStrategy(paymentType);
		return paymentStrategy.processPayment(amount);
	}
	
	public BillSummary generateBill(String paymentType, double amount, Appointment appointment, double discountAmount, double taxAmount) {
		Bill bill = new Bill.Builder().appointment(appointment).netAmount(amount)
				.consultationFee(appointment.getDoctor().getConsultationAmount())
				.invoiceNumber(InvoiceGenerator.generateInvoiceNumber()).discountAmount(discountAmount).taxAmount(taxAmount).status("PAID")
				.paymentMethod(paymentType).build();
		return new BillSummary(InvoiceGenerator.generateInvoiceNumber(), appointment, bill);

	}

	private void notifyUser(Appointment appointment, BillSummary bill) {
		// Notification(observer)
		NotificationSystem notificationSystem = NotificationSystemFactory
				.getNotificationSystem(appointment.getPatient().getNotificationType());
		notificationSystem.notifyUser(appointment);
		System.out.println(bill);
	}
	
	public void saveInvoice(BillSummary bill) {
		  new InvoiceRepository().saveInvoice(bill);
	}
	
	public BillSummary getInvoice(String invoiceNumber) {
		return new InvoiceRepository().getInvoice(invoiceNumber);
	}
	
}