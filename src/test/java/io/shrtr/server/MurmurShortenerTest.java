package io.shrtr.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class MurmurShortenerTest {

	@Test
	public void test() {
		Shortener shortener = new MurmurShortener("https://shrtr.io/");
		String actual = shortener.shorten("http://www.nurkiewicz.com/2014/08/url-shortener-service-in-42-lines-of.html");
		String expected = "https://shrtr.io/0e9d3e2d";
		assertEquals(expected, actual);
	}

}
