package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;

public class Validator {

	public static void invalidInput() {
		try {
			throw new InvalidDataException(Constants.INVALID_SELECTION_TRY_AGAIN);
		} catch (InvalidDataException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void invalidData(InvalidDataException exception) {
		System.out.println(exception.getMessage());
	}
	
	public static void noDataFound(String message) {
		System.out.println(message);
	}
	
	
	public static void noAppointmentFound(String message) {
		try {
			throw new AppointmentNotFoundException(message);
		} catch (AppointmentNotFoundException e) {
			System.out.println(message);
		}
	}
}