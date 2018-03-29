package io.shrtr.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import io.shrtr.api.ShrtrService;

@RunWith(MockitoJUnitRunner.class)
public class ShrtrResourceTest {

	private static final String PREFIX = "https://shrtr.io/";

	private ShrtrService shrtr;
	
	@Mock
	private ShrtrConfiguration config;
	
	
	@Before
	public void setUp() {
		when(config.prefix()).thenReturn(PREFIX);
		
		shrtr = new ShrtrResource(
					config,
					new MurmurShortener(PREFIX),
					TestUtils.dynamoDbLocalPersister());
		TestUtils.setupShortUrlTable();
	}
	
	@After
	public void tearDown() {
		TestUtils.deleteShortUrlTable();
	}
	
	@Test
	public void testShorten() {
		String shortened = shrtr.shorten("https://www.google.com");
		String expectedShort = "https://shrtr.io/50328aa4";
		assertEquals(expectedShort, shortened);
		
		assertEquals("https://www.google.com", shrtr.actualUrl(shortened));
		
		Response actual = shrtr.visit("50328aa4");
		assertEquals(Status.TEMPORARY_REDIRECT.getStatusCode(), actual.getStatus());
		assertEquals("https://www.google.com", actual.getLocation().toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void testShortenThrowsOnNull() {
		shrtr.shorten(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShortenThrowsOnEmptyString() {
		shrtr.shorten("");
	}
}
