package com.filipwieland.railstatus.datafeeds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceCall {
	public enum CallType {
		ARRIVAL, DEPARTURE, PASS
	};
	private final Date planned;
	private final Date estimated;
	private final String trainId;
	private final String location;
	private final CallType type;
	private final static SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	
	private ServiceCall(String trainId, Date planned, Date estimated, String location, CallType type) {
		this.trainId = trainId;
		this.planned = planned;
		this.estimated = estimated;
		this.location = location;
		this.type = type;
	}
	
	private static String normaliseTimeString(String ts) throws Exception {
		String[] parts = ts.split(":");
		if (parts.length == 2) {
			return ts + ":00";
		} else if (parts.length == 3) {
			return ts;
		} else {
			throw new Exception("Time string " + ts + " not normalisable.");
		}
	}

	public static ServiceCall getInstance(Date now, String trainId, String planned, String estimated, String location, CallType ctype) throws Exception {
		String relativeEstimated = new SimpleDateFormat("yyyy-MM-dd").format(now) + "T" + normaliseTimeString(estimated);
		String relativeWorking = new SimpleDateFormat("yyyy-MM-dd").format(now) + "T" + normaliseTimeString(planned);
		Date relativeEstDate = isoDateFormat.parse(relativeEstimated);
		Date relativeWorkingDate = isoDateFormat.parse(relativeWorking);
		return new ServiceCall(trainId, relativeEstDate, relativeWorkingDate, location, ctype);
	}

	public final Date getPlanned() {
		return planned;
	}

	public final Date getEstimated() {
		return estimated;
	}

	public final String getTrainId() {
		return trainId;
	}

	public final CallType getType() {
		return type;
	}
	
	public final String getLocation() {
		return location;
	}
	
	public final int getDifference() {
		return (int) (estimated.getTime() - planned.getTime());
	}
}
