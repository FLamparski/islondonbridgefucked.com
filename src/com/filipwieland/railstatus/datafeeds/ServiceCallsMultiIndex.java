package com.filipwieland.railstatus.datafeeds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * This class only exists because there's no typedef in Java.
 * @author filip
 *
 */
public class ServiceCallsMultiIndex implements Map<String, Map<String, List<ServiceCall>>> {
	private final Map<String, Map<String, List<ServiceCall>>> backingStore;
	
	public ServiceCallsMultiIndex(Map<String, Map<String, List<ServiceCall>>> backingStore) {
		this.backingStore = backingStore;
	}
	
	public synchronized void upsertCall(ServiceCall call) {
		if (!backingStore.containsKey(call.getTrainId()) || backingStore.get(call.getTrainId()) == null) {
			// This service call is for a train we've never seen before, so we need to create its
			// structure in the index.
			Map<String, List<ServiceCall>> locations = new HashMap<>();
			List<ServiceCall> calls = new ArrayList<>(Arrays.asList(call));
			locations.put(call.getLocation(), calls);
			backingStore.put(call.getTrainId(), locations);
		} else {
			// Or, this service call is for a train we've seen previously, so we need to update
			// its entry in the index.
			Map<String, List<ServiceCall>> calls = backingStore.get(call.getTrainId());
			if (!calls.containsKey(call.getLocation()) || calls.get(call.getLocation()) == null) {
				// Similarly, this could be the first time we encounter this location for this train...
				List<ServiceCall> locCalls = new ArrayList<>(Arrays.asList(call));
				calls.put(call.getLocation(), locCalls);
			} else {
				// ...or not, and we may need to update an existing estimate...
				List<ServiceCall> locCalls = calls.get(call.getLocation());
				boolean updated = false;
				for (int i = 0; i < locCalls.size(); i++) {
					ServiceCall existing = locCalls.get(i);
					if (existing.getType() == call.getType()) {
						locCalls.set(i, call);
						updated = true;
					}
				}
				// ... or, in case it's a call type we've not seen before for this (train, location) we need to add it.
				if (!updated) {
					locCalls.add(call);
				}
			}
		}
	}

	@Override
	public void clear() {
		backingStore.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return backingStore.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return backingStore.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, Map<String, List<ServiceCall>>>> entrySet() {
		return backingStore.entrySet();
	}

	@Override
	public synchronized Map<String, List<ServiceCall>> get(Object key) {
		return backingStore.get(key);
	}

	@Override
	public boolean isEmpty() {
		return backingStore.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return backingStore.keySet();
	}

	@Override
	public synchronized Map<String, List<ServiceCall>> put(String key, Map<String, List<ServiceCall>> value) {
		return backingStore.put(key, value);
	}

	@Override
	public synchronized void putAll(Map<? extends String, ? extends Map<String, List<ServiceCall>>> m) {
		backingStore.putAll(m);
	}

	@Override
	public synchronized Map<String, List<ServiceCall>> remove(Object key) {
		return backingStore.remove(key);
	}

	@Override
	public synchronized int size() {
		return backingStore.size();
	}

	@Override
	public Collection<Map<String, List<ServiceCall>>> values() {
		return backingStore.values();
	}

	/**
	 * @return A stream of all ServiceCalls in the index.
	 */
	@SuppressWarnings("resource")
	public synchronized Stream<ServiceCall> stream() {
		Stream<ServiceCall> str = null;
		for (Map<String, List<ServiceCall>> x : backingStore.values()) {
			for (List<ServiceCall> y : x.values()) {
				if (str == null) str = y.stream();
				else str = Stream.concat(str, y.stream());
			}
		}
		return str;
	}
	
}
