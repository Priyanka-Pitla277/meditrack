package com.airtribe.meditrack.service;

import java.util.List;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.repository.DoctorRepository;

public class DoctorService {

	DoctorRepository repository = new DoctorRepository();

	public void createDoctor(Doctor doctor) {
		repository.addDoctor(doctor);
	}

	public List<Doctor> getDoctors() {
		return repository.getDoctors();

	}
	
	public Doctor getDoctor(String doctorId) {
		return repository.getDoctor(doctorId);

	}

	public void updateDoctor(Doctor doctor) {
		repository.updateDoctor(doctor.getId(), doctor);

	}

	public void deleteDoctor(String doctorId) {
		repository.removeDoctor(doctorId);
	}

}
