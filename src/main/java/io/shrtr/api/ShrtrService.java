package io.shrtr.api;

public interface ShrtrService {

	public String shorten(String fullLengthUrl);
	
	public String actualUrl(String shortenedUrl);
	
}
