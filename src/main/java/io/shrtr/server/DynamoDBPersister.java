package io.shrtr.server;

import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class DynamoDBPersister implements Persister {

	private static final String SHORT_URL_TABLE_NAME = "SHORT_URLS";
	private static final String SHORT_URL_TABLE_KEY_NAME = "short_url";
	private static final String SHORT_URL_TABLE_LONG_URL = "long_url";
	private final AmazonDynamoDB dynamoDb;
	
	
	public DynamoDBPersister() {
		dynamoDb = ddbClient();
		
	}
	
	@VisibleForTesting
	DynamoDBPersister(AmazonDynamoDB dynamoDb) {
		this.dynamoDb = dynamoDb;
	}
	
	@Override
	public void storeMapping(String fullLength, String shortened) throws Exception {
		
		try {
			Map<String, AttributeValue> attributes = Maps.newHashMap();
			attributes.put(SHORT_URL_TABLE_KEY_NAME, new AttributeValue().withS(shortened));
			attributes.put(SHORT_URL_TABLE_LONG_URL, new AttributeValue().withS(fullLength));
			
			// only succeeds if the short url does not exist in dynamodb
			dynamoDb.putItem(new PutItemRequest()
					.withTableName(SHORT_URL_TABLE_NAME)
					.withItem(attributes)
					.withConditionExpression(keyDoesNotExist()));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<String> getMapping(String shortened) {
		GetItemRequest get = new GetItemRequest().withKey(toAttributeMap(shortened))
				.withTableName(SHORT_URL_TABLE_NAME);

		GetItemResult result = dynamoDb.getItem(get);		
		Map<String, AttributeValue> returnedItem = result.getItem();
		if (null == returnedItem || returnedItem.isEmpty()) {
			return Optional.absent();
		}
		return Optional.fromNullable(returnedItem.get(SHORT_URL_TABLE_LONG_URL).getS());
	}

	@Override
	public void handleCollision(String fullLength, String shortened) {
		throw new RuntimeException("Could not store URL " + fullLength);
	}

	private Map<String, AttributeValue> toAttributeMap(String shortened) {
		return new ImmutableMap.Builder<String, AttributeValue>()
				.put(SHORT_URL_TABLE_KEY_NAME, new AttributeValue(shortened)).build();
	}
	
	private AmazonDynamoDB ddbClient() {
		AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.standard()
				.withRegion(Regions.EU_WEST_2)
				.build();
		return dynamoDb;
	}
	
	private boolean differentUrlSameHash(String fullLength, Optional<String> stored) {
		return !stored.get().equals(fullLength);
	}
	
	private String keyDoesNotExist() {
		return String.format("attribute_not_exists(%s)", SHORT_URL_TABLE_KEY_NAME);
	}
	
}
