package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore<T> {
	private Map<String, T> items = new HashMap<>();

	public void add(String id, T item) {
		items.put(id, item);
	}
	
	public List<T> getAllItems() {
		return new ArrayList<>(items.values());
	}

	public T get(String id) {
		return items.get(id);
	}

	public void update(String id, T newItem) {
		items.put(id, newItem);
	}

	public void remove(String id) {
		items.remove(id);
	}
}
