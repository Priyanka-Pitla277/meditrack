package com.airtribe.meditrack.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.airtribe.meditrack.bill.service.BillUtility;
import com.airtribe.meditrack.bill.service.BillingStrategy;
import com.airtribe.meditrack.bill.service.BillingStrategyFactory;
import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.NotifcationType;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.search.service.DoctorDynamicSearchService;
import com.airtribe.meditrack.service.AppointmentManagerService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.DoctorSearchCriteria;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

public class Main {
	public static void main(String[] args) {
		loadData(args);
		Scanner scanner = new Scanner(System.in);
		boolean keepRunning = true;

		while (keepRunning) {
			try {
				printMenu();
				System.out.print("Please select Menu option on which you want to perform action: ");
				int choice = Integer.parseInt(scanner.nextLine().trim());
				PatientService patientService = new PatientService();
				DoctorService doctorService = new DoctorService();
				AppointmentManagerService appService = new AppointmentManagerService();
				Searchable searchService = new  DoctorDynamicSearchService();

				switch (choice) {
				case 1:
					getAllDoctors(doctorService);
					break;
				case 2:
					addDoctor(scanner, doctorService);
					break;
				case 3:
					updateDoctor(scanner, doctorService);
					break;
				case 4:
					getDoctor(scanner, doctorService);
					break;
				case 5:
					deleteDoctor(scanner, doctorService);
					break;
				case 6:
					getAllPatients(patientService);
					break;
				case 7:
					addPatient(scanner, patientService);
					break;
				case 8:
					updatePatient(scanner, patientService);
					break;
				case 9:
					getPatient(scanner, patientService);
					break;
				case 10:
					deletePatient(scanner, patientService);
					break;
				case 11:
					getAllAvailableSlots(appService);
					break;
				case 12:
					addAppointmentSlot(scanner, appService);
					break;
				case 13:
					bookAppointment(scanner, appService);
					break;
				case 14:
					getAppointment(scanner, appService);
					break;
				case 15:
					cancelAppointment(scanner, appService);
					break;
				case 16:
					doctorDynamicSearch(scanner, searchService);
					break;
				case 0:
					keepRunning = false;
					System.out.println("Exiting the interactive menu window.");
					break;

				default:
					Validator.invalidInput();
					break;
				}
			} catch (IllegalArgumentException e) {
				System.out.println("\n[Error] Argument error: " + e.getMessage() + "\n");
			} catch (DateTimeParseException e) {
				System.out.println("\n[Error] Date format mismatch. Use dd-MM-yyyy HH:mm layout.\n");
			} catch (Exception e) {
				System.out.println("\n[Error] System issue: " + e.getMessage());
			}
		}
		scanner.close();

	}

	private static void doctorDynamicSearch(Scanner scanner, Searchable searchService) {
		System.out.print("Enter name: ");
		String name = scanner.nextLine().trim();
		System.out.print("Enter min years of experience: ");
		int minExp = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Enter consultation amount: ");
		double consulationAmount = Double.parseDouble(scanner.nextLine().trim());
		System.out.print("Enter specialization: ");
		String specialization = scanner.nextLine().trim().toUpperCase();
		DoctorSearchCriteria searchCriteria = new DoctorSearchCriteria.Builder().maxConsultationFee(consulationAmount)
				.minExperience(minExp).name(name).specialization(specialization).build();
		System.out.println(searchService.searchDoctors(searchCriteria));
		
	}


	private static void cancelAppointment(Scanner scanner, AppointmentManagerService appService) {
		System.out.print("Enter appointment Id: ");
		String appointmentId = scanner.nextLine().trim();
		appService.cancelAppointment(appointmentId);		
	}
	

	private static void loadData(String[] args) {
		if (args.length > 0) {
			if (args[0].equals(Constants.LOAD_DATA)) {
				CSVUtil.readDoctorsFromCsv("src\\csv\\doctors.csv");
				CSVUtil.loadPatientsFromCsv("src\\csv\\patients.csv");
				CSVUtil.loadSlotsFromCsv("src\\csv\\slots.csv");
			}
		}
	}

	private static void bookAppointment(Scanner scanner, AppointmentManagerService appsService) {
		System.out.print("Enter patientId: ");
		String patientId = scanner.nextLine().trim();
		System.out.print("Enter appointmentId: ");
		String appointmentId = scanner.nextLine().trim();
		Appointment appointment = appsService.bookAppointment(appointmentId, patientId);
		if(null != appointment) {
			if(appointment.getStatus()==AppointmentStatus.PENDING) {
				BillingStrategy strategy = BillingStrategyFactory.getBillingStrategy("discounted");
				BillUtility billUtility = new BillUtility(strategy);
				double amountToBePaid = billUtility.executeStrategy(appointment.getDoctor().getConsultationAmount());
				System.out.println("please proceed for the payment of " + amountToBePaid);
				System.out.println("select the payment type UPI/Credit Card/Insurance: ");
				String paymentType = scanner.nextLine().trim();
				billUtility.processPaymentAndgenerateBill(paymentType, amountToBePaid, appointment);
		}
	}
	}

	private static void deleteDoctor(Scanner scanner, DoctorService doctorService) {
		System.out.print("Enter doctorId: ");
		String doctorId = scanner.nextLine().trim();
		doctorService.deleteDoctor(doctorId);
	}
	

	private static void getAllDoctors(DoctorService doctorService) {
		System.out.println("****Doctors List****");
		doctorService.getDoctors();
	}
	
	private static void getAllPatients(PatientService patientService) {
		patientService.findAllPatients();
	}
	
	private static void getAllAvailableSlots(AppointmentManagerService appService) {
		appService.getAllAvailableSlots();
	}

	private static void deletePatient(Scanner scanner, PatientService patientService) {
		System.out.print("Enter patientId: ");
		String patientId = scanner.nextLine().trim();
		patientService.deletePatient(patientId);
		System.out.println("patient removed successfully: " + patientId);
	}

	private static void getPatient(Scanner scanner, PatientService patientService) {
		System.out.print("Enter patientId: ");
		String patientId = scanner.nextLine().trim();
		Patient patient = patientService.getPatient(patientId);	
		System.out.println("patient details: "+patient);
	}
	
	private static void getAppointment(Scanner scanner, AppointmentManagerService appService) {
		System.out.print("Enter appointment Id: ");
		String appointmentId = scanner.nextLine().trim();
		appService.getAppointment(appointmentId);
	}
	
	private static void getDoctor(Scanner scanner, DoctorService doctorService) {
		System.out.print("Enter doctor Id: ");
		String doctorId = scanner.nextLine().trim();
		Doctor doctor = doctorService.getDoctor(doctorId);	
		System.out.println(null == doctor?"No doctor info available for the selected ID: "+doctorId:doctor);

	}
	

	private static void printMenu() {
		System.out.println("Select the operation you want to perform on book: " + Constants.DOCTOR_MENU);
		System.out.println("Select the operation you want to perform on patron: " + Constants.PATIENT_MENU);
		System.out.println("Select the operation you want to perform on services: " + Constants.APPOINTMENT_MENU);
	}
	
	private static void addPatient(Scanner scanner, PatientService service) {
		System.out.print("Enter name: ");
		String name = scanner.nextLine().trim();
		System.out.print("Enter email: ");
		String email = scanner.nextLine().trim();
		System.out.print("Enter mobile: ");
		String mobileNo = scanner.nextLine().trim();
		System.out.print("Enter alertType (SMS/EMAIL): ");
		String alertTypeStr = scanner.nextLine().trim().toUpperCase();
		System.out.print("Enter age: ");
		int age = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Enter gender (Male/Female): ");
		String gender = scanner.nextLine().trim();
		System.out.print("Enter insuranceProvider: ");
		String insuranceProvider = scanner.nextLine().trim();

		Patient patient = new Patient.Builder().name(name).email(email).phoneNo(mobileNo)
				.notificationType(NotifcationType.valueOf(alertTypeStr)).age(age).gender(gender)
				.insuranceProvider(insuranceProvider).build();
		service.createPatient(patient);
	}
	
	private static void addDoctor(Scanner scanner, DoctorService service) {
		System.out.print("Enter name: ");
		String name = scanner.nextLine().trim();
		System.out.print("Enter email: ");
		String email = scanner.nextLine().trim();
		System.out.print("Enter mobile: ");
		String mobileNo = scanner.nextLine().trim();
		System.out.print("Enter alertType (SMS/EMAIL): ");
		String alertTypeStr = scanner.nextLine().trim().toUpperCase();
		System.out.print("Enter age: ");
		int age = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Enter gender (Male/Female): ");
		String gender = scanner.nextLine().trim();
		System.out.print("Enter specialization): ");
		String specialization = scanner.nextLine().trim();
		System.out.print("Enter years of experience: ");
		int exp = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Enter consultation amount: ");
		double consulationAmount = Double.parseDouble(scanner.nextLine().trim());

		Doctor doctor = new Doctor.Builder().id(IdGenerator.generateRandomId()).name(name).age(age).gender(gender).email(email).phoneNo(mobileNo).notificationType(NotifcationType.valueOf(alertTypeStr))
				.specialization(Specialization.valueOf(specialization)).yearsOfExperience(exp).consultationAmount(consulationAmount).build();
		service.createDoctor(doctor);
	}
	
	private static void addAppointmentSlot(Scanner scanner, AppointmentManagerService appService) {

		System.out.print("Enter date (dd-MM-yyyy HH:mm)format ");
		String dateInput = scanner.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		System.out.print("Enter doctor Id: ");
		String doctorId = scanner.nextLine().trim();

		LocalDateTime date = LocalDateTime.parse(dateInput, formatter);
		appService.createScheduleSlot(doctorId, date);

	}
	
	private static void updatePatient(Scanner scanner, PatientService patientService) {
		System.out.print("Enter patientId: ");
		String patientId = scanner.nextLine().trim();
		Patient patient = patientService.getPatient(patientId);
		System.out.print("Enter name: ");
		String name = scanner.nextLine().trim().isEmpty()? scanner.nextLine().trim():patient.getName();
		System.out.print("Enter email: ");
		String email =scanner.nextLine().trim().isEmpty()? scanner.nextLine().trim():patient.getEmail();
		System.out.print("Enter gender: ");
		String gender =scanner.nextLine().trim().isEmpty()? scanner.nextLine().trim():patient.getGender();
		System.out.print("Enter age: ");
		int age =Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Enter mobile: ");
		String mobileNo = scanner.nextLine().trim().isEmpty()? scanner.nextLine().trim():patient.getPhoneNo();
		System.out.print("Enter insurance provider: ");
		String insurancePorvider = scanner.nextLine().trim().isEmpty()? scanner.nextLine().trim():patient.getInsuranceProvider();
		System.out.print("Enter alertType (SMS/EMAIL): ");
		String alertTypeStr = scanner.nextLine().trim().toUpperCase() != null? scanner.nextLine().trim().toUpperCase():patient.getNotificationType().toString();

		Patient updatedPatient = new Patient.Builder().name(name).email(email).phoneNo(mobileNo)
				.notificationType(NotifcationType.valueOf(alertTypeStr)).age(age).gender(gender)
				.insuranceProvider(insurancePorvider).build();

		patientService.updatePatient(updatedPatient);
	}
	
	private static void updateDoctor(Scanner scanner, DoctorService doctorService) {
		System.out.print("Enter doctrId: ");
		String doctorId = scanner.nextLine().trim();
		Doctor doctor = doctorService.getDoctor(doctorId);
		System.out.print("Enter name: ");
		String name = scanner.nextLine().trim().isEmpty() ? scanner.nextLine().trim() : doctor.getName();
		System.out.print("Enter email: ");
		String email = scanner.nextLine().trim().isEmpty() ? scanner.nextLine().trim() : doctor.getEmail();
		System.out.print("Enter gender: ");
		String gender = scanner.nextLine().trim().isEmpty() ? scanner.nextLine().trim() : doctor.getGender();
		System.out.print("Enter age: ");
		int age = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Enter Years of exp: ");
		int exp = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Enter mobile: ");
		String mobileNo = scanner.nextLine().trim().isEmpty() ? scanner.nextLine().trim() : doctor.getPhoneNo();
		System.out.print("Enter alertType (SMS/EMAIL): ");
		String alertTypeStr = scanner.nextLine().trim().toUpperCase() != null ? scanner.nextLine().trim().toUpperCase()
				: doctor.getNotificationType().toString();

		Doctor updatedDoctor = new Doctor.Builder().name(name).email(email).phoneNo(mobileNo)
				.notificationType(NotifcationType.valueOf(alertTypeStr)).age(age).gender(gender).yearsOfExperience(exp)
				.build();

		doctorService.updateDoctor(updatedDoctor);
	}
}
