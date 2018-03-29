package io.shrtr.server;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class TestUtils {

	public static AmazonDynamoDB ddbLocalClient() {
		AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-west-2"))
				.build();
		return dynamoDb;
	}
	
	public static Persister dynamoDbLocalPersister() {
		AmazonDynamoDB dynamoDb = ddbLocalClient();
		return new DynamoDBPersister(dynamoDb);
	}
	
	/*
	 * 
	 * Requires local dockerized dynamodb:
	 * 
	 * $ docker run -d -p 8000:8000 --name dynamodb deangiberson/aws-dynamodb-local
	 *
	 * 
	 * Equivalent of:
	 * 
	 * aws dynamodb create-table 
	 * 	 --table-name SHORT_URLS 
	 *   --attribute-definitions AttributeName=short_url,AttributeType=S 
	 *   --key-schema AttributeName=short_url,KeyType=HASH 
	 *   --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1  
	 *   --endpoint-url http://localhost:8000/
	 */
	public static void setupShortUrlTable() {
		AmazonDynamoDB dynamoDb = ddbLocalClient();
		dynamoDb.createTable(new CreateTableRequest()
				.withTableName("SHORT_URLS")
				.withAttributeDefinitions(new AttributeDefinition()
						.withAttributeName("short_url")
						.withAttributeType(ScalarAttributeType.S))
				.withKeySchema(new KeySchemaElement()
						.withAttributeName("short_url")
						.withKeyType(KeyType.HASH))
				.withProvisionedThroughput(new ProvisionedThroughput()
						.withReadCapacityUnits(1L)
						.withWriteCapacityUnits(1L)));
	}
	
	public static void deleteShortUrlTable() {
		AmazonDynamoDB dynamoDb = ddbLocalClient();
		dynamoDb.deleteTable("SHORT_URLS");
	}
}
