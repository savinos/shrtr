package io.shrtr.server;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class MurmurShortener implements Shortener {

	private final String prefix;

	public MurmurShortener(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public String shorten(String fullLengthUrl) {
		String hashed = Hashing.murmur3_32().hashString(fullLengthUrl, StandardCharsets.UTF_8).toString();
		return String.format("%s%s", prefix, hashed);
	}

	@Override
	public String prefix() {
		return prefix;
	}

}
