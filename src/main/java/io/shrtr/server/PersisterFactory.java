package io.shrtr.server;

public class PersisterFactory {

	public static Persister create(String store) {
		if (store.equals("dynamodb")) {
			return new DynamoDBPersister();
		} else if (store.equals("memory")) {
			return new InMemoryPersister();
		}
		throw new IllegalArgumentException("Unsupported 'store': valid values are 'memory' and 'dynamodb'."
				+ " Check your configuration.");
	}
}
