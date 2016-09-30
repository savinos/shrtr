package io.shrtr.server;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

public class InMemoryPersister implements Persister {

	private Map<String, String> store = Maps.newLinkedHashMap();

	@Override
	public void storeMapping(String fullLength, String shortened) {
		store.put(shortened, fullLength);
	}

	@Override
	public Optional<String> getMapping(String shortened) {
		return Optional.fromNullable(store.get(shortened));
	}
	
}
