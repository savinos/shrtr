package io.shrtr.server;

import io.shrtr.api.ShrtrService;

public class ShrtrResource implements ShrtrService {

	private final Shortener shortener;

	public ShrtrResource(Shortener shortener) {
		this.shortener = shortener;
	}
	
	@Override
	public String shorten(String url) {
		return shortener.shorten(url);
	}

}
