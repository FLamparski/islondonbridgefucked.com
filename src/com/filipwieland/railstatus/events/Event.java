package com.filipwieland.railstatus.events;

import java.util.Map;

public final class Event {
	private final String name;
	private final Map<String, Object> attrs;
	
	public Event(String name, Map<String, Object> attrs) {
		super();
		this.name = name;
		this.attrs = attrs;
	}

	public String getName() {
		return name;
	}
	
	public Map<String, Object> getAttrs() {
		return attrs;
	}
}
