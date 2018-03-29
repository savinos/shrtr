package io.shrtr.server;

import java.net.URI;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import io.shrtr.api.ShrtrService;

public class ShrtrResource implements ShrtrService {

	private static final int MAX_INPUT_SIZE = 10000;
	private final Shortener shortener;
	private final Persister persister;
	private final ShrtrConfiguration config;

	public ShrtrResource(ShrtrConfiguration config, Shortener shortener, Persister persister) {
		this.config = config;
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
		try {
			persister.storeMapping(url, shortenedUrl);
		} catch (Exception e) {
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
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

	@Override
	public Response visit(String shortenedUrl) {
		Optional<String> actual = persister.getMapping(config.prefix() + shortenedUrl);
		if (actual.isPresent()) {
			return Response.temporaryRedirect(asURI(actual)).build();
		}
		throw new WebApplicationException(Status.NOT_FOUND);
	}
	
	private URI asURI(Optional<String> actual) {
		return UriBuilder.fromPath(actual.get()).build();
	}
}
