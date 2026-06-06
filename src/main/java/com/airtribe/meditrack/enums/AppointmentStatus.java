package com.airtribe.meditrack.enums;

public enum AppointmentStatus {
	AVAILABLE("Available"),PENDING("Pending"), CONFIRMED("Confirmed"), CANCELLED("Cancelled");

	private final String displayName;

	// Constructor
	AppointmentStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}