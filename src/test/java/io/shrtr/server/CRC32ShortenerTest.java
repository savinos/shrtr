package io.shrtr.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class CRC32ShortenerTest {

	@Test
	public void test() {
		Shortener shortener = new CRC32Shortener("https://shrtr.io/");
		String actual = shortener.shorten("http://www.nurkiewicz.com/2014/08/url-shortener-service-in-42-lines-of.html");
		String expected = "https://shrtr.io/33a4603f";
		assertEquals(expected, actual);
	}

}
