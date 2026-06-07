package com.airtribe.meditrack.service;

import java.util.List;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.util.Validator;

public class DoctorService {

	DoctorRepository repository = new DoctorRepository();

	public void createDoctor(Doctor doctor) {
		repository.addDoctor(doctor);
	}

	public List<Doctor> getDoctors() {
		System.out.println(repository.getDoctors());
		return repository.getDoctors();

	}
	
	public Doctor getDoctor(String doctorId) {
		try {
			return repository.getDoctor(doctorId);
		} catch (InvalidDataException e) {
			Validator.invalidData(e);
		}
		return null;

	}

	public void updateDoctor(Doctor doctor) {
		repository.updateDoctor(doctor.getId(), doctor);
		System.out.println("updated doctor details:" + doctor);
	}

	public void deleteDoctor(String doctorId) {
		try {
			repository.removeDoctor(doctorId);
		} catch (InvalidDataException e) {
			Validator.invalidData(e);
		}
	}

}
