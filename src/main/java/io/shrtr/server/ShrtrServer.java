package io.shrtr.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import io.shrtr.api.ShrtrService;

public class ShrtrServer extends Application<ShrtrConfiguration> {

	@Override
	public void run(ShrtrConfiguration configuration, Environment environment) throws Exception {
		final ShrtrService shrtr = new ShrtrResource(
				new MurmurShortener(configuration.prefix()), 
				new DynamoDBPersister());
		
		environment.jersey().register(shrtr);
	}

	public static void main(String[] args) throws Exception {
        new ShrtrServer().run(args);
    }
}
