package com.filipwieland.railstatus.tests;

import java.util.Date;

import com.codahale.metrics.health.HealthCheck;
import com.filipwieland.railstatus.datafeeds.ServiceCall;
import com.filipwieland.railstatus.datafeeds.ServiceCallsMultiIndex;

public class ServiceCallQueueHealthCheck extends HealthCheck {
	private final ServiceCallsMultiIndex queue;
	
	public ServiceCallQueueHealthCheck(ServiceCallsMultiIndex serviceCalls) {
		this.queue = serviceCalls;
	}

	@Override
	protected Result check() throws Exception {
		if (queue.size() == 0) return Result.unhealthy("No trains in queue, but may be starting up.");
		if (queue.stream().parallel().anyMatch((ServiceCall call) -> (new Date().getTime() - call.getPlanned().getTime() > 30))) {
			return Result.unhealthy("Queue has service calls planned over 30 minutes ago");
		}
		return Result.healthy();
	}

}
