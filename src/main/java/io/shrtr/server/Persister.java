package io.shrtr.server;

import com.google.common.base.Optional;

public interface Persister {

	void storeMapping(String fullLength, String shortened) throws Exception;
	
	Optional<String> getMapping(String shortened);
	
	void handleCollision(String fullLength, String shortened);
}
