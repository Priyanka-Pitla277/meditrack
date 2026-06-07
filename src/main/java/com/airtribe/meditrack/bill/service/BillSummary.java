package com.airtribe.meditrack.bill.service;

import java.time.LocalDateTime;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;

/**
 * An immutable value object representing a read-only snapshot transaction
 * receipt generated immediately after successful payment processing.
 */
public final class BillSummary {

	private final String invoiceNumber;
	private final String appointmentId;
	private final String doctorName;
	private final String patientName;
	private final double baseAmount;
	private final double finalPaidAmount;
	private final String paymentType;
	private final double discountAmount;
	private final double taxAmount;
	private final LocalDateTime transactionTimestamp;

	/**
	 * Constructs a secure runtime transaction summary.
	 */
	public BillSummary(String invoiceNumber, Appointment appointment, Bill bill) {
		if (appointment == null) {
			throw new IllegalArgumentException("Appointment context cannot be null for bill summarization.");
		}

		this.invoiceNumber = invoiceNumber;
		this.appointmentId = appointment.getAppointmentId();
		this.doctorName = appointment.getDoctor() != null ? appointment.getDoctor().getName() : "N/A";
		this.patientName = appointment.getPatient() != null ? appointment.getPatient().getName() : "N/A";
		this.baseAmount = appointment.getDoctor() != null ? appointment.getDoctor().getConsultationAmount() : 0.0;
		this.finalPaidAmount = bill.getNetAmount();
		this.paymentType = bill.getPaymentMethod() != null ? bill.getPaymentMethod() : "UNKNOWN";
		this.transactionTimestamp = LocalDateTime.now();
		this.taxAmount = bill.getTaxAmount();
		this.discountAmount = bill.getDiscountAmount();
	}

	public double getDiscountAmount() {
		return discountAmount;
	}



	public double getTaxAmount() {
		return taxAmount;
	}



	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public String getPatientName() {
		return patientName;
	}

	public double getBaseAmount() {
		return baseAmount;
	}

	public double getFinalPaidAmount() {
		return finalPaidAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public LocalDateTime getTransactionTimestamp() {
		return transactionTimestamp;
	}

	@Override
    public String toString() {
        return String.format(
            "========================================%n" +
            "           MEDICAL BILL SUMMARY         %n" +
            "========================================%n" +
            "Invoice No   : %s%n" +
            "Appointment  : %s%n" +
            "Patient Name : %s%n" +
            "Physician    : %s%n" +
            "----------------------------------------%n" +
            "Base Fee     : $%.2f%n" +
            "Tax Fee      : $%.2f%n" +
            "Discount Fee : $%.2f%n" +
            "Amount Paid  : $%.2f (via %s)%n" +
            "issuesAt     : %s%n" +
            "========================================",
            invoiceNumber, appointmentId, patientName, doctorName, baseAmount,taxAmount, discountAmount, finalPaidAmount, paymentType, transactionTimestamp
        );
    }
}