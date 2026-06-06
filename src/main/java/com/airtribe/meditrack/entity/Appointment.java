package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.util.IdGenerator;

//deep cloning
public class Appointment implements Cloneable{
	private String appointmentId;
	private Patient patient;
	private Doctor doctor;
	private LocalDateTime appointmentDateTime;
	private AppointmentStatus status;

	public Appointment(Doctor doctor, LocalDateTime appointmentDateTime, AppointmentStatus status) {
		super();
		this.appointmentId = "APT-"+IdGenerator.generateRandomId();
		this.doctor = doctor;
		this.appointmentDateTime = appointmentDateTime;
		this.status = status;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDateTime getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
	    return "\n==========================================\n" +
	           "         APPOINTMENT DETAILS              \n" +
	           "==========================================\n" +
	           "Appointment ID : " + appointmentId + "\n" +
	           "Date & Time    : " + appointmentDateTime + "\n" +
	           "Current Status : " + status + "\n" +
	           "------------------------------------------\n" +
	           "Doctor         : " + (doctor != null ? doctor.getName() : "Unassigned") + "\n" +
	           "Patient        : " + (patient != null ? patient.getName() : "No Patient Registered") + "\n" +
	           "==========================================";
	}
	
	//deep cloning
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		Appointment cloned = (Appointment) super.clone();
		cloned.patient = patient != null?(Patient) patient.clone():null; // cloning nested object
		cloned.doctor = doctor != null?(Doctor) doctor.clone():null; // cloning nested object
		return cloned;
	}

}
