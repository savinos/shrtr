package io.shrtr.server;

import com.google.common.base.Optional;

public interface Persister {

	void storeMapping(String fullLength, String shortened);
	
	Optional<String> getMapping(String shortened);
}
