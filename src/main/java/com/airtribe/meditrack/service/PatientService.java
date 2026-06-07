package com.airtribe.meditrack.service;

import java.util.List;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.repository.PatientRepository;
import com.airtribe.meditrack.util.Validator;

public class PatientService {

	PatientRepository repository = new PatientRepository();

	public void createPatient(Patient patient) {
		repository.addPatient(patient);
		System.out.println("patient data added successfully: " + patient.getId());
	}

	public Patient getPatient(String patientId) {
		try {
			return repository.findPatient(patientId);
		} catch (InvalidDataException e) {
			Validator.invalidData(e);
		}
		return null;

	}

	public void updatePatient(Patient patient) {
		repository.updatePatient(patient.getId(), patient);
	}

	public void deletePatient(String patientId) {
		try {
			repository.removePatient(patientId);
		} catch (InvalidDataException e) {
			Validator.invalidData(e);
		}

	}

	public List<Patient> findAllPatients() {
		System.out.println(repository.findAllPatients());
		return repository.findAllPatients();
	}
}
