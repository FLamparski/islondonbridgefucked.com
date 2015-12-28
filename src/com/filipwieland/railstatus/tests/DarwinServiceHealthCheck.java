package com.filipwieland.railstatus.tests;

import org.apache.activemq.ActiveMQConnection;

import com.codahale.metrics.health.HealthCheck;
import com.filipwieland.railstatus.datafeeds.DarwinDataFeed;

public class DarwinServiceHealthCheck extends HealthCheck {
	private final DarwinDataFeed feed;
	
	public DarwinServiceHealthCheck(DarwinDataFeed feed) {
		this.feed = feed;
	}

	@Override
	protected Result check() throws Exception {
		ActiveMQConnection conn = feed.getConnection();
		if (conn.isClosed() || conn.isClosed()) return Result.unhealthy("ActiveMQ connection is closing or closed");
		if (conn.isTransportFailed()) return Result.unhealthy("ActiveMQ underlying transport is failed");
		return Result.healthy();
	}

}
