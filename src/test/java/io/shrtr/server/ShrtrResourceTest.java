package io.shrtr.server;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import io.shrtr.api.ShrtrService;

public class ShrtrResourceTest {

	private final ShrtrService shrtr = new ShrtrResource(
			new CRC32Shortener("https://shrtr.io/"),
			new InMemoryPersister());
	
	@Test
	public void testShorten() {
		String shortened = shrtr.shorten("https://www.google.com");
		String expectedShort = "https://shrtr.io/6b5b1e33";
		assertEquals(expectedShort, shortened);
		
		Response actual = shrtr.actualUrl(shortened);
		assertEquals(Status.TEMPORARY_REDIRECT.getStatusCode(), actual.getStatus());
		assertEquals("https://www.google.com", actual.getLocation().toString());
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testShortenThrowsOnNull() {
		shrtr.shorten(null);
	}
}
