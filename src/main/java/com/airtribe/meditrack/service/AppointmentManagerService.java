package com.airtribe.meditrack.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.airtribe.meditrack.bill.service.BillUtility;
import com.airtribe.meditrack.bill.service.BillingStrategy;
import com.airtribe.meditrack.bill.service.BillingStrategyFactory;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.repository.PatientRepository;
import com.airtribe.meditrack.util.DoctorSearchCriteria;

public class AppointmentManagerService {

    private final static List<Appointment> appointments = new ArrayList<>();
	DoctorRepository doctorRepository = new DoctorRepository();
	PatientRepository patientRepository = new PatientRepository();



    // Generates an empty calendar checkup slot for a specific doctor
    public void createScheduleSlot(String doctorId, LocalDateTime time) {
        Doctor doc = doctorRepository.getDoctor(doctorId);
        if (doc == null) throw new IllegalArgumentException("Doctor profile does not exist.");
        appointments.add(new Appointment(doc, time,AppointmentStatus.AVAILABLE));
    }
    
    public void addSlot(Appointment appointment) {
        appointments.add(appointment);
    }
//
//    // Search Strategy: Find open slots filterable by specialization
    public List<Appointment> getAvailableSlotsBySpecialization(Specialization spec) {
     
        System.out.println("\n--- Current available Sessions ---");

        for (Appointment apt : appointments) {
            if (apt.getStatus() == AppointmentStatus.AVAILABLE) {
                System.out.printf("ID: %s | Doctor: %s (%s) | Time: %s | Amount: %f%n",
                        apt.getAppointmentId(), apt.getDoctor().getName(), 
                        apt.getDoctor().getSpecialization(), 
                        apt.getAppointmentDateTime(), apt.getDoctor().getConsultationAmount());
            }
        }
        return appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE)
                .filter(a -> a.getDoctor().getSpecialization() == spec)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getAvailableSlotsByDoctor(String name) {
        
        System.out.println("\n--- Current available Sessions ---");

        for (Appointment apt : appointments) {
            if (apt.getStatus() == AppointmentStatus.AVAILABLE && apt.getDoctor().getName().contains(name)) {
                System.out.printf("ID: %s | Doctor: %s (%s) | Time: %s%n",
                        apt.getAppointmentId(), apt.getDoctor().getName(), 
                        apt.getDoctor().getSpecialization(), 
                        apt.getAppointmentDateTime());
            }
        }
        return appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE)
                .filter(a -> a.getDoctor().getName().contains(name))
                .collect(Collectors.toList());
    }
    
	public List<Appointment> getAllAvailableSlots() {
		return appointments.stream().filter(a -> (a.getStatus() == AppointmentStatus.AVAILABLE))
				.collect(Collectors.toList());
	}
    
    
//
//    // Transaction Logic: Locks and reserves an open appointment slot
	public Appointment bookAppointment(String appointmentId, String patientId) {
		Patient patient = patientRepository.findPatient(patientId);
		if (patient == null)
			throw new IllegalArgumentException("Invalid Patient Records.");

		for (Appointment apt : appointments) {
			if (apt.getAppointmentId().equals(appointmentId)) {
				if (apt.getStatus() == AppointmentStatus.AVAILABLE) {
					// payment
					BillingStrategy strategy = BillingStrategyFactory.getBillingStrategy("discounted");
					BillUtility billContext = new BillUtility(strategy);
					double amountToBePaid =billContext.executeStrategy(apt.getDoctor().getConsultationAmount());
					apt.setPatient(patient);
					apt.setStatus(AppointmentStatus.PENDING);
					System.out.println("please proceed for the payment of "+amountToBePaid);
					System.out.println("select the payment type UPI, Credit Card, Insurance");
					return apt;
				}
			}
		}
		System.out.println("appointment not available");
		return null;
	}
//
    public void displayScheduledAppointments() {
        System.out.println("\n--- Current Scheduled Sessions ---");
        for (Appointment apt : appointments) {
            if (apt.getStatus() == AppointmentStatus.CONFIRMED) {
                System.out.printf("ID: %s | Doctor: %s (%s) | Patient: %s | Time: %s%n",
                        apt.getAppointmentId(), apt.getDoctor().getName(), 
                        apt.getDoctor().getSpecialization(), apt.getPatient().getName(), 
                        apt.getAppointmentDateTime());
            }
        }
    }
    
    public void cancelAppointment(String appointmentId) {
        System.out.println("\n--- Current Scheduled Sessions ---");
        for (Appointment apt : appointments) {
            if (apt.getAppointmentId().equals(appointmentId) && apt.getStatus()==AppointmentStatus.CONFIRMED) {
            	apt.setStatus(AppointmentStatus.CANCELLED);
            	System.out.println("refund cannot be processed for the cancellation");
            	appointments.remove(apt);
            	System.out.println("appointment cancelled");
            	break;
            }
        }
    }
    
    
	public List<Doctor> searchDoctors(DoctorSearchCriteria criteria) {
		return doctorRepository.getDoctors().stream().filter(doc -> {
			// 1. Dynamic Name Check
			if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
				if (!doc.getName().toLowerCase().contains(criteria.getName().toLowerCase().trim())) {
					return false;
				}
			}
			// 2. Dynamic Specialization Check
			if (criteria.getSpecialization() != null && !criteria.getSpecialization().trim().isEmpty()) {
				if (!doc.getSpecialization().toString().toLowerCase()
						.equalsIgnoreCase(criteria.getSpecialization().toLowerCase().trim())) {
					return false;
				}
			}
			// 3. Dynamic Experience Check
			if (criteria.getMinExperience() != null) {
				if (doc.getYearsOfExperience() < criteria.getMinExperience()) {
					return false;
				}
			}
			// 4. Dynamic Budget/Fee Check
			if (criteria.getMaxConsultationFee() != null) {
				if (doc.getConsultationAmount() > criteria.getMaxConsultationFee()) {
					return false;
				}
			}
			return true; // Match found if it passes all active checks
		}).collect(Collectors.toList());
	}
}
