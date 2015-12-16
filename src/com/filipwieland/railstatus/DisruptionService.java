package com.filipwieland.railstatus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.filipwieland.railstatus.configuration.RailStatusConfiguration;
import com.filipwieland.railstatus.datafeeds.DarwinDataFeed;
import com.thalesgroup.pushport.Pport;
import com.thalesgroup.pushport.StationMessage;
import com.thalesgroup.pushport.TS;
import com.thalesgroup.pushport.TSLocation;
import com.thalesgroup.pushport.TrainAlert;
import com.thalesgroup.pushport.Schedule;

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
		
		this.feed = new DarwinDataFeed("tcp://datafeeds.nationalrail.co.uk:61616", conf.getDarwinConfiguration().getQueue());
		feed.setListener((Pport port) -> {
			System.out.println(port.getTs().toString() + ": " + port.getUR().getUpdateOrigin() + ":");
			if (port.getUR().getTS().size() > 0) {
				List<TS> tss = port.getUR().getTS();
				System.out.println("  TrainStatus x " + tss.size());
				for (TS ts : tss) {
					System.out.printf("    TS: %s %s %s\n", ts.getRid(), ts.getUid(), ts.getSsd().toString());
					for (TSLocation lcn : ts.getLocation()) {
						// TODO: With this information I should be able to start calculating delay rates for tiplocs around London Bridge
						System.out.printf("      Location: %s (wta: %s, wtd: %s, wtp: %s, arr: %s, dep: %s, pass: %s, suppressed: %b)\n",
								stationNames.get(lcn.getTpl()),
								lcn.getWta(),
								lcn.getWtd(),
								lcn.getWtp(),
								lcn.getArr() != null ? lcn.getArr().getEt() : "na",
								lcn.getDep() != null ? lcn.getDep().getEt() : "na",
								lcn.getPass() != null ? lcn.getPass().getEt() : "na",
								lcn.isSuppr());
					}
				}
			}
			if (port.getUR().getOW().size() > 0) {
				List<StationMessage> sms = port.getUR().getOW();
				System.out.println("  StationMessage x " + sms.size());
			}
			if (port.getUR().getSchedule().size() > 0) {
				List<Schedule> ss = port.getUR().getSchedule();
				System.out.println("  Schedule x " + ss.size());
			}
			if (port.getUR().getTrainAlert().size() > 0) {
				List<TrainAlert> ats = port.getUR().getTrainAlert();
				System.out.println("  TrainAlert x " + ats.size());
			}
			if (port.getUR().getSchedule().size() > 0) {
				List<Schedule> ss = port.getUR().getSchedule();
				for (Schedule schedule : ss) {
					System.out.println(schedule.getUid());
				}
			}
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
