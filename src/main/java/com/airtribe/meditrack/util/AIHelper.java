package com.airtribe.meditrack.util;

import java.util.Arrays;

import com.airtribe.meditrack.enums.Specialization;

public class AIHelper {
	/**
     * Rule-based inference engine matching symptom phrases to medical specialties.
     */
    public static Specialization evaluateSymptoms(String symptomText) {
        if (symptomText == null || symptomText.trim().isEmpty()) {
            throw new IllegalArgumentException("Symptom input description cannot be empty.");
        }

        String input = symptomText.toLowerCase().trim();

        // Rule 1: Cardiology
        if (matchesAny(input, "chest pain", "heart", "palpitation", "high blood pressure", "arrhythmia")) {
            return Specialization.CARDIOLOGY;
        }
        // Rule 2: Dermatology
        if (matchesAny(input, "rash", "skin", "acne", "mole", "itching", "eczema")) {
            return Specialization.DERMATOLOGY;
        }
        // Rule 3: Neurology
        if (matchesAny(input, "migraine", "headache", "seizure", "numbness", "dizziness", "nerve")) {
            return Specialization.NEUROLOGY;
        }
        // Rule 4: Pediatrics
        if (matchesAny(input, "child", "baby", "pediatric", "infant", "toddler")) {
            return Specialization.PEDIATRICS;
        }
        // Rule 5: Psychiatry
        if (matchesAny(input, "anxiety", "depression", "panic attack", "insomnia", "mood swing")) {
            return Specialization.PSYCHIATRY;
        }
        // Rule 6: Radiology
        if (matchesAny(input, "x-ray", "mri", "ultrasound", "scan", "fracture")) {
            return Specialization.RADIOLOGY;
        }

        System.out.println("[System Info] Symptoms did not match specific rules. Defaulting to Cardiology.");
        return Specialization.CARDIOLOGY; 
    }

    private static boolean matchesAny(String input, String... keywords) {
        return Arrays.stream(keywords).anyMatch(input::contains);
    }
}
