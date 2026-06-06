package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.NotifcationType;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.service.AppointmentManagerService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

	public static List<Doctor> readDoctorsFromCsv(String filePath) {
		List<Doctor> doctors = new ArrayList<>();
		String line;
		boolean isHeader = true;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while ((line = br.readLine()) != null) {
				// Skip the first row containing the column headers
				if (isHeader) {
					isHeader = false;
					continue;
				}

				// Split the row by commas
				String[] data = line.split(",");

				// Ensure the row has all necessary columns to avoid
				// ArrayIndexOutOfBoundsException
				if (data.length >= 6) {
					try {
						// Parse values out of strings safely
						String id = data[0].trim();
						String name = data[1].trim();
						String specialization = data[2].trim();
						int experience = Integer.parseInt(data[3].trim());
						String license = data[4].trim();
						double fee = Double.parseDouble(data[5].trim());
						String gender = data[6].trim();
						String email = data[7].trim();
						String phoneNo = data[8].trim();
						int age = Integer.parseInt(data[9].trim());

						// Reconstruct the Doctor entity using your Builder pattern
						Doctor doctor = new Doctor.Builder().name(name).id(id)
								.specialization(Specialization.valueOf(specialization)).yearsOfExperience(experience)
								.licenseNumber(license).consultationAmount(fee).phoneNo(phoneNo).email(email)
								.gender(gender).age(age).build();

//                        doctors.add(doctor);
						DoctorService doctorService = new DoctorService();
						doctorService.createDoctor(doctor);
					} catch (NumberFormatException e) {
						System.err.println("Skipping malformed data row due to numeric error: " + line);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading the CSV file: " + e.getMessage());
		}

		return doctors;
	}

	public static void loadPatientsFromCsv(String filePath) {
		String line;
		boolean isHeader = true;
		PatientService patientService = new PatientService();
		// Try-with-resources automatically closes the file stream when finished
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			while ((line = br.readLine()) != null) {
				// 1. Skip the first row containing column headers
				if (isHeader) {
					isHeader = false;
					continue;
				}

				// 2. Split the row by commas
				String[] data = line.split(",");

				// 3. Ensure the row has all 4 columns to avoid index errors
				if (data.length >= 4) {
					try {
						String name = data[0].trim();
						int age = Integer.parseInt(data[1].trim());
						String gender = data[2].trim();
						String insurance = data[3].trim();
						String email = data[4].trim();
						String phoneNo = data[5].trim();
						String notificationType = data[6].trim();

						// 4. Feed the CSV values directly into your Patient Builder
						Patient patient = new Patient.Builder().name(name).age(age).gender(gender)
								.insuranceProvider(insurance).email(email).phoneNo(phoneNo)
								.notificationType(NotifcationType.valueOf(notificationType)).build();

						// 5. Register the fresh object into your service engine
						patientService.createPatient(patient);
						System.out.println("Successfully registered patient: " + name);

					} catch (NumberFormatException e) {
						System.err.println("Skipping malformed data row (invalid age format): " + line);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Critical error reading the patient CSV database: " + e.getMessage());
		}
	}

	public static void loadSlotsFromCsv(String filePath) {
		String line;
		boolean isHeader = true;
		AppointmentManagerService appService = new AppointmentManagerService();

		// Optional: If using a custom date pattern instead of standard ISO format
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy
		// HH:mm");

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while ((line = br.readLine()) != null) {
				// 1. Skip the structural CSV header line
				if (isHeader) {
					isHeader = false;
					continue;
				}

				// 2. Parse out token segments
				String[] tokens = line.split(",");
				if (tokens.length >= 2) {
					try {
						String doctorId = tokens[0].trim();
						String rawDateTime = tokens[1].trim();

						// 3. Convert String text into executable LocalDateTime object
						LocalDateTime slotTime = LocalDateTime.parse(rawDateTime);
						// If using custom pattern: LocalDateTime.parse(rawDateTime, formatter);

						// 4. Register the generated slot directly with the target Doctor
						appService.createScheduleSlot(doctorId, slotTime);
						System.out.println("Slot created successfully for Doctor ID: " + doctorId + " at " + slotTime);

					} catch (Exception e) {
						System.err.println(
								"Skipping row layout due to parsing errors: " + line + " -> " + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Critical IO exception error reading schedule matrix tracking file: " + e.getMessage());
		}
	}

}