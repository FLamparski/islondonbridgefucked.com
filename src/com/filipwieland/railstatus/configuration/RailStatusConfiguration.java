package com.filipwieland.railstatus.configuration;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class RailStatusConfiguration extends Configuration {
	@NotNull
	@JsonProperty("darwin")
	private DarwinConfiguration darwin = new DarwinConfiguration();
	
	@NotNull
	@JsonProperty("locations-file")
	private String locationsFile;
	
	public DarwinConfiguration getDarwinConfiguration() {
		return this.darwin;
	}
	
	public String getLocationsFile() {
		return this.locationsFile;
	}
}
