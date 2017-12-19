package hu.neckermann.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.neckermann.model.DetailedLodgement;
import hu.neckermann.model.SingleResult;

public class SingleLodgementParser {

	private static Logger logger = LoggerFactory.getLogger(SingleLodgementParser.class);

	public SingleLodgementParser() {
	}

	private DetailedLodgement extractItem(Document doc) throws IOException {
		DetailedLodgement lodgement = new DetailedLodgement();
		
		Set<String> images = new HashSet<String>();
		
		Elements infoTop = doc.select("div#hotel-info-top");
		
		Elements descs = infoTop.select("div#hotel-info-description");
		
		Elements infos = descs.select("div#hotel-info-card");
		
		for (Element element : infoTop.select("div#hotel-info-images div#slider ul.slides li img")) {
			
			try {
				images.add(element.attr("abs:src").trim());
				
			} catch(Exception e) {
				throw new IOException("Malformed document");
			}
			
		}
		
		lodgement.setImages(images);
		
		return lodgement;
	}

	public SingleResult parse(Document doc) throws IOException {
		return new SingleResult(extractItem(doc));
	}

}
