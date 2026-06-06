package com.airtribe.meditrack.notification.service;

import com.airtribe.meditrack.enums.NotifcationType;

public class NotificationSystemFactory {

	public static NotificationSystem getNotificationSystem(NotifcationType notificationType) {
		switch (String.valueOf(notificationType)) {
		case "SMS":
			return new SMSNotificationSystem();
		case "EMAIL":
			return new EmailNotificationSystem();
		default:
			throw new IllegalArgumentException("Unknown notificationType");
		}
	}
}
