package com.airtribe.meditrack.service;

import java.util.List;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.repository.PatientRepository;

public class PatientService {

	PatientRepository repository = new PatientRepository();

	public void createPatient(Patient patient) {
		repository.addPatient(patient);
	}

	public Patient getPatient(String patientId) {
		return repository.findPatient(patientId);

	}

	public void updatePatient(Patient patient) {
		repository.updatePatient(patient.getId(), patient);

	}

	public void deletePatient(String patientId) {
		repository.removePatient(patientId);

	}

	public List<Patient> findAllPatients() {
		System.out.println(repository.findAllPatients());
		return repository.findAllPatients();
	}
}
