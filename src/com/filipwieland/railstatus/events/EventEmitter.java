package com.filipwieland.railstatus.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventEmitter {
	public static final String EVT_ERROR = "error";
	
	protected Map<String, List<EventListener>> eventListeners = new HashMap<>();
	
	protected boolean emit(String eventName, Map<String, Object> attrs) {
		List<EventListener> listeners = eventListeners.get(eventName);
		return listeners.stream().anyMatch((EventListener listener) -> listener.callback(new Event(eventName, attrs)));
	}
	
	public void on(String eventName, EventListener listener) {
		List<EventListener> listeners = eventListeners.get(eventName);
		if (listeners == null) {
			listeners = new LinkedList<>();
			eventListeners.put(eventName, listeners);
		}
		listeners.add(listener);
	}
}
