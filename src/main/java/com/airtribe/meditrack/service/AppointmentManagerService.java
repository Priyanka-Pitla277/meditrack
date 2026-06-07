package com.airtribe.meditrack.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.repository.AppointmentRepository;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.repository.PatientRepository;
import com.airtribe.meditrack.util.Validator;

public class AppointmentManagerService {

//    private final static List<Appointment> appointments = new ArrayList<>();
	DoctorRepository doctorRepository = new DoctorRepository();
	PatientRepository patientRepository = new PatientRepository();
	AppointmentRepository appointmentRepository = new AppointmentRepository();


    // Generates an empty calendar checkup slot for a specific doctor
	public void createScheduleSlot(String doctorId, LocalDateTime time) {
		Doctor doctor = doctorRepository.getDoctor(doctorId);
		if (doctor == null) {
			Validator.noDataFound("no data found for doctor: " + doctorId);
			return;
		}
		appointmentRepository.createAppointmentSlot(doctor, time);
		System.out.println("appointment slot added successfully");
	}
    
    
    public void addSlot(Appointment appointment) {
    	appointmentRepository.addSlot(appointment);
    }
    
    public Appointment getAppointment(String appointmentId) {
    	List<Appointment> appointments = appointmentRepository.getAppointments();
        for (Appointment apt : appointments) {
            if (apt.getAppointmentId().equals(appointmentId)) {
            	System.out.println(apt);
                return apt;
            }
        }
       Validator.noAppointmentFound(new AppointmentNotFoundException("No appointment found for: "+appointmentId));
	   return null;
    }

    // Search Strategy: Find open slots filterable by specialization
    public List<Appointment> getAvailableSlotsBySpecialization(Specialization spec) {   
    	return appointmentRepository.getAppointmentsSlotsBySpecialization(spec);
    }
    
    public List<Appointment> getAvailableSlotsByDoctor(String name) {       
    	List<Appointment> appointments = appointmentRepository.getAppointmentsSlotsByDoctor(name);       
        for (Appointment apt : appointments) {
            if (apt.getStatus() == AppointmentStatus.AVAILABLE && apt.getDoctor().getName().contains(name)) {
                System.out.println(apt);
            }
        }
        return appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE)
                .filter(a -> a.getDoctor().getName().contains(name))
                .collect(Collectors.toList());
    }
    
	public List<Appointment> getAllAvailableSlots() {
		List<Appointment> appointments = appointmentRepository.getAvailableSlots();
		System.out.println(appointments);
		return appointments;
	}
        

    // Transaction Logic: Locks and reserves an open appointment slot
	public Appointment bookAppointment(String appointmentId, String patientId) {
		Patient patient = null;
		try {
			patient = patientRepository.findPatient(patientId);
			if (patient == null) {
				throw new InvalidDataException("Invalid Patient Records.");
			}
		} catch (InvalidDataException e) {
			Validator.invalidData(e);
			return null;
		}

		List<Appointment> appointments = appointmentRepository.getAvailableSlots();
		for (Appointment apt : appointments) {
			if (apt.getAppointmentId().equals(appointmentId)) {
				apt.setPatient(patient);
				apt.setStatus(AppointmentStatus.PENDING);
				return apt;
			}
		}
		System.out.println("appointment not available");
		return null;
	}

    public void displayScheduledAppointments() {
    	List<Appointment> appointments =  appointmentRepository.getScheduledAppointments();
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
		try {
			if (appointmentRepository.cancelAppointment(appointmentId)) {
				System.out.println("refund cannot be processed for the cancellation");
				System.out.println("appointment cancelled");
			}
		} catch (AppointmentNotFoundException e) {
			Validator.noAppointmentFound(e);
		}
	}
}
