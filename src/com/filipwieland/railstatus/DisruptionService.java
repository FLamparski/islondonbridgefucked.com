package com.filipwieland.railstatus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.filipwieland.railstatus.configuration.RailStatusConfiguration;
import com.filipwieland.railstatus.datafeeds.DarwinDataFeed;
import com.filipwieland.railstatus.datafeeds.ServiceCall;
import com.filipwieland.railstatus.events.Event;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DisruptionService extends Application<RailStatusConfiguration> {
	
	private DarwinDataFeed feed;
	private Map<String, String> stationNames;
	
	@Override
	public String getName() {
		return "is-london-bridge-fucked";
	}
	
	@Override
	public void initialize(Bootstrap<RailStatusConfiguration> bootstrap) {
	}

	@Override
	public void run(RailStatusConfiguration conf, Environment env) throws Exception {
		stationNames = getStationNames(conf.getLocationsFile());
		
		feed = new DarwinDataFeed("failover:(tcp://datafeeds.nationalrail.co.uk:61616)?maxReconnectAttempts=5", conf.getDarwinConfiguration().getQueue());
		feed.on(DarwinDataFeed.EVT_TRAIN_STATUS, (Event e) -> {
			ServiceCall call = (ServiceCall) e.getAttrs().get("call");
			System.out.printf("Service Call: Train ID %s scheduled %s at %s at %s, estimated %s.\n",
					call.getTrainId(),
					call.getType().name(),
					stationNames.get(call.getLocation()),
					call.getPlanned().toString(),
					call.getEstimated().toString());
			return true;
		});
		feed.start();

		final DisrupitonResource rs = new DisrupitonResource();
		env.jersey().register(rs);
	}

	private Map<String, String> getStationNames(String locationsFile) {
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
	}

}
