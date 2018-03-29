package io.shrtr.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;

import io.shrtr.server.Persister;
import io.shrtr.server.PersisterFactory;

public class GetURL implements RequestHandler<String, String> {
	private final Persister persister;
	public GetURL() {
		persister = PersisterFactory.create("dynamodb");		
	}
	
	@VisibleForTesting
	GetURL(Persister persister) {
		this.persister = persister;
	}
	
    @Override
    public String handleRequest(String input, Context context) {
    	
    	Optional<String> url = persister.getMapping(input);
    	if (url.isPresent()) {
    		return url.get();
    	}
        
        return null;
    }

}
