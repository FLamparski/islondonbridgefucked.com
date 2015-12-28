package com.filipwieland.railstatus.tests;

import java.util.Date;
import java.util.Deque;

import com.codahale.metrics.health.HealthCheck;
import com.filipwieland.railstatus.datafeeds.ServiceCall;

public class ServiceCallQueueHealthCheck extends HealthCheck {
	private final Deque<ServiceCall> queue;
	
	public ServiceCallQueueHealthCheck(Deque<ServiceCall> queue) {
		this.queue = queue;
	}

	@Override
	protected Result check() throws Exception {
		if (queue.size() == 0) return Result.unhealthy("No service calls in queue, but may be starting up.");
		if (queue.stream().anyMatch((ServiceCall call) -> (new Date().getTime() - call.getPlanned().getTime() > 30))) {
			return Result.unhealthy("Queue has service calls planned over 30 minutes ago");
		}
		return Result.healthy();
	}

}
