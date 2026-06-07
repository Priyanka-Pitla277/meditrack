package com.airtribe.meditrack.bill.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.notification.service.NotificationSystem;
import com.airtribe.meditrack.notification.service.NotificationSystemFactory;
import com.airtribe.meditrack.repository.InvoiceRepository;
import com.airtribe.meditrack.util.InvoiceGenerator;

public class BillUtility {
	private static final String UPI = "UPI";
	private static final String PAID = "PAID";
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

	public void processPaymentAndgenerateBill(String paymentType, double amount, Appointment apt) {
		if (processPayment(paymentType, amount)) {
			apt.setStatus(AppointmentStatus.CONFIRMED);
			Bill bill = generateBill(paymentType, amount, apt);
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
	
	public Bill generateBill(String paymentType, double amount, Appointment appointment) {
		// generateBill
		return new Bill.Builder().appointment(appointment).consultationFee(appointment.getDoctor().getConsultationAmount())
				.invoiceNumber(InvoiceGenerator.generateInvoiceNumber()).surchargeAmount(0).taxAmount(0)
				.status(PAID).paymentMethod(UPI).build();

	}

	private void notifyUser(Appointment appointment, Bill bill) {
		// Notification(observer)
		NotificationSystem notificationSystem = NotificationSystemFactory
				.getNotificationSystem(appointment.getPatient().getNotificationType());
		notificationSystem.notifyUser(appointment);
		System.out.println(bill);
	}
	
	public void saveInvoice(Bill bill) {
		  new InvoiceRepository().saveInvoice(bill);
	}
	
	public Bill getInvoice(String invoiceNumber) {
		return new InvoiceRepository().getInvoice(invoiceNumber);
	}
	
}