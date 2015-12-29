package com.filipwieland.railstatus.tests;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.filipwieland.railstatus.datafeeds.DarwinDataFeed;
import com.filipwieland.railstatus.datafeeds.ServiceCall;
import com.filipwieland.railstatus.events.Event;
import com.filipwieland.railstatus.events.EventEmitter;

public class TrainStatusTest {
	private static BufferedReader xmlIs;

	@BeforeClass
	public static void setUp() throws Exception {
		xmlIs = new BufferedReader(new FileReader("/home/filip/Documents/pPortData.log.2015-12-17-03-13"));
	}

	@AfterClass
	public static void tearDown() throws Exception {
		xmlIs.close();
	}

	@Test
	public void testFew() throws IOException, JAXBException {
		DarwinDataFeed feed = new DarwinDataFeed(null, null, null);
		feed.on(DarwinDataFeed.EVT_TRAIN_STATUS, (Event e) -> {
			Map<String, Object> attrs = e.getAttrs();
			ServiceCall call = (ServiceCall) attrs.get("descriptor");
			System.out.printf("Service Call: Train ID %s scheduled %s at %s at %s, estimated %s, delta %d.\n",
					call.getTrainId(),
					call.getType().name(),
					call.getLocation(),
					call.getPlanned().toString(),
					call.getEstimated().toString(),
					call.getDifference());
			return true;
		});
		feed.on(EventEmitter.EVT_ERROR, (Event e) -> {
			Map<String, Object> attrs = e.getAttrs();
			((Exception) attrs.get("exception")).printStackTrace();
			fail("Error event cauht");
			return true;
		});
		for (int i = 0; i < 5; i++) {
			String line = xmlIs.readLine();
			InputStream cooked = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
			feed.onCookedMessage(cooked);
		}
	}

}
