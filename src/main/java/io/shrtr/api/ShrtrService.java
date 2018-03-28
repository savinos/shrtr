package io.shrtr.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;

@Path("/v0")
@Produces(MediaType.APPLICATION_JSON)
public interface ShrtrService {

	@GET
	@Timed
	@Path("/shorten")
	public String shorten(@QueryParam("url") String fullLengthUrl);
	
	@GET
	@Timed
	@Path("/actual")
	public Response actualUrl(@QueryParam("url") String shortenedUrl);
	
}
