package com.airtribe.meditrack.enums;

public enum NotifcationType {
	SMS("SMS"), EMAIL("EMAIL");

	private final String type;

	// Constructor
	NotifcationType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
