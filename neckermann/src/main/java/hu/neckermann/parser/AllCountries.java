package hu.neckermann.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.neckermann.model.Countries;
import hu.neckermann.model.Country;

public class AllCountries {

	private static Logger logger = LoggerFactory.getLogger(AllCountries.class);

	public AllCountries() {
	}

	private List<Country> extractItems(Document doc) throws IOException {
		List<Country> countries = new LinkedList<Country>();
		
		for (Element element : doc.select("div.search_panel")) {
			
			Country country = null;
			
			try {
				for(Element e : element.select("* > div.geo-select > div > select > option")){
					if(e.attr("class").contains("country")){
						country = new Country();
						countries.add(country);
						
						country.setCountry(e.text().trim());
					}else if(e.attr("class").contains("region")){
						Set<String> regions = country.getRegions();
						
						if (regions == null){
							regions = new HashSet<String>();
							country.setRegions(regions);
						}
						
						regions.add(e.text().trim());
					}
				}
			} catch(Exception e) {	
				throw new IOException("Malformed document");
			}
			
		}
		return countries;
	}

	public Countries listCountries(Document doc) throws IOException {
		List<Country> countries = extractItems(doc);
		return new Countries(countries.size(), 1, countries.size(), countries);
	}

}
