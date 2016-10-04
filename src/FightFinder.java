import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import Helpers.Constants;

import java.util.Arrays;
import java.io.*;

// This class scrapes through for fighter info

public class FightFinder {
	private String weightClassURL;

	public FightFinder() {
		this.weightClassURL = "http://www.ufc.com/fighter/Weight_Class/";
	}

	public void getWeightClass(String weightClass) {
		/*
		 * Check if parameter weightClass is a weightClass, if not then look up 
		 * all fighters
		 */
		String url;
		if (Arrays.asList(Constants.WEIGHT_CLASSES).contains(weightClass))
			url = this.weightClassURL + weightClass;
		else
			url = this.weightClassURL;

		try {
			Document doc = Jsoup.connect(url).get();
			String title = doc.title();
			System.out.println(title);

			// Gets a tbody of all the fighters and other miscellaneous data that will need to be parsed through 
			Elements tbody = doc.select("div.content-inner > div.fighter-listing-page > "
					+ "div.content-section > div > div.main-section > div.tab-content > "
					+ "table > tbody > tr.fighter");
			System.out.println(tbody);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
