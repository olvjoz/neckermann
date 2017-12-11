package hu.neckermann.service;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import hu.neckermann.model.Countries;
import hu.neckermann.parser.AllCountries;


public class ListAllCountries extends ServerResource {

	private static final String URI = "https://www.neckermann.hu/";

	@Get("json|xml")
	public Countries represent() throws IOException {
		Document doc = Jsoup.connect(URI).userAgent("Mozilla").get();
		return new AllCountries().listCountries(doc);
	}

}