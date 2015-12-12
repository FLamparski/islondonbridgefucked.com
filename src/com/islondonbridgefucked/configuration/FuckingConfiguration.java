package com.islondonbridgefucked.configuration;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class FuckingConfiguration extends Configuration {
	@NotNull
	@JsonProperty("darwin")
	private DarwinConfiguration darwin = new DarwinConfiguration();
	
	public DarwinConfiguration getDarwinConfiguration() {
		return this.darwin;
	}
}
