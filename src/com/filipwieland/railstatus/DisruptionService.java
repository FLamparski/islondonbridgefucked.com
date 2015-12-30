package com.filipwieland.railstatus;

import java.util.HashMap;

import com.filipwieland.railstatus.configuration.RailStatusConfiguration;
import com.filipwieland.railstatus.datafeeds.DarwinDataFeed;
import com.filipwieland.railstatus.datafeeds.ServiceCall;
import com.filipwieland.railstatus.datafeeds.ServiceCallsMultiIndex;
import com.filipwieland.railstatus.events.Event;
import com.filipwieland.railstatus.events.EventEmitter;
import com.filipwieland.railstatus.tests.DarwinServiceHealthCheck;
import com.filipwieland.railstatus.tests.ServiceCallQueueHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DisruptionService extends Application<RailStatusConfiguration> {
	
	private DarwinDataFeed feed;
	private ServiceCallsMultiIndex serviceCalls = new ServiceCallsMultiIndex(new HashMap<>());
	
	@Override
	public String getName() {
		return "is-london-bridge-fucked";
	}
	
	@Override
	public void initialize(Bootstrap<RailStatusConfiguration> bootstrap) {
	}

	@Override
	public void run(RailStatusConfiguration conf, Environment env) throws Exception {
		// stationNames = getStationNames(conf.getLocationsFile());
		
		feed = new DarwinDataFeed("failover:(tcp://datafeeds.nationalrail.co.uk:61616)?maxReconnectAttempts=5",
				conf.getDarwinConfiguration().getQueue(),
				env.metrics());
		feed.on(DarwinDataFeed.EVT_TRAIN_STATUS, (Event e) -> {
			ServiceCall call = (ServiceCall) e.getAttrs().get("descriptor");
			serviceCalls.upsertCall(call);
			return true;
		});
		feed.on(EventEmitter.EVT_ERROR, (Event e) -> {
			((Exception) e.getAttrs().get("exception")).printStackTrace();
			return true;
		});
		feed.start();
		
		env.healthChecks().register("service-call-queue", new ServiceCallQueueHealthCheck(serviceCalls));
		env.healthChecks().register("darwin-service", new DarwinServiceHealthCheck(feed));

		final DisruptionResource rs = new DisruptionResource(serviceCalls);
		final DataStructureResource rd = new DataStructureResource(serviceCalls);
		env.jersey().register(rs);
		env.jersey().register(rd);
	}

}
