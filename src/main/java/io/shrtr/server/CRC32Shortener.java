package io.shrtr.server;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class CRC32Shortener implements Shortener {

	private final String prefix;

	public CRC32Shortener(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String shorten(String url) {
		return String.format("%s%s", prefix(), Hashing.crc32().hashString(url, StandardCharsets.UTF_8).toString());
	}

	@Override
	public String prefix() {
		return prefix;
	}

}
