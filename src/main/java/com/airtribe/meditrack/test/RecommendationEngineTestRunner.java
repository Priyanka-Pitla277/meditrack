package com.airtribe.meditrack.test;

import java.time.LocalDateTime;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.search.service.RecommendationEngine;
import com.airtribe.meditrack.service.AppointmentManagerService;
import com.airtribe.meditrack.service.DoctorService;

/**
 * Pure Java Manual Integration Test Runner for testing the Recommendation Engine
 * without utilizing external framework dependencies like JUnit.
 */
public class RecommendationEngineTestRunner {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   STARTING SYMPTOM SUGGESTION ENGINE TEST       ");
        System.out.println("=================================================\n");

        // 1. Initialize the required application services
        AppointmentManagerService appointmentService = new AppointmentManagerService();
        RecommendationEngine testEngine = new RecommendationEngine(appointmentService);
        DoctorService docService = new DoctorService();
      
        // 2. Register Mock Doctors via the Builder patterns defined in code.txt
        System.out.println("--- [STEP 1] Generating System Mock Profiles ---");
        
        Doctor cardDoc = new Doctor.Builder()
                .id("D101")
                .name("Dr. Sai Kumar")
                .specialization(Specialization.CARDIOLOGY)
                .consultationAmount(800.0)
                .build();

        Doctor neuroDoc = new Doctor.Builder()
                .id("D102")
                .name("Dr. Vamshi Krishna")
                .specialization(Specialization.NEUROLOGY)
                .consultationAmount(1200.0)
                .build();
        
        docService.createDoctor(cardDoc);
        docService.createDoctor(neuroDoc);

        // Explicitly inject these doctors into the repository ecosystem if your 
        // appointmentService pulls details during creation loops.
        System.out.println("Mock baseline profiles generated.\n");

        // 3. Populate Open Scheduling Slots (Simulating TestRunner logic)
        System.out.println("--- [STEP 2] Injecting Open Schedule Slots ---");
        LocalDateTime testDate = LocalDateTime.of(2026, 6, 15, 10, 0); // Fixed date context for 2026
        
        // Populate open appointment slots into memory
        appointmentService.createScheduleSlot("D101", testDate);
        appointmentService.createScheduleSlot("D101", testDate.plusHours(2));
        appointmentService.createScheduleSlot("D102", testDate.plusMinutes(45));
        
        System.out.println("Successfully generated 2 Cardiology slots and 1 Neurology slot.\n");

        // ---------------------------------------------------------------------
        // TEST CASE 1: Positive Scenario (Matching Symptoms Exist)
        // ---------------------------------------------------------------------
        System.out.println("--- [TEST CASE 1] Symptoms matching CARDIOLOGY ---");
        String cardiacSymptoms = "I am experiencing sharp chest pain and my heart rate feels very high.";
        
        // This invokes AIHelper.evaluateSymptoms, maps to CARDIOLOGY, 
        // and prints out both available slots for Dr. Sai Kumar.
        testEngine.recommendAndSuggestSlots(cardiacSymptoms);


        // ---------------------------------------------------------------------
        // TEST CASE 2: Alternative Scenario (Different Department)
        // ---------------------------------------------------------------------
        System.out.println("--- [TEST CASE 2] Symptoms matching NEUROLOGY ---");
        String neuroSymptoms = "Severe splitting migraine headaches and chronic dizziness.";
        
        // This maps to NEUROLOGY and prints out the single slot for Dr. Vamshi Krishna.
        testEngine.recommendAndSuggestSlots(neuroSymptoms);


        // ---------------------------------------------------------------------
        // TEST CASE 3: Edge Case Scenario (Valid Rule, Zero Slots Available)
        // ---------------------------------------------------------------------
        System.out.println("--- [TEST CASE 3] Valid Matching Specialty with Zero Open Slots ---");
        String dermaSymptoms = "I have an itchy skin rash spreading across my arm.";
        
        // This maps cleanly to DERMATOLOGY. Since we didn't populate any slots for 
        // DERMATOLOGY above, it triggers the fallback empty state warning text block.
        testEngine.recommendAndSuggestSlots(dermaSymptoms);


        System.out.println("=================================================");
        System.out.println("         MANUAL VERIFICATION SCRIPT COMPLETE     ");
        System.out.println("=================================================");
    }
}