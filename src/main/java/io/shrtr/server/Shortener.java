package io.shrtr.server;

public interface Shortener {

	public String shorten(String fullLengthUrl);

	public String prefix();
}
