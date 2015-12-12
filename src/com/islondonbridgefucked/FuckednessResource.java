package com.islondonbridgefucked;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/fuck")
@Produces(MediaType.APPLICATION_JSON)
public class FuckednessResource {
	private final AtomicLong counter;
	
	public FuckednessResource() {
		counter = new AtomicLong(0);
	}
	
	@GET
	@Timed
	public Fuck sayHello() {
		return new Fuck(counter.getAndIncrement());
	}
}
