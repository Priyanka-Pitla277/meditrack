package com.airtribe.meditrack.entity;

public class CloneUtil {

	public static Patient cloneCopyOfPatient(Patient patient) {
		try {
			return (Patient) patient.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return patient;
	}

	public static Appointment cloneOfAppointment(Appointment apt) {
		try {
			return (Appointment) apt.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return apt;
	}
}
