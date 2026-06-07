package com.airtribe.meditrack.notification.service;

import com.airtribe.meditrack.entity.Appointment;

public class SMSNotificationSystem implements NotificationSystem {

	@Override
	public void notifyUser(Appointment appointment) {
		System.out.println("SMS: Received with Booking details:" + appointment.getPatient().getPhoneNo());
		System.out.println(appointment);

	}

}
