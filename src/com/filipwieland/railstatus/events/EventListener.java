package com.filipwieland.railstatus.events;

@FunctionalInterface
public interface EventListener {
	public boolean callback(Event ev);
}
