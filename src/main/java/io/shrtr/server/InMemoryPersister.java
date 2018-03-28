package io.shrtr.server;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

public class InMemoryPersister implements Persister {

	private Map<String, String> store = Maps.newLinkedHashMap();

	@Override
	public void storeMapping(String fullLength, String shortened) {
		// on collision we throw, for now
		if (store.containsKey(shortened)) {
			handleCollision(fullLength, shortened);
		}
		store.put(shortened, fullLength);
	}

	@Override
	public Optional<String> getMapping(String shortened) {
		return Optional.fromNullable(store.get(shortened));
	}

	@Override
	public void handleCollision(String fullLength, String shortened) {
		throw new RuntimeException("Error shortening URL: " + fullLength);
	}
}
