package com.islondonbridgefucked;

import java.util.Collections;
import java.util.zip.GZIPInputStream;

import javax.jms.BytesMessage;
import javax.jms.Message;

import org.apache.activemq.util.ByteArrayInputStream;

import com.islondonbridgefucked.configuration.FuckingConfiguration;
import com.islondonbridgefucked.datafeeds.DarwinDataFeed;
import com.sun.el.stream.Stream;

import io.dropwizard.Application;
import io.dropwizard.jersey.gzip.GZipDecoder;
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
		feed.setListener((Message msg) -> {
			BytesMessage bm = (BytesMessage) msg;
			try {
				byte[] bytes = new byte[(int) bm.getBodyLength()];
				bm.readBytes(bytes);
				
				byte[] decoded = new byte[(int) bm.getBodyLength() * 2];
				GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
				gis.read(decoded);
				System.out.println(new String(decoded));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		feed.start();

		final FuckednessResource rs = new FuckednessResource();
		env.jersey().register(rs);
	}

}
