package com.filipwieland.railstatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.filipwieland.railstatus.datafeeds.ServiceCallsMultiIndex;

/**
 * This resource simply dumps the internal data structure
 * used to analyse train data. It may be used as an API endpoint
 * for further consumption by third-party applications,
 * or for diagnostics of the service itself.
 * @author filip
 *
 */
@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
public class DataStructureResource {
	private final ServiceCallsMultiIndex idx;
	
	public DataStructureResource(ServiceCallsMultiIndex idx) {
		this.idx = idx;
	}
	
	@GET
	@Timed
	public ServiceCallsMultiIndex get() {
		return idx;
	}
}
