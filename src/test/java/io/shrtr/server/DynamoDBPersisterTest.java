package io.shrtr.server;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;


/*
 * 
 * Requires local dockerized dynamodb:
 * 
 * $ docker run -d -p 8000:8000 --name dynamodb deangiberson/aws-dynamodb-local
 *
 */
public class DynamoDBPersisterTest {

	// test client pointing to localhost dynamodb
	AmazonDynamoDB dynamoDb = TestUtils.ddbLocalClient();
	
	Persister dps = new DynamoDBPersister(dynamoDb);
	
	@Before
	public void setUp() {
		TestUtils.setupShortUrlTable();
	}
	
	@After
	public void tearDown() {
		TestUtils.deleteShortUrlTable();
	}
	
	@Test
	public void testBasicStoreAndGet() throws Exception {
		try {
			dps.storeMapping("http://www.google.com", "abc");
			assertEquals("http://www.google.com", dps.getMapping("abc").get());
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Test(expected = RuntimeException.class)
	public void testCollision() throws Exception {
		try {
			dps.storeMapping("http://www.google.com", "abc");
			assertEquals("http://www.google.com", dps.getMapping("abc").get());
			dps.storeMapping("http://www.google.it", "abc");
		} catch (Exception e) {
			throw e;
		}
	}

}
