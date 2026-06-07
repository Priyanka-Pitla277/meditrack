package com.airtribe.meditrack.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;

public class AppointmentRepository {
	private final static List<Appointment> appointments = new ArrayList<>();

	public void createAppointmentSlot(Doctor doctor, LocalDateTime time) {
		appointments.add(new Appointment(doctor, time, AppointmentStatus.AVAILABLE));
	}

	public void addSlot(Appointment appointment) {
		appointments.add(appointment);
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public List<Appointment> getAppointmentsSlotsBySpecialization(Specialization specialization) {
		return appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE)
				.filter(a -> a.getDoctor().getSpecialization() == specialization).collect(Collectors.toList());
	}

	public List<Appointment> getAppointmentsSlotsByDoctor(String name) {
		return appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE)
				.filter(a -> a.getDoctor().getName().equalsIgnoreCase(name)).collect(Collectors.toList());
	}

	public boolean cancelAppointment(String appointmentId) {
		for (Appointment appointment : appointments) {
			if (appointment.getAppointmentId().equals(appointmentId)) {
				if (appointment.getStatus() == AppointmentStatus.CONFIRMED
						|| appointment.getStatus() == AppointmentStatus.PENDING) {
					appointment.setStatus(AppointmentStatus.CANCELLED);
					return true;
				}
			}
		}
		throw new AppointmentNotFoundException("no appointments found");
	}
	
    public List<Appointment> getScheduledAppointments() {
    	return appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.CONFIRMED)
				.collect(Collectors.toList());
    }
    
	
    public List<Appointment> getAvailableSlots() {
    	return appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE)
				.collect(Collectors.toList());
    }


}