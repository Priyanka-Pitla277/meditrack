package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class Bill {
    private String invoiceNumber;
    private Appointment appointment;
    private double netAmount;
    private LocalDateTime issuedAt;
    private double consultationFee;
    private double taxAmount;
    private double surchargeAmount;
    private String paymentMethod;
    private String status;

    // 1. Private constructor forcing instantiation exclusively through the Builder
    private Bill(Builder builder) {
        this.invoiceNumber = builder.invoiceNumber;
        this.appointment = builder.appointment;
        this.consultationFee = builder.consultationFee;
        this.taxAmount = builder.taxAmount;
        this.surchargeAmount = builder.surchargeAmount;
        this.paymentMethod = builder.paymentMethod;
        this.status = builder.status;
        
        // Automatically handle business logic on generation
        this.issuedAt = builder.issuedAt != null ? builder.issuedAt : LocalDateTime.now();
        this.netAmount = this.consultationFee + this.taxAmount + this.surchargeAmount;
    }
    
    

    @Override
    public String toString() {
        return "Bill Receipt\n" +
               "------------------------------------------\n" +
               "Invoice Number   : " + invoiceNumber + "\n" +
               "Status           : " + status + "\n" +
               "Issued At        : " + issuedAt + "\n" +
               "Payment Method   : " + paymentMethod + "\n" +
               "------------------------------------------\n" +
               "Consultation Fee : ₹" + consultationFee + "\n" +
               "Tax Amount       : ₹" + taxAmount + "\n" +
               "Surcharge Amount : ₹" + surchargeAmount + "\n" +
               "------------------------------------------\n" +
               "Net Amount Paid  : ₹" + netAmount + "\n" +
               "------------------------------------------";
    }



	// Getters
    public String getInvoiceNumber() { return invoiceNumber; }
    public Appointment getAppointment() { return appointment; }
    public double getNetAmount() { return netAmount; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public double getConsultationFee() { return consultationFee; }
    public double getTaxAmount() { return taxAmount; }
    public double getSurchargeAmount() { return surchargeAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }

    // 2. The Static Internal Builder Class
    public static class Builder {
        private String invoiceNumber;
        private Appointment appointment;
        private LocalDateTime issuedAt;
        private double consultationFee;
        private double taxAmount;
        private double surchargeAmount;
        private String paymentMethod;
        private String status;

        public Builder() {
            // Default setup values can be assigned here if needed
        }

        public Builder invoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
            return this;
        }

        public Builder appointment(Appointment appointment) {
            this.appointment = appointment;
            return this;
        }

        public Builder issuedAt(LocalDateTime issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public Builder consultationFee(double consultationFee) {
            this.consultationFee = consultationFee;
            return this;
        }

        public Builder taxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }

        public Builder surchargeAmount(double surchargeAmount) {
            this.surchargeAmount = surchargeAmount;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        // Terminal step to initialize the Bill entity securely
        public Bill build() {
            // Optional: Basic validation guard rail
            if (this.invoiceNumber == null || this.invoiceNumber.isEmpty()) {
                this.invoiceNumber = "INV-" + System.currentTimeMillis();
            }
            return new Bill(this);
        }
    }
}
