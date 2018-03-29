package io.shrtr.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.common.base.Optional;

import io.shrtr.server.Persister;
import io.shrtr.server.TestUtils;

public class PutURLTest {

	private final String input = "https://www.google.com";

	@Before
	public void setUp() {
		TestUtils.setupShortUrlTable();
	}

	@After
	public void tearDown() {
		TestUtils.deleteShortUrlTable();
	}

	private Context createContext() {
		TestContext ctx = new TestContext();
		ctx.setFunctionName("GetURL");
		return ctx;
	}

	@Test
	public void testPutURL() throws Exception {
		Persister dps = TestUtils.dynamoDbLocalPersister();

		PutURL handler = new PutURL(dps);
		Context ctx = createContext();

		String shortened = handler.handleRequest(input, ctx);
		assertEquals("50328aa4", shortened);
		Optional<String> fromDynamo = dps.getMapping(shortened);
		assertTrue(fromDynamo.isPresent());
		assertEquals(input, fromDynamo.get());
	}

}
