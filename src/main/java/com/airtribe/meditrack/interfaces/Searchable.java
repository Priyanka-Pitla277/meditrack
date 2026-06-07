package com.airtribe.meditrack.interfaces;

import java.util.List;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.util.DoctorSearchCriteria;

public interface Searchable {

	public List<Doctor> searchDoctors(DoctorSearchCriteria criteria);
	
	public default void startSearch()
	{
		System.out.println("starting search");
	}

}
