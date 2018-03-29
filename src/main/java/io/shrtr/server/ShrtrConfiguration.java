package io.shrtr.server;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ShrtrConfiguration extends Configuration {

	@NotNull
	private String prefix;

	@NotNull
	private String store;

	@JsonProperty
	public String prefix() {
		return prefix;
	}

	@JsonProperty
	public String store() {
		return store;
	}
}
