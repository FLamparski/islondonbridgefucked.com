package com.filipwieland.railstatus.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.filipwieland.railstatus.events.Event;
import com.filipwieland.railstatus.events.EventEmitter;

public class EventEmitterTest extends EventEmitter {

	@Test
	public void test() {
		Object testObj = new Object();
		this.on("test", (Event e) -> { assertEquals(e.getAttrs().get("test"), testObj); return true; });
		Map<String, Object> testAttrs = new HashMap<>();
		testAttrs.put("test", testObj);
		this.emit("test", testAttrs);
	}

}
