package com.airtribe.meditrack.notification.service;

import com.airtribe.meditrack.entity.Appointment;

public class EmailNotificationSystem implements NotificationSystem {

	@Override
	public void notifyUser(Appointment appointment) {
		// TODO Auto-generated method stub
		System.out.println("EMAIL: Received with Booking details:" + appointment.getPatient().getEmail());
		System.out.println(appointment);

	}

}
