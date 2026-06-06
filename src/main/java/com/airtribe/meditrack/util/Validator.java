package com.airtribe.meditrack.util;

import com.airtribe.learntrack.exception.InvalidDataException;
import com.airtribe.meditrack.constants.Constants;

public class Validator {

	public static void invalidInput() {
		try {
			throw new InvalidDataException(Constants.INVALID_SELECTION_TRY_AGAIN);
		} catch (InvalidDataException e) {
			System.out.println(e.getMessage());
		}
	}
}