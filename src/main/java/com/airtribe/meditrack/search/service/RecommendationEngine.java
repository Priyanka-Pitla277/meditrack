package com.airtribe.meditrack.search.service;

import java.util.List;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.service.AppointmentManagerService;
import com.airtribe.meditrack.util.AIHelper;


public class RecommendationEngine {

    private final AppointmentManagerService appointmentService;

    public RecommendationEngine(AppointmentManagerService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Evaluates symptoms, recommends a specialty, and auto-suggests open booking spots.
     */
    public void recommendAndSuggestSlots(String symptoms) {
        System.out.println("\n==========================================");
        System.out.println("       RUNNING SYMPTOM DIAGNOSIS ENGINE   ");
        System.out.println("==========================================");
        System.out.println("Patient Reported Symptoms: \"" + symptoms + "\"");

        // 1. Fire Rule-Based Evaluation
        Specialization recommendedSpecialty = AIHelper.evaluateSymptoms(symptoms);
        System.out.println("Recommended Medical Department: [" + recommendedSpecialty + "]");

        // 2. Fetch Active Open Slots matching the Specialty from our Manager Service
        List<Appointment> suggestedSlots = appointmentService.getAvailableSlotsBySpecialization(recommendedSpecialty);

        // 3. Render Auto-Suggestions to the User Interface Console
        if (suggestedSlots.isEmpty()) {
            System.out.println("Notice: No upcoming open appointment slots found for " + recommendedSpecialty + ".");
        } else {
            System.out.println("Auto-Suggested Appointment Slots Found (" + suggestedSlots.size() + "):");
            System.out.println("----------------------------------------------------------------------");
            for (Appointment slot : suggestedSlots) {
                System.out.printf("[Slot ID: %s] | Dr. %s | Time: %s | Fee: ₹%.2f%n",
                        slot.getAppointmentId(),
                        slot.getDoctor().getName(),
                        slot.getAppointmentDateTime(),
                        slot.getDoctor().getConsultationAmount());
            }
            System.out.println("----------------------------------------------------------------------");
            System.out.println("To secure a reservation, select option (13) from the main menu using the Slot ID above.");
        }
        System.out.println("==========================================\n");
    }
}