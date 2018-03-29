package io.shrtr.server;

import com.google.common.base.Preconditions;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import io.shrtr.api.ShrtrService;

public class ShrtrServer extends Application<ShrtrConfiguration> {

	@Override
	public void run(ShrtrConfiguration configuration, Environment environment) throws Exception {
		Preconditions.checkArgument(configuration.prefix().endsWith("/"));
		final ShrtrService shrtr = new ShrtrResource(
				configuration,
				new MurmurShortener(configuration.prefix()), 
				PersisterFactory.create(configuration.store()));
		
		environment.jersey().register(shrtr);
	}

	public static void main(String[] args) throws Exception {
        new ShrtrServer().run(args);
    }
}
