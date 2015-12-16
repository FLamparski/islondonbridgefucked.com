package com.filipwieland.railstatus;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/disruptions")
@Produces(MediaType.APPLICATION_JSON)
public class DisrupitonResource {
	private final AtomicLong counter;
	
	public DisrupitonResource() {
		counter = new AtomicLong(0);
	}
	
	@GET
	@Timed
	public StationStatus sayHello() {
		return new StationStatus(counter.getAndIncrement());
	}
}
