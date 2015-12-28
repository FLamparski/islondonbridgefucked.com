package com.filipwieland.railstatus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import com.filipwieland.railstatus.configuration.RailStatusConfiguration;
import com.filipwieland.railstatus.datafeeds.DarwinDataFeed;
import com.filipwieland.railstatus.datafeeds.ServiceCall;
import com.filipwieland.railstatus.events.Event;
import com.filipwieland.railstatus.events.EventEmitter;
import com.filipwieland.railstatus.tests.DarwinServiceHealthCheck;
import com.filipwieland.railstatus.tests.ServiceCallQueueHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DisruptionService extends Application<RailStatusConfiguration> {
	
	private DarwinDataFeed feed;
	// private Map<String, String> stationNames;
	
	private Deque<ServiceCall> serviceCalls = new AutoFilteringDeque<>((ServiceCall call) -> ((new Date().getTime() - call.getPlanned().getTime()) < 30 * 60 * 1000));
	
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
		
		feed = new DarwinDataFeed("failover:(tcp://datafeeds.nationalrail.co.uk:61616)?maxReconnectAttempts=5", conf.getDarwinConfiguration().getQueue());
		feed.on(DarwinDataFeed.EVT_TRAIN_STATUS, (Event e) -> {
			ServiceCall call = (ServiceCall) e.getAttrs().get("descriptor");
			/*
			 * TODO: a better way of updating train states.
			 * 
			 * This will have to be a mapping between train_id and a list of its call points (tiploc, call_type, planned, estimated).
			 * This avoids the pair uniqueness issue above, but ensure Darwin train IDs are unique.
			 * Need to ensure there's a way to get rid of old train (listen to train deactivation message, iterate through all the trains
			 * and remove those whose greatest estimated arrival or pass date is more than 30 minutes ago or 1h into the future).
			 * 
			 * TrainStates = {
			 * 	Train001: [ { tiploc: Loc001, type: PASS, planned: 13:30, estimated: 13:34 }, ... ],
			 *  ...
			 * }
			 */
			serviceCalls.addFirst(call);
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
		env.jersey().register(rs);
	}

	/*private Map<String, String> getStationNames(String locationsFile) {
		try (BufferedReader reader = new BufferedReader(new FileReader(locationsFile))) {
			Map<String, String> map = new HashMap<>();
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\t");
				map.put(fields[0], fields[1]);
			}
			return map;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

}
