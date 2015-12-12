package com.islondonbridgefucked;

import java.util.List;

import com.islondonbridgefucked.configuration.FuckingConfiguration;
import com.islondonbridgefucked.datafeeds.DarwinDataFeed;
import com.thalesgroup.pushport.Pport;
import com.thalesgroup.pushport.StationMessage;
import com.thalesgroup.pushport.TS;
import com.thalesgroup.pushport.TrainAlert;
import com.thalesgroup.pushport.Schedule;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ShitconService extends Application<FuckingConfiguration> {
	
	private DarwinDataFeed feed;

	public static void main(String[] args) throws Exception {
		new ShitconService().run(args);
	}
	
	@Override
	public String getName() {
		return "is-london-bridge-fucked";
	}
	
	@Override
	public void initialize(Bootstrap<FuckingConfiguration> bootstrap) {
	}

	@Override
	public void run(FuckingConfiguration conf, Environment env) throws Exception {
		this.feed = new DarwinDataFeed("tcp://datafeeds.nationalrail.co.uk:61616", conf.getDarwinConfiguration().getQueue());
		feed.setListener((Pport port) -> {
			System.out.println(port.getTs().toString() + ": " + port.getUR().getUpdateOrigin() + ":");
			if (port.getUR().getTS().size() > 0) {
				List<TS> tss = port.getUR().getTS();
				System.out.println("  TrainStatus x " + tss.size());
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
		});
		feed.start();

		final FuckednessResource rs = new FuckednessResource();
		env.jersey().register(rs);
	}

}
