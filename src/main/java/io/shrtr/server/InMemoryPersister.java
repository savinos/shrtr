package io.shrtr.server;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

public class InMemoryPersister implements Persister {

	private Map<String, String> store = Maps.newLinkedHashMap();

	@Override
	public void storeMapping(String fullLength, String shortened) {
		// on collision we throw, for now
		if (sameHashAlreadyPresent(fullLength, shortened)) {
			if (differentUrlSameHash(fullLength, shortened)) {
				handleCollision(fullLength, shortened);
			} 
			return;
		}
		store.put(shortened, fullLength);
	}

	@Override
	public Optional<String> getMapping(String shortened) {
		return Optional.fromNullable(store.get(shortened));
	}

	private boolean sameHashAlreadyPresent(String fullLength, String shortened) {
		return store.containsKey(shortened);
	}
	
	private boolean differentUrlSameHash(String fullLength, String shortened) {
		return !store.get(shortened).equals(fullLength);
	}
	
	@Override
	public void handleCollision(String fullLength, String shortened) {
		throw new RuntimeException("Error shortening URL: " + fullLength);
	}
}
