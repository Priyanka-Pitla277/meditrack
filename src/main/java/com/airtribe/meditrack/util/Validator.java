package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.exception.InvalidInputException;

public class Validator {

	public static void invalidInput(InvalidInputException exception) {
		System.out.println(exception.getMessage());
	}

	public static void invalidData(InvalidDataException exception) {
		System.out.println(exception.getMessage());
	}

	public static void noDataFound(String message) {
		System.out.println(message);
	}

	public static void noAppointmentFound(AppointmentNotFoundException exception) {
		System.out.println(exception.getMessage());
	}
}