package hu.neckermann.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.neckermann.model.Lodgement;
import hu.neckermann.model.Price;
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
			List<Price> prices = new ArrayList<Price>();
			
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
				
				try{
					String priceValues = element.select("div.result-price-holder > h3.displayPrice").text().replaceAll("\\s","");
					String[] splitted = priceValues.split("€");
					
					Price e = new Price();
					e.setValue(Long.valueOf(splitted[0].replaceAll("[^\\d]", "")));
					e.setCurrency("€");
					prices.add(e);
					
					Price ft = new Price();
					ft.setValue(Long.valueOf(splitted[1].replaceAll("[^\\d]", "")));
					ft.setCurrency("Ft");
					prices.add(ft);
				}catch (Exception e) {
					if(logger.isDebugEnabled()){
						logger.debug("No prices for lodgement: " + uri);
					}
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
			lodgement.setPrices(prices);
			
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
		Document retdoc = null;
		try {
			String nextPageUri = null;
			
			if(!doc.baseUri().contains("&page=")){
				nextPageUri = doc.baseUri();
				
				if(!nextPageUri.contains("?")){
					nextPageUri = nextPageUri + "?&page=2";
				}else{
					nextPageUri = nextPageUri + "&page=2";
				}
			}else{
				String[] split = doc.baseUri().split("&page=");
				nextPageUri = split[0] + "&page=" + (Integer.valueOf(split[1])+1) ;
			}
			logger.info("Next page: {}", nextPageUri);
			
			retdoc = nextPageUri != null ? Jsoup.connect(nextPageUri).userAgent("Mozilla").get() : null;
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		return retdoc;
	}

	public SearchResults parse(Document doc) throws IOException {
		List<Lodgement> lodgements = new LinkedList<Lodgement>();
		
		int totalItems = getTotalItems(doc);
		logger.info("Total number of items: {}", totalItems);
		
		loop: while (totalItems != 0 && doc != null) {
			for (Lodgement lodgement : extractItems(doc)) {
				if (0 <= maxItems && maxItems <= lodgements.size()) {
					break loop;
				}
				lodgements.add(lodgement);
			}
			
			if ((0 <= maxItems && maxItems <= lodgements.size()) || totalItems <= lodgements.size()){
				break;
			}
			
			doc = getNextPage(doc);
		}
		
		return new SearchResults(totalItems, totalItems > 0 ? 1 : 0, lodgements.size(), lodgements);
	}

}
