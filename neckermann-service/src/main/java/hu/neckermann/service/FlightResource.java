package hu.neckermann.service;
import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import hu.neckermann.model.SearchResults;
import hu.neckermann.search.FlightSearch;


public class FlightResource extends ServerResource {

	@Get("json|xml")
	public SearchResults represent() throws IOException {
		String country = getQueryValue("country");
		String region = getQueryValue("region");
		
		if (country == null) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Required parameter 'country' is missing");
		}
		
		return new FlightSearch().doSearch(country, region);
	}

}