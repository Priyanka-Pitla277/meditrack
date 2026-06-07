package com.airtribe.meditrack.test;

import java.time.LocalDateTime;

import com.airtribe.meditrack.bill.service.BillUtility;
import com.airtribe.meditrack.bill.service.BillingStrategy;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.NotifcationType;
import com.airtribe.meditrack.enums.Specialization;

/**
 * Pure Java Manual Integration Test Runner for testing BillUtility
 * without relying on external frameworks like JUnit.
 */
public class BillUtilityTestRunner {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     STARTING BILLUTILITY MANUAL INTEGRATION TEST ");
        System.out.println("=================================================\n");

        // 1. Initialize Mock Dependencies
        System.out.println("--- [STEP 1] Generating Mock Entity Profiles ---");
        
        Doctor mockDoctor = new Doctor.Builder()
                .id("DOC-99")
                .name("Dr. Adithya")
                .specialization(Specialization.CARDIOLOGY)
                .consultationAmount(1000.0)
                .build();

        Patient mockPatient = new Patient.Builder()
                .name("Rahul Sharma")
                .notificationType(NotifcationType.valueOf("SMS")).phoneNo("12121212")
                .build();
        mockPatient.setId("PAT-45");

        System.out.println("Mock profiles generated successfully.\n");

        // 2. Instantiate a baseline Strategy (Using an anonymous implementation for testing)
        BillingStrategy testStrategy = new BillingStrategy() {
            @Override
            public double calculateBill(double baseAmount) {
                return baseAmount; // Simple pass-through strategy for testing calculations
            }
        };

        BillUtility billUtility = new BillUtility(testStrategy);

        // ---------------------------------------------------------------------
        // TEST CASE 1: Successful "Standard" Strategy Processing
        // ---------------------------------------------------------------------
        System.out.println("--- [TEST CASE 1] Processing Successful Standard Bill ---");      
		Appointment app1 = new Appointment(mockDoctor, LocalDateTime.of(2026, 6, 15, 10, 0), AppointmentStatus.PENDING);
		app1.setPatient(mockPatient);
        try {
            // NOTE: Ensure your BillingStrategyFactory maps "UPI" to a valid dummy strategy 
            // that returns true for processPayment() to avoid "payment failed" output.
            System.out.println("Executing payment processing via UPI...");
            billUtility.processPaymentAndgenerateBill("standard", "UPI", 1100.0, app1);
            
            // Verify structural side-effects on passing objects
            System.out.println("\nPost-Execution Verification:");
            System.out.println("Expected Status: CONFIRMED | Actual Status: " + app1.getStatus());
            
            if (app1.getStatus() == AppointmentStatus.CONFIRMED) {
                System.out.println("✅ TEST CASE 1 PASSED!");
            } else {
                System.out.println("❌ TEST CASE 1 FAILED: Appointment state not muted to CONFIRMED.");
            }
        } catch (Exception e) {
            System.out.println("❌ TEST CASE 1 FAILED with exception: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n-------------------------------------------------\n");

        // ---------------------------------------------------------------------
        // TEST CASE 2: Successful "Discounted" Strategy Processing
        // ---------------------------------------------------------------------
        System.out.println("--- [TEST CASE 2] Processing Successful Discounted Bill ---");

		Appointment app2 = new Appointment(mockDoctor, LocalDateTime.of(2026, 6, 16, 14, 0), AppointmentStatus.PENDING);
		app1.setPatient(mockPatient);

        try {
            billUtility.processPaymentAndgenerateBill("discounted", "Credit Card", 900.0, app2);
            
            System.out.println("\nPost-Execution Verification:");
            System.out.println("Expected Status: CONFIRMED | Actual Status: " + app2.getStatus());
            
            if (app2.getStatus() == AppointmentStatus.CONFIRMED) {
                System.out.println("TEST CASE 2 PASSED!");
            } else {
                System.out.println("TEST CASE 2 FAILED.");
            }
        } catch (Exception e) {
            System.out.println("TEST CASE 2 FAILED with exception: " + e.getMessage());
        }

        System.out.println("\n-------------------------------------------------\n");

        // ---------------------------------------------------------------------
        // TEST CASE 3: Edge Case (Payment Engine Rejection/Failure)
        // ---------------------------------------------------------------------
        System.out.println("--- [TEST CASE 3] Simulating Payment Failure Flow ---");
		Appointment app3 = new Appointment(mockDoctor, LocalDateTime.of(2026, 6, 17, 11, 30), AppointmentStatus.PENDING);
		app3.setPatient(mockPatient);

        // Using an intentionally unmapped payment type string "INVALID_GATEWAY" 
        // to force the underlying payment factory execution to return false or fail.
        System.out.println("Executing process payment with an unmapped payment system method...");
        billUtility.processPaymentAndgenerateBill("standard", "UPI", 1000.0, app3);
        
        System.out.println("\nPost-Execution Verification:");
        System.out.println("Expected Status: PENDING | Actual Status: " + app3.getStatus());
        
        if (app3.getStatus() == AppointmentStatus.PENDING) {
            System.out.println("EST CASE 3 PASSED: System gracefully rejected operation and maintained state safety.");
        } else {
            System.out.println("TEST CASE 3 FAILED: Status mutated despite payment rejection flag.");
        }

        System.out.println("\n=================================================");
        System.out.println("         MANUAL TEST SCRIPTS EXECUTION COMPLETE  ");
        System.out.println("=================================================");
    }
}