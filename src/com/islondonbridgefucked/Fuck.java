package com.islondonbridgefucked;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fuck {
	private long id;
	
	public Fuck(){}
	
	public Fuck(long id) {
		this.id = id;
	}
	
	@JsonProperty
	public long getId() { return id; }
	@JsonProperty
	public boolean isFucked() { return true; }
	@JsonProperty
	public int getFuckednessLevel() { return 4; }
	@JsonProperty
	public Date getGeneratedAt() { return new Date(); }
}
