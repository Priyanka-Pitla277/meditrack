package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.airtribe.meditrack.exception.InvalidDataException;

public class DataStore<T> {
	private Map<String, T> items = new HashMap<>();

	public void add(String id, T item) {
		items.put(id, item);
	}
	
	public List<T> getAllItems() {
		return new ArrayList<>(items.values());
	}

	public T get(String id) {
		if (null != items.get(id)) {
			return items.get(id);
		} else {
			throw new InvalidDataException("No data found for " + id);
		}
	}

	public T update(String id, T newItem) {
		return items.put(id, newItem);

	}

	public void remove(String id) {
		if (null == items.remove(id)) {
			throw new InvalidDataException("No data found for " + id);
		}
		System.out.println("removed data for " + id);
	}
}
