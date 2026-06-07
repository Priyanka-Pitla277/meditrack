package com.airtribe.meditrack.repository;

import java.util.List;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.DataStore;

public class PatientRepository {
    private static DataStore<Patient> patientStore = new DataStore<>();

    public void addPatient(Patient patient) {
        patientStore.add(patient.getId(), patient);
    }

    public Patient findPatient(String id) {
        return patientStore.get(id);
    }

    public void updatePatient(String id, Patient updatedPatient) {
        patientStore.update(id, updatedPatient);
    }

    public void removePatient(String id) {
        patientStore.remove(id);
    }
    

    public List<Patient> findAllPatients() {
        return patientStore.getAllItems();
    }
}
