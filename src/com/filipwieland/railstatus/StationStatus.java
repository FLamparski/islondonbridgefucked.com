package com.filipwieland.railstatus;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationStatus {
	private long id;
	private int disruptionLevel;
	
	public StationStatus(){}
	
	public StationStatus(long id, int disruptionLevel) {
		this.id = id;
		this.disruptionLevel = disruptionLevel;
	}
	
	@JsonProperty
	public long getId() { return id; }
	@JsonProperty
	public boolean isDisrupted() { return disruptionLevel < 4; }
	@JsonProperty
	public int getDisruptionLevel() { return disruptionLevel; }
	@JsonProperty
	public Date getGeneratedAt() { return new Date(); }
}
