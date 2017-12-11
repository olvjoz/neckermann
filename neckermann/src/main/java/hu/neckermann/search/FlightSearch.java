package hu.neckermann.search;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import hu.neckermann.model.SearchResults;
import hu.neckermann.parser.SearchResultsParser;

public class FlightSearch extends SearchResultsParser {

	public FlightSearch() {
	}

	public FlightSearch(int maxItems) {
		super(maxItems);
	}

	public SearchResults doSearch(String country, String region) throws IOException {
		String uri = "https://www.neckermann.hu/" + country.toLowerCase();
		
		if (region != null){
			uri = uri + "/" + region.toLowerCase() + "/repulos-utak";
		}else{
			uri = uri + "/repulos-utak";
		}
		
		Document doc = Jsoup.connect(uri).userAgent("Mozilla").get();
		
		return parse(doc);
	}
}
