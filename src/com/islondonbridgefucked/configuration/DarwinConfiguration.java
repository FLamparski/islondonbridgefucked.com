package com.islondonbridgefucked.configuration;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class DarwinConfiguration extends Configuration {
	@NotNull
	private String queue;
	
	@JsonProperty
	public String getQueue() {
		return this.queue;
	}
}
