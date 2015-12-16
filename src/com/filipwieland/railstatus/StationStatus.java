package com.filipwieland.railstatus;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationStatus {
	private long id;
	
	public StationStatus(){}
	
	public StationStatus(long id) {
		this.id = id;
	}
	
	@JsonProperty
	public long getId() { return id; }
	@JsonProperty
	public boolean isDisrupted() { return true; }
	@JsonProperty
	public int getDisruptionLevel() { return 4; }
	@JsonProperty
	public Date getGeneratedAt() { return new Date(); }
}
