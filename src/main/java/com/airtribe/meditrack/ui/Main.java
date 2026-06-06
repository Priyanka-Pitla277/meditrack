package com.airtribe.meditrack.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.airtribe.meditrack.bill.service.BillUtility;
import com.airtribe.meditrack.bill.service.BillingStrategy;
import com.airtribe.meditrack.bill.service.BillingStrategyFactory;
import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.CloneUtil;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.NotifcationType;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.notification.service.NotificationSystem;
import com.airtribe.meditrack.notification.service.NotificationSystemFactory;
import com.airtribe.meditrack.service.AppointmentManagerService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.DoctorSearchCriteria;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.InvoiceGenerator;
import com.airtribe.meditrack.util.Validator;

public class Main {
	public static void main(String[] args) {
		CSVUtil.readDoctorsFromCsv("src\\csv\\doctors.csv");
		CSVUtil.loadPatientsFromCsv("src\\csv\\patients.csv");
		CSVUtil.loadSlotsFromCsv("src\\csv\\slots.csv");

		Scanner scanner = new Scanner(System.in);
		boolean keepRunning = true;

		while (keepRunning) {
			try {
				printMenu();
				System.out.print("Please select Menu option on which you want to perform action: ");

				// Read full line to prevent scanner buffer mismatch issues
				int choice = Integer.parseInt(scanner.nextLine().trim());
				PatientService patientService = new PatientService();
				DoctorService doctorService = new DoctorService();
				AppointmentManagerService appService = new AppointmentManagerService();


				switch (choice) {
				case 1:
					addDoctor(scanner, doctorService);
					break;
				case 2:
					updateDoctor(scanner, doctorService);
					break;
				case 3:
					getDoctor(scanner, doctorService);
					break;
				case 4:
					deleteDoctor(scanner, doctorService);
					break;
				case 5:
					addPatient(scanner, patientService);
					break;
				case 6:
					updatePatient(scanner, patientService);
					break;
				case 7:
					getPatient(scanner, patientService);
					break;
				case 8:
					deletePatient(scanner, patientService);
					break;
				case 9:
					System.out.println(appService.getAllAvailableSlots());
					break;
				case 10:
					bookAppointment(scanner, appService);
					break;
//				case 6:
//					Patron patron = constructPatron(scanner);
//					patronManagementService.updatePatron(patron);
//					break;
//				case 7:
//					System.out.print("Enter patronid to search item: ");
//					String searchPatronId = scanner.nextLine().trim();
//					patronManagementService.getPatron(searchPatronId);
//					break;
//				case 8:
//					handleCheckoutBook(scanner, libraryService);
//					break;
//				case 9:
//					handleReturnBook(scanner, libraryService);
//					break;
//				case 10:
//					handleReserveBook(scanner, libraryService);
//					break;
//				case 11:
//					LogisticsService logistics = new LogisticsService();
//					handleLogisticsTransfer(scanner, logistics);
//					break;
				case 12:
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
				System.out.println("\n[Error] Date format mismatch. Use dd/MM/yyyy layout.\n");
			} catch (Exception e) {
				System.out.println("\n[Error] System issue: " + e.getMessage());
			}
		}

		scanner.close();

		// add patient
		PatientService service = new PatientService();
//		Patient patient1 = new Patient.Builder().name("sai").age(32).gender("Male").insuranceProvider("CARE").build();
//		service.createPatient(patient1);
//		Patient patient2 = new Patient.Builder().name("vamshi").age(33).gender("Male").insuranceProvider("CARE")
//				.build();
//		service.createPatient(patient2);
//		Patient patient3 = new Patient.Builder().name("arun").age(34).gender("Male").insuranceProvider("CARE").build();
//		service.createPatient(patient3);
//		Patient patient4 = new Patient.Builder().name("raju").age(34).gender("Male").insuranceProvider("CARE").build();
//		service.createPatient(patient4);
		List<Patient> patients = service.findAllPatients();
		for (Patient p : patients) {
			Patient pclone = CloneUtil.cloneCopyOfPatient(p);
			pclone.setId(IdGenerator.generateRandomId());
			System.out.println(p.hashCode() == pclone.hashCode());
			System.out.println(pclone);
			service.createPatient(pclone);

		}

		System.out.println("------ppppppppppppppppppppppppp---------" + patients.size());

		List<Patient> patientsList = service.findAllPatients();
		System.out.println("----------------" + patientsList.size());

		DoctorService doctorService = new DoctorService();
//		Doctor doctor1 = new Doctor.Builder().name("sai").age(32).gender("Male")
//				.specialization(Specialization.CARDIOLOGY).consultationAmount(100.0).build();
//		doctorService.createDoctor(doctor1);
//		Doctor doctor2 = new Doctor.Builder().name("vamshi").age(33).gender("Male")
//				.specialization(Specialization.CARDIOLOGY).consultationAmount(200.0).build();
//		doctorService.createDoctor(doctor2);
//		Doctor doctor3 = new Doctor.Builder().name("arun").age(34).gender("Male")
//				.specialization(Specialization.CARDIOLOGY).yearsOfExperience(10).consultationAmount(200.0).build();
//		doctorService.createDoctor(doctor3);
//		Doctor doctor4 = new Doctor.Builder().name("raju").age(34).gender("Male")
//				.specialization(Specialization.NEUROLOGY).yearsOfExperience(10).consultationAmount(300.0).build();
//		doctorService.createDoctor(doctor4);

		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

//        DateTimeFormatter 24HourFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		// ==========================================
		// SCENARIO A: Formatting an existing Date object to String
		// ==========================================
		LocalDateTime appointment1 = LocalDateTime.of(2026, 6, 15, 22, 30); // 3:30 PM

		String formatted1 = appointment1.format(fomatter);
		LocalDateTime appointment2 = LocalDateTime.of(2026, 6, 15, 16, 30); // 4:30 PM
		String formatted2 = appointment2.format(fomatter);

		List<Doctor> doctors = doctorService.getDoctors();
//        Appointment app1 = new Appointment(doctor1, LocalDateTime.parse(formatted1, fomatter), AppointmentStatus.AVAILABLE);
//        Appointment app2 = new Appointment(doctor1, LocalDateTime.parse(formatted2, fomatter), AppointmentStatus.AVAILABLE);
//        Appointment app3 = new Appointment(doctor2, LocalDateTime.parse(formatted1, fomatter), AppointmentStatus.AVAILABLE);
//        Appointment app4 = new Appointment(doctor3, LocalDateTime.parse(formatted1, fomatter), AppointmentStatus.AVAILABLE);
//        List<Appointment> appts = new ArrayList<Appointment>();
//        appts.add(app1);
//        appts.add(app2);
//        appts.add(app3);
//        appts.add(app4);
//        System.out.println(appts);

		AppointmentManagerService appService = new AppointmentManagerService();
		System.out.println("doctors profiles");
		System.out.println(doctors);
//		appService.createScheduleSlot(doctors.get(0).getId(), LocalDateTime.parse(formatted1, fomatter));
//		appService.createScheduleSlot(doctors.get(0).getId(), LocalDateTime.parse(formatted2, fomatter));
//
//		appService.createScheduleSlot(doctors.get(1).getId(), LocalDateTime.parse(formatted1, fomatter));
//
//		appService.createScheduleSlot(doctors.get().getId(), LocalDateTime.parse(formatted1, fomatter));

		List<Appointment> availableSlots = appService.getAllAvailableSlots();
		System.out.println("slottttttttttttttttttttttttttttttttttt" + availableSlots.size());
		for (Appointment p : availableSlots) {
			Appointment pclone = CloneUtil.cloneOfAppointment(p);
			pclone.setAppointmentId(IdGenerator.generateRandomId());
			pclone.setAppointmentDateTime(appointment1);
			pclone.setStatus(AppointmentStatus.AVAILABLE);
			System.out.println(p.hashCode() == pclone.hashCode());
			System.out.println(pclone);
			appService.addSlot(pclone);

		}
		List<Appointment> availableSlots2 = appService.getAvailableSlotsBySpecialization(Specialization.NEUROLOGY);
		System.out.println("sssssssssssssssssssssssssssssssss" + availableSlots2.size());

		String id = availableSlots.get(0).getAppointmentId();
		Appointment appt1 = appService.bookAppointment(id, patients.get(0).getId());
//		appService.bookAppointment(id,  patients.get(1).getId());
//		appService.bookAppointment(availableSlots.get(1).getAppointmentId(),  patients.get(1).getId());
		// proceed for the payment

		// payment
		BillingStrategy strategy = BillingStrategyFactory.getBillingStrategy("discounted");
		BillUtility billContext = new BillUtility(strategy);
		double amountToBePaid = billContext.executeStrategy(appt1.getDoctor().getConsultationAmount());
		System.out.println("please proceed for the payment of " + amountToBePaid);
		System.out.println("select the payment type UPI, Credit Card, Insurance");
		if (billContext.processPayment("UPI", amountToBePaid)) {
			appt1.setStatus(AppointmentStatus.CONFIRMED);
			// generateBill
			Bill bill = new Bill.Builder().appointment(appt1).consultationFee(appt1.getDoctor().getConsultationAmount())
					.invoiceNumber(InvoiceGenerator.generateInvoiceNumber()).surchargeAmount(0).taxAmount(0)
					.status("PAID").paymentMethod("UPI").build();
			// Notification
			NotificationSystem notificationSystem = NotificationSystemFactory
					.getNotificationSystem(appt1.getPatient().getNotificationType());
			notificationSystem.notifyUser(appt1);
			System.out.println(bill);
			billContext.saveInvoice(bill);

		} else {
			System.out.println("payment failed");
			appt1.setStatus(AppointmentStatus.AVAILABLE);

		}
		System.out.println("scheduled appointments:");
		appService.displayScheduledAppointments();
		appService.getAvailableSlotsByDoctor("vamshi");
		System.out.println(billContext.getInvoice("INV-2026-19E9D2FE736"));
		appService.cancelAppointment("APT-7GPZP");

		System.out.println(appService.searchDoctors(new DoctorSearchCriteria.Builder().minExperience(12).build()));

	}

	private static void bookAppointment(Scanner scanner, AppointmentManagerService appsService) {
		System.out.print("Enter patientId: ");
		String patientId = scanner.nextLine().trim();
		System.out.print("Enter appointmentId: ");
		String appointmentId = scanner.nextLine().trim();
		appsService.bookAppointment(appointmentId, patientId);
	}

	private static void deleteDoctor(Scanner scanner, DoctorService doctorService) {
		System.out.print("Enter doctorId: ");
		String doctorId = scanner.nextLine().trim();
		doctorService.deleteDoctor(doctorId);
		System.out.println("patiedoctornt removed successfully: " + doctorId);
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
	
	private static void getDoctor(Scanner scanner, DoctorService doctorService) {
		System.out.print("Enter patientId: ");
		String doctorId = scanner.nextLine().trim();
		Doctor doctor = doctorService.getDoctor(doctorId);	
		System.out.println("doctor details: "+doctor);
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

		Doctor doctor = new Doctor.Builder().name(name).age(age).gender(gender).email(email).phoneNo(mobileNo).notificationType(NotifcationType.valueOf(alertTypeStr))
				.specialization(Specialization.valueOf(specialization)).yearsOfExperience(exp).consultationAmount(consulationAmount).build();
		service.createDoctor(doctor);
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
