package hu.neckermann.parser;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.neckermann.model.Lodgement;
import hu.neckermann.model.SearchResults;

public class SearchResultsParser {

	private static Logger logger = LoggerFactory.getLogger(SearchResultsParser.class);

	public static final int MAX_ITEMS = 60;

	private int maxItems = MAX_ITEMS;

	public SearchResultsParser() {
	}

	public SearchResultsParser(int maxItems) {
		setMaxItems(maxItems);
	}

	public int getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	private int getTotalItems(Document doc) throws IOException {
		int totalItems = 0;
		try {
			totalItems = Integer.parseInt(doc.select("div.nr-of-results > div > p > span").get(0).text().trim());
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		return totalItems;
	}

	private List<Lodgement> extractItems(Document doc) throws IOException {
		List<Lodgement> lodgements = new LinkedList<Lodgement>();
		
		for (Element element : doc.select("div.resultitem")) {
			
			String uri,rating, hotelName, location, description = null;
			Set<String> features = new HashSet<String>();
			
			try {
				hotelName = element.select("div.result-hotel > a > span").get(0).text().trim();
				location = element.select("div.result-location").get(0).text().trim();
				description = element.select("div.result-body > div > span.product-text").get(0).text().trim();
				
				features.addAll(element.select("div.result-header > * > div.attribute-holder > span").eachText());
				
				uri = element.select("div.result-hotel > a").get(0).attr("abs:href").trim().split("\\?")[0];
				
				try {
					rating = element.select("div.rg-ratings > div > span").last().text().trim();
					rating = rating + " of "+ element.select("div.rg-ratings > div > span").first().text().trim() + " votes";
				} catch(NullPointerException e) {
					if(logger.isInfoEnabled()){
						logger.info("Not found ratings for hotel: "+ hotelName);
					}
					rating = "Undefined";
				}
				
			} catch(Exception e) {
				throw new IOException("Malformed document");
			}
			
			Lodgement lodgement = new Lodgement();
			
			lodgement.setUri(uri);
			lodgement.setHotelName(hotelName);
			lodgement.setLocation(location);
			lodgement.setRating(rating);
			lodgement.setDescription(description);
			lodgement.setFeatures(features);
			
			lodgements.add(lodgement);
			
			if(logger.isDebugEnabled()){
				StringBuilder loggerSB = new StringBuilder();

				loggerSB.append("\nUri: " + uri + "\n");
				loggerSB.append("Hotel Name: " + hotelName + "\n");
				loggerSB.append("Location: " + location + "\n");
				loggerSB.append("Ratings: " + rating + "\n");
				loggerSB.append("Description: " + description + "\n");
				
				logger.debug(loggerSB.toString());
			}
		}
		return lodgements;
	}

	private Document getNextPage(Document doc) throws IOException {
		String nextPage = null;
		try {
			doc.baseUri().split("&"); // TODO
			nextPage = doc.baseUri().replace("&page=", "&page=");
			logger.info("Next page: {}", nextPage);
		} catch(Exception e) {
			// no more pages
		}
		return nextPage != null ? Jsoup.connect(nextPage).userAgent("Mozilla").get() : null;
	}

	public SearchResults parse(Document doc) throws IOException {
		List<Lodgement> lodgements = new LinkedList<Lodgement>();
		int totalItems = getTotalItems(doc);
		logger.info("Total number of items: {}", totalItems);
loop:		while (totalItems != 0 && doc != null) {
			for (Lodgement lodgement : extractItems(doc)) {
				if (0 <= maxItems && maxItems <= lodgements.size()) {
					break loop;
				}
				lodgements.add(lodgement);
			}
			if (0 <= maxItems && maxItems <= lodgements.size()) break;
			doc = getNextPage(doc);
		}
		return new SearchResults(totalItems, 1, lodgements.size(), lodgements);
	}

}
