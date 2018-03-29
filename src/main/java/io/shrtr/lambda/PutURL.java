package io.shrtr.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.annotations.VisibleForTesting;

import io.shrtr.server.MurmurShortener;
import io.shrtr.server.Persister;
import io.shrtr.server.PersisterFactory;

public class PutURL implements RequestHandler<String, String> {

	private final MurmurShortener shortener;
	private final Persister persister;

	public PutURL() {
		shortener = new MurmurShortener(""); 
		persister = PersisterFactory.create("dynamodb");
	}
	@VisibleForTesting
	PutURL(Persister persister) {
		shortener = new MurmurShortener("");
		this.persister = persister;
	}
	
    @Override
    public String handleRequest(String input, Context context) {
    	
		String shortened = shortener.shorten(input);
    	try {
			persister.storeMapping(input, shortened);
			return shortened;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

}
