package com.airtribe.meditrack.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.search.service.DoctorDynamicSearchService;
import com.airtribe.meditrack.service.AppointmentManagerService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DoctorSearchCriteria;

public class AppointmentTestRunner {

    // Common formatter to parse our manual console date-times securely
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("    STARTING MEDITRACK MANUAL INTEGRATION TEST   ");
        System.out.println("=================================================\n");

        // Instantiate our target services
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentManagerService appointmentService = new AppointmentManagerService();
        DoctorDynamicSearchService searchService = new DoctorDynamicSearchService();

        // -------------------------------------------------------------
        // TEST 1: Doctor & Patient CRUD Data Ingestion
        // -------------------------------------------------------------
        System.out.println("--- [TEST 1] Creating Doctors and Patients ---");
        
        // Mocking Data Entities (Assuming standard constructor variants exist)
        Doctor doctor1 = new Doctor.Builder().name("sai").age(32).gender("Male")
				.specialization(Specialization.CARDIOLOGY).consultationAmount(100.0).id("D101").build();
        Doctor doctor2 = new Doctor.Builder().name("vamshi").age(33).gender("Male")
				.specialization(Specialization.CARDIOLOGY).consultationAmount(200.0).id("D102").build();    
        doctorService.createDoctor(doctor1);
        doctorService.createDoctor(doctor2);
        
        System.out.print("Current Registered Doctors: ");
        doctorService.getDoctors(); // Invokes internal sysout print

        Patient pat1 = new Patient.Builder().name("John Doe").build();
        pat1.setId("PAT99");
        patientService.createPatient(pat1);
        System.out.print("Current Registered Patients: ");
        patientService.findAllPatients(); 
        System.out.println();

        // -------------------------------------------------------------
        // TEST 2: Creating Scheduling Slots (Using parsed dates)
        // -------------------------------------------------------------
        System.out.println("--- [TEST 2] Generating Schedule Slots ---");
        try {
            // Simulate string data processing from a UI/Console
            String rawDate1 = "2026-06-15 09:30";
            String rawDate2 = "2026-06-15 11:00";
            
            LocalDateTime time1 = LocalDateTime.parse(rawDate1, FORMATTER);
            LocalDateTime time2 = LocalDateTime.parse(rawDate2, FORMATTER);

            appointmentService.createScheduleSlot("D101", time1);
            appointmentService.createScheduleSlot("D101", time2);
            System.out.println("Successfully generated two slots for Doctor ID: D101");
        } catch (Exception e) {
            System.err.println("Critical failure during date parsing or slot insertion: " + e.getMessage());
        }
        System.out.println();

        // -------------------------------------------------------------
        // TEST 3: Slot Query & Filtering Strategies
        // -------------------------------------------------------------
        System.out.println("--- [TEST 3] Querying and Filtering Slots ---");
        
        // Querying all open slots
        System.out.println("Fetching all available slots overall:");
        List<Appointment> allAvailable = appointmentService.getAllAvailableSlots();

        // Filtering by specialization
        System.out.println("\nFiltering available slots specifically for CARDIOLOGY:");
        List<Appointment> cardioSlots = appointmentService.getAvailableSlotsBySpecialization(Specialization.CARDIOLOGY);
        System.out.println("Matches found: " + cardioSlots.size());
        System.out.println();

        // -------------------------------------------------------------
        // TEST 4: Booking Flow & State Mutations
        // -------------------------------------------------------------
        System.out.println("--- [TEST 4] Booking Transaction Processing ---");
        if (!allAvailable.isEmpty()) {
            Appointment targetApt = allAvailable.get(0);
            String targetId = targetApt.getAppointmentId();
            
            System.out.println("Attempting to lock and reserve Slot ID: " + targetId + " for Patient ID: PAT99");
            Appointment bookedApt = appointmentService.bookAppointment(targetId, "PAT99");
            
            if (bookedApt != null) {
                System.out.println("Booking Successful! Current Status: " + bookedApt.getStatus());
                System.out.println("Assigned Patient Name: " + bookedApt.getPatient().getName());
            } else {
                System.out.println("Booking execution returned null mapping.");
            }
        }
        System.out.println();

        // -------------------------------------------------------------
        // TEST 5: Dynamic Criteria Search Filtering
        // -------------------------------------------------------------
        System.out.println("--- [TEST 5] Dynamic Doctor Search Multi-Criteria ---");
        DoctorSearchCriteria criteria = new DoctorSearchCriteria();
        criteria.setName("Sarah");
        criteria.setMinExperience(10);
        criteria.setMaxConsultationFee(200.0);

        List<Doctor> searchResults = searchService.searchDoctors(criteria);
        System.out.println("Search Results matching name 'Sarah', min 10 yrs exp, fee <= 200:");
        for (Doctor d : searchResults) {
            System.out.printf(" -> Found: %s | Exp: %d yrs | Fee: $%.2f%n", d.getName(), d.getYearsOfExperience(), d.getConsultationAmount());
        }
        System.out.println();

        // -------------------------------------------------------------
        // TEST 6: Defensive Error and Exception Boundary Assertions
        // -------------------------------------------------------------
        System.out.println("--- Robustness Check (Negative Test Boundaries) ---");
        
        System.out.println("Scenario A: Requesting slot generation for a non-existent Doctor Profile:");
        try {
            appointmentService.createScheduleSlot("NON_EXISTENT_ID", LocalDateTime.now());
        } catch (InvalidDataException e) {
            System.out.println("Caught expected protection mechanism exception: " + e.getMessage());
        }

        System.out.println("\nScenario B: Requesting a booking validation using a non-existent Patient Profile:");
        // Note: Your Service catches InvalidDataException internally and passes it to your custom Validator.
        // We evaluate if it returns null or bubbles out depending on your Validator.invalidData structure.
		try {
			Appointment failedBooking = appointmentService.bookAppointment("ANY_ID", "BAD_PATIENT_ID");
	        System.out.println("Booking reference return validation state: " + (failedBooking == null ? "Returned Null (Safe handling)" : "Failed evaluation"));

		} catch (InvalidDataException e) {
			System.out.println("Caught expected protection mechanism exception: " + e.getMessage());
		}


        System.out.println("\n=================================================");
        System.out.println("            MANUAL TEST EXECUTION COMPLETE       ");
        System.out.println("=================================================");
    }
}