package com.filipwieland.railstatus;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.filipwieland.railstatus.datafeeds.ServiceCall;
import com.filipwieland.railstatus.datafeeds.ServiceCallsMultiIndex;

@Path("/disruptions")
@Produces(MediaType.APPLICATION_JSON)
public class DisruptionResource {
	private final static int MINUTE = 60 * 1000;
	private final static Logger logger = LoggerFactory.getLogger(DisruptionResource.class); 
	
	private final AtomicLong counter;
	
	private final ServiceCallsMultiIndex serviceCalls;
	
	public DisruptionResource(ServiceCallsMultiIndex serviceCalls) {
		counter = new AtomicLong(0);
		this.serviceCalls = serviceCalls;
	}
	
	@GET
	@Timed
	public StationStatus sayHello() {
		long countDelayed;
		long countSeverelyDelayed;
		int size;
		synchronized (serviceCalls) {
			countDelayed = serviceCalls.stream().filter((ServiceCall call) -> call.getDifference() > MINUTE).count();
			countSeverelyDelayed = serviceCalls.stream().filter((ServiceCall call) -> call.getDifference() > 15 * MINUTE).count();
			size = serviceCalls.size();
		}
		double amtDelayed = size > 0 ? (double) countDelayed / size : 0;
		double amtSeverelyDelayed = size > 0 ? (double) countSeverelyDelayed / size : 0;
		int shitconLevel = 4;
		if (amtDelayed >= 0.1) {
			shitconLevel = 3;
		}
		if (amtSeverelyDelayed >= 0.1 || amtDelayed >= 0.4) {
			shitconLevel = 2;
		}
		if (amtSeverelyDelayed >= 0.3 || amtDelayed >= 0.6) {
			shitconLevel = 1;
		}
		logger.info(String.format("GET /disruptions: delayed: %d/%.2f%%, severe: %d/%.2f%%, shitcon: %d, queue size: %d", countDelayed, amtDelayed * 100, countSeverelyDelayed, amtSeverelyDelayed * 100, shitconLevel, serviceCalls.size()));
		return new StationStatus(counter.getAndIncrement(), shitconLevel);
	}
}
