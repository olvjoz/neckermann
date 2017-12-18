package hu.neckermann.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.neckermann.model.Lodgement;
import hu.neckermann.model.SingleResult;

public class SingleLodgementParser {

	private static Logger logger = LoggerFactory.getLogger(SingleLodgementParser.class);

	public SingleLodgementParser() {
	}

	private Lodgement extractItem(Document doc) throws IOException {
		Lodgement lodgement = new Lodgement();
		
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
			
			lodgement.setUri(uri);
			lodgement.setHotelName(hotelName);
			lodgement.setLocation(location);
			lodgement.setRating(rating);
			lodgement.setDescription(description);
			lodgement.setFeatures(features);
			
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
		return lodgement;
	}

	public SingleResult parse(Document doc) throws IOException {
		return new SingleResult(extractItem(doc));
	}

}
