package hu.neckermann.search;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import hu.neckermann.model.SingleResult;
import hu.neckermann.parser.SingleLodgementParser;

public class SingleSearch extends SingleLodgementParser {

	public SingleSearch() {
	}

	public SingleResult doSearch(String hotelNumber, String hotelName) throws IOException {
		String uri = "https://www.neckermann.hu/szallas";
		
		if (hotelNumber != null && !hotelNumber.isEmpty() 
				&& hotelName!=null && !hotelName.isEmpty() ){
			uri = uri + "/" + hotelName.toLowerCase() + "/" + hotelNumber;
		}
		
		Document doc = Jsoup.connect(uri).userAgent("Mozilla").get();
		
		return parse(doc);
	}
}
