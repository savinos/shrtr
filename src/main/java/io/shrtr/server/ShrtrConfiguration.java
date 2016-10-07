package io.shrtr.server;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ShrtrConfiguration extends Configuration {

	@NotEmpty
	private String prefix;

	@JsonProperty
    public String prefix() {
        return prefix;
    }
}
