package hu.neckermann.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.neckermann.model.DetailedLodgement;
import hu.neckermann.model.LongDescription;
import hu.neckermann.model.SingleResult;

public class SingleLodgementParser {

	private static Logger logger = LoggerFactory.getLogger(SingleLodgementParser.class);

	public SingleLodgementParser() {
	}

	private DetailedLodgement extractItem(Document doc) throws IOException {
		DetailedLodgement lodgement = new DetailedLodgement();
		
		Set<String> images = new HashSet<String>();
		
		Elements infoTop = doc.select("div#hotel-info-top");
		
		for (Element element : infoTop.select("div#hotel-info-images div#slider ul.slides li img")) {
			
			try {
				images.add(element.attr("abs:src").trim());
				
			} catch(Exception e) {
				throw new IOException("Malformed document");
			}
		}
		
		String rating, hotelName, location, description = null;
		Set<String> features = new HashSet<String>();
		List<LongDescription> longDescriptions = new ArrayList<LongDescription>();
		
		try {
			Elements descs = infoTop.select("div#hotel-info-description");
			
			Elements infos = descs.select("div#hotel-info-card");
			
			hotelName = infos.select("h1.hotelname").get(0).text().trim();
			location = infos.select("h5").get(0).text().trim();
			description = infos.select("div.product-text").get(0).text().trim();
			
			
			try {
				rating = infos.select("div.rg-ratings > div > span").last().text().trim();
				rating = rating + " of "+ infos.select("div.rg-ratings > div > span").first().text().trim() + " votes";
			} catch(NullPointerException e) {
				if(logger.isInfoEnabled()){
					logger.info("Not found ratings for hotel: "+ hotelName);
				}
				rating = "Undefined";
			}
			
			features.addAll(infos.select("div.attribute-holder > span").eachText());
			
			Elements details = doc.select("div#hotel-info-details");
			
			for (Element element : details.select("div.whiteportlet > div#my-tab-content > div#info > div.hotel-description")){
				LongDescription desc = new LongDescription();
				for (Element innerElement : element.getAllElements()) {
					if(innerElement.tagName().equals("h4")){
						desc.setTitle(innerElement.text().trim());
					}else if (innerElement.tagName().equals("p") && innerElement.hasText()){
						desc.setValue(innerElement.text().trim());
						longDescriptions.add(desc);
						desc = new LongDescription();
					}
				}
			}
			
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		lodgement.setUri(doc.baseUri());
		lodgement.setHotelName(hotelName);
		lodgement.setRating(rating);
		lodgement.setLocation(location);
		lodgement.setDescription(description);
		lodgement.setLongDescriptions(longDescriptions);
		lodgement.setFeatures(features);
		lodgement.setImages(images);
		
		return lodgement;
	}

	public SingleResult parse(Document doc) throws IOException {
		return new SingleResult(extractItem(doc));
	}

}
