package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.NotifcationType;
import com.airtribe.meditrack.enums.Specialization;

public class Doctor extends Person implements Cloneable{
	private Specialization specialization;
	private int yearsOfExperience;
	private String licenseNumber;
	private double consultationAmount;

	// 1. Private constructor accepting the Builder instance
	private Doctor(Builder builder) {
		super(builder.id, builder.name, builder.age, builder.gender, builder.email, builder.phoneNo,
				builder.notificationType);
		this.specialization = builder.specialization;
		this.yearsOfExperience = builder.yearsOfExperience;
		this.licenseNumber = builder.licenseNumber;
		this.consultationAmount = consultationAmount == 0 ? 500.0 : builder.consultationAmount;
	}

	// Existing Getters and Setters
	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public double getConsultationAmount() {
		return consultationAmount;
	}

	public void setConsultationAmount(double consultationAmount) {
		this.consultationAmount = consultationAmount;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
	    return "------------------------------------------\n" +
	           "ID                 : " + getId() + "\n" +
	           "Full Name          : " + getName() + "\n" +
	           "Age                : " + getAge() + " years\n" +
	           "Gender             : " + getGender() + "\n" +
	           "Email Address      : " + getEmail() + "\n" +
	           "Phone Number       : " + getPhoneNo() + "\n" +
	           "specialization     : " + getSpecialization() + "\n" +
	           "experience         : " + getYearsOfExperience() + "\n" +
	           "consultation amount: " + getConsultationAmount() + "\n" +
	           "Notification Pref : " + (getNotificationType() != null ? getNotificationType() : "Not Configured") + "\n" +
	           "------------------------------------------";
	}

	// 2. The Static Internal Builder Class
	public static class Builder {
		public String id;
		// Person fields (Inherited)
		private String name;
		private int age;
		private String gender;

		// Doctor fields (Subclass specific)
		private Specialization specialization;
		private int yearsOfExperience;
		private String licenseNumber;
		private String email;
		private String phoneNo;
		private NotifcationType notificationType;
		private double consultationAmount;

		public Builder() {
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder age(int age) {
			this.age = age;
			return this;
		}

		public Builder gender(String gender) {
			this.gender = gender;
			return this;
		}

		public Builder specialization(Specialization specialization) {
			this.specialization = specialization;
			return this;
		}

		public Builder yearsOfExperience(int yearsOfExperience) {
			this.yearsOfExperience = yearsOfExperience;
			return this;
		}

		public Builder licenseNumber(String licenseNumber) {
			this.licenseNumber = licenseNumber;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;

		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder phoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
			return this;

		}

		public Builder notificationType(NotifcationType notificationType) {
			this.notificationType = notificationType;
			return this;
		}

		public Builder consultationAmount(double consultationAmount) {
			this.consultationAmount = consultationAmount;
			return this;
		}

		// Terminal step to initialize the Doctor entity
		public Doctor build() {
			return new Doctor(this);
		}

	}

}