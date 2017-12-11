package hu.neckermann.service;
import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import hu.neckermann.model.SearchResults;
import hu.neckermann.search.KeywordSearch;


public class SearchResource extends ServerResource {

	@Get("json|xml")
	public SearchResults represent() throws IOException {
		String keywords = getQueryValue("keywords");
		if (keywords == null) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Required parameter 'keywords' is missing");
		}
		return new KeywordSearch().doSearch(keywords);
	}

}