package io.shrtr.lambda;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.Context;

import io.shrtr.server.DynamoDBPersister;
import io.shrtr.server.Persister;
import io.shrtr.server.TestUtils;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetURLTest {

    private String input = "https://www.google.com";

   
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

        // TODO: customize your context here if needed.
        ctx.setFunctionName("GetURL");

        return ctx;
    }

    @Test
    public void testGetURL() throws Exception {
    	Persister dps = TestUtils.dynamoDbLocalPersister();
    	dps.storeMapping(input, "abc");
    	
        GetURL handler = new GetURL(dps);
        Context ctx = createContext();
    	
        String output = handler.handleRequest("abc", ctx);

        assertEquals(input, output);
    }
}
