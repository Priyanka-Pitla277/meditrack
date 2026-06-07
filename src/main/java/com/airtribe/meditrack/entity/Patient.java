package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.NotifcationType;

public class Patient extends Person implements Cloneable  {
    private String medicalHistory;
    private String insuranceProvider;

    // 1. Private constructor forcing instantiation through the Builder
    private Patient(Builder builder) {
        super(builder.name, builder.age, builder.gender,builder.email, builder.phoneNo, builder.notificationType);
        this.medicalHistory = builder.medicalHistory;
        this.insuranceProvider = builder.insuranceProvider;
    }
    

	// Getters and Setters
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public String getInsuranceProvider() { return insuranceProvider; }
    public void setInsuranceProvider(String insuranceProvider) { this.insuranceProvider = insuranceProvider; }

    @Override
    public String toString() { 
        return "------------------------------------------\n" +
               "Patient ID         : " + getId() + "\n" +
               "Name               : " + getName() + "\n" +
               "Age                : " + getAge() + "\n" +
               "Gender             : " + getGender() + "\n" +
               "Email              : " + getEmail() + "\n" +
               "phoneNo            : " + getPhoneNo() + "\n" +
               "notificationType   : " + getNotificationType() + "\n" +
               "Insurance Provider : " + insuranceProvider + "\n" +
               "------------------------------------------";
    }
    

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

    // 2. The Static Nested Builder Class
    public static class Builder {
        // Inherited fields
        private String name;
        private int age;
        private String gender;
     	private String email;
    	private String phoneNo;
    	private NotifcationType notificationType;
        
        // Subclass fields
        private String medicalHistory;
        private String insuranceProvider;

        public Builder() {}


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

		public Builder email(String email) {
			this.email = email;
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

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder medicalHistory(String medicalHistory) {
            this.medicalHistory = medicalHistory;
            return this;
        }

        public Builder insuranceProvider(String insuranceProvider) {
            this.insuranceProvider = insuranceProvider;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }


    }
}