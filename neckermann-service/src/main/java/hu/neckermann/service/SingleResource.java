package hu.neckermann.service;
import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import hu.neckermann.model.SingleResult;
import hu.neckermann.search.SingleSearch;


public class SingleResource extends ServerResource {

	@Get("json|xml")
	public SingleResult represent() throws IOException {
		String hotelName = getQueryValue("name");
		String hotelNumber = getQueryValue("id");
		
		if (hotelName == null || hotelName.isEmpty() || hotelNumber == null || hotelNumber.isEmpty()) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Required parameter 'name' or 'id' is missing");
		}
		
		return new SingleSearch().doSearch(hotelNumber, hotelName);
	}

}