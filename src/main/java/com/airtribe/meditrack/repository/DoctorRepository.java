package com.airtribe.meditrack.repository;

import java.util.List;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.util.DataStore;

public class DoctorRepository {
	
    private static DataStore<Doctor> doctorStore = new DataStore<>();

    public void addDoctor(Doctor doctor) {
    	doctorStore.add(doctor.getId(), doctor);
        System.out.println("doctor added successfully:" +doctor.getId());

    }

    public Doctor getDoctor(String id) {
        return doctorStore.get(id);
    }

    public List<Doctor> getDoctors() {
        return doctorStore.getAllItems();
    }
    
    public Doctor updateDoctor(String id, Doctor updatedDoctor) {
    	return doctorStore.update(id, updatedDoctor);
    }

    public void removeDoctor(String id) {
    	doctorStore.remove(id);
    }

}
