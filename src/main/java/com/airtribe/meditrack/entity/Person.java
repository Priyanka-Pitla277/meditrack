package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.NotifcationType;
import com.airtribe.meditrack.util.IdGenerator;

public class Person {
	private String id;
	private String name;
	private int age;
	private String gender;
	private String email;
	private String phoneNo;
	private NotifcationType notificationType;

	public Person() {
		super();
	}

	public Person(String id, String name, int age, String gender) {
		this.id = IdGenerator.generateRandomId();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	public Person(String name, int age, String gender, String email, String phoneNo,
			NotifcationType notificationType) {
		super();
		this.id = IdGenerator.generateRandomId();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.phoneNo = phoneNo;
		this.notificationType = notificationType;
	}

	public Person(String id, String name, int age, String gender, String email, String phoneNo,
			NotifcationType notificationType) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.phoneNo = phoneNo;
		this.notificationType = notificationType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public NotifcationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotifcationType notificationType) {
		this.notificationType = notificationType;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", email=" + email
				+ ", phoneNo=" + phoneNo + ", notificationType=" + notificationType + "]";
	}
	

}
