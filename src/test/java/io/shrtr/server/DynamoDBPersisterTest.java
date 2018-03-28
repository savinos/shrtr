package io.shrtr.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
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

	AmazonDynamoDB dynamoDb = ddbLocalClient();
	Persister dps = new DynamoDBPersister(dynamoDb);
	
	@Before
	public void setUp() {
		dynamoDb.createTable(new CreateTableRequest()
				.withTableName("SHORT_URLS")
				.withAttributeDefinitions(new AttributeDefinition().withAttributeName("short_url").withAttributeType(ScalarAttributeType.S))
				.withKeySchema(new KeySchemaElement().withAttributeName("short_url").withKeyType(KeyType.HASH))
				.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L)));
	}
	
	@After
	public void tearDown() {
		dynamoDb.deleteTable("SHORT_URLS");
	}
	
	@Test
	public void testBasicStoreAndGet() {
		try {
			dps.storeMapping("http://www.google.com", "abc");
			assertEquals("http://www.google.com", dps.getMapping("abc").get());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	
	private AmazonDynamoDB ddbLocalClient() {
		AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-west-2"))
				.build();
		return dynamoDb;
	}
}
