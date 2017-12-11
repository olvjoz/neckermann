package hu.neckermann.search;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import hu.neckermann.model.SearchResults;
import hu.neckermann.parser.SearchResultsParser;

public class KeywordSearch extends SearchResultsParser {

	private static final String SEARCH_URI = "https://www.neckermann.hu/minden-orszag/minden-ajanlat";

	public KeywordSearch() {
	}

	public KeywordSearch(int maxItems) {
		super(maxItems);
	}

	public SearchResults doSearch(String query) throws IOException {
		Document doc = Jsoup.connect(SEARCH_URI).userAgent("Mozilla").data("keywords", query).get();
		return parse(doc);
	}
}
