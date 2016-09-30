package io.shrtr.server;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Optional;

public class InMemoryPersisterTest {

	@Test
	public void test() {
		Persister persister = new InMemoryPersister();

		String fullLength = "full length";
		String shortened = "shortened";
		try {
			persister.storeMapping(fullLength, shortened);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals(Optional.of(fullLength), persister.getMapping(shortened));
		assertEquals(Optional.absent(), persister.getMapping("not present"));
	}
}
