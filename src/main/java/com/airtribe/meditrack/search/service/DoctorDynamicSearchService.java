package com.airtribe.meditrack.search.service;

import java.util.List;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.util.DoctorSearchCriteria;

public class DoctorDynamicSearchService implements Searchable {

	private DoctorRepository doctorRepository = new DoctorRepository();

	@Override
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
