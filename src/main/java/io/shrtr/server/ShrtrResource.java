package io.shrtr.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import io.shrtr.api.ShrtrService;


public class ShrtrResource implements ShrtrService {

	private static final int MAX_INPUT_SIZE = 10000;
	private final Shortener shortener;
	private final Persister persister;

	public ShrtrResource(Shortener shortener, Persister persister) {
		this.shortener = shortener;
		this.persister = persister;
	}

	@Override
	public String shorten(String url) {
		// TODO regex validate url
		Preconditions.checkNotNull(url, "Argument should not be null.");
		Preconditions.checkArgument(url.length() > 0, "Argument should not be empty.");
		Preconditions.checkArgument(url.length() < MAX_INPUT_SIZE, 
				String.format("Argument should not be larger than %s.", MAX_INPUT_SIZE));
		
		String shortenedUrl = shortener.shorten(url);
		persister.storeMapping(url, shortenedUrl);
		return shortenedUrl;
	}

	@Override
	public String actualUrl(String shortenedUrl) {
		Optional<String> actual = persister.getMapping(shortenedUrl);
		if (actual.isPresent()) {
			return actual.get();
		}
		throw new WebApplicationException(Status.NOT_FOUND);
	}
}
