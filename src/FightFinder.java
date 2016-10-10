import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import Helpers.Constants;

import java.util.*;
import java.io.*;

// This class scrapes through for fighter info

public class FightFinder {
	// CLASS is the weight class, PAGE is the url page number which is formatted
	// as 0 20 40. Each page number is +20
	private List<Fighter> fighters;

	public FightFinder() {
		fighters = new ArrayList<Fighter>();
	}

	public void getWeightClass(String weightClass, int page) {
		/*
		 * Check if parameter weightClass is a weightClass, if not then look up
		 * all fighters
		 */
		String url;
		if (Arrays.asList(Constants.WEIGHT_CLASSES).contains(weightClass))
			url = Constants.weightClassURL.replaceAll("W_CLASS", weightClass).replaceAll("PAGE",
					Integer.toString(page));
		else
			url = Constants.fallbackWeightClassURL.replaceAll("PAGE", Integer.toString(page));

		while (parseWeightClassPage(url)) {
			page += 20;
			if (Arrays.asList(Constants.WEIGHT_CLASSES).contains(weightClass))
				url = Constants.weightClassURL.replaceAll("W_CLASS", weightClass).replaceAll("PAGE",
						Integer.toString(page));
			else
				url = Constants.fallbackWeightClassURL.replaceAll("PAGE", Integer.toString(page));
		}
	}

	public void parseAllFighters() {
		int page = 0;
		int classNumber = 0;
		String url;

		while (classNumber < Constants.WEIGHT_CLASSES.length) {
			url = Constants.weightClassURL.replaceAll("W_CLASS", Constants.WEIGHT_CLASSES[classNumber])
					.replaceAll("PAGE", Integer.toString(page));
			// Loop through weight class
			while (parseWeightClassPage(url)) {
				page += 20;
				url = Constants.weightClassURL.replaceAll("W_CLASS", Constants.WEIGHT_CLASSES[classNumber])
						.replaceAll("PAGE", Integer.toString(page));
			}
			// Move to next weight class
			page = 0;
			classNumber++;
		}
	}

	public Boolean parseWeightClassPage(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			String title = doc.title();
			System.out.println(title);

			// Gets the "cell-inner" for each fighter. Each fighter has 4
			// "cell-inner'sections that contain data about them
			Elements infoCells = doc.select("div.content-inner > div.fighter-listing-page > "
					+ "div.content-section > div > div.main-section > div.tab-content > "
					+ "table > tbody > tr.fighter > td > div.cell-inner");

			// If there are no fighters on the page
			if (infoCells.text().toString().length() <= 0)
				return false;

			String name = "test";
			String nickname = "test";
			int wins = -1;
			int losses = -1;
			int draws = -1;
			int weight = -1;
			int height = -1;

			int cellCounter = 0;
			for (Element cell : infoCells) {
				if (cellCounter >= 4) {
					Fighter f = new Fighter(name, nickname, height, weight, wins, losses, draws);
					fighters.add(f);
					cellCounter = 0;
				}
				if (cell.select("div.fighter-info > div > a.fighter-name") != null && cellCounter == 0) {
					if (cell.select("div.fighter-info > div > a.fighter-name").text().toString().length() <= 0)
						continue;
					else
						name = cell.select("div.fighter-info > div > a.fighter-name").text().toString();
					if (cell.select("div.fighter-nickname").text().toString().length() != 0) {
						nickname = cell.select("div.fighter-nickname").text().toString();
					}
					cellCounter++;
				}
				if (cell.select("div.sub-txt") != null && cell.select("div.sub-txt").text().toString().contains("W")
						&& cellCounter == 1) {
					String[] record = cell.select("div.main-txt").text().toString().split("-|\\,");
					wins = Integer.parseInt(record[0]);
					losses = Integer.parseInt(record[1]);
					draws = Integer.parseInt(record[2]);

					cellCounter++;
				}
				if (cell.select("div.sub-txt") != null && cell.select("div.sub-txt").text().toString().contains("cm")
						&& cellCounter == 2) {
					String[] cm = cell.select("div.sub-txt").text().toString().split(" ");
					height = Integer.parseInt(cm[1]);
					cellCounter++;
				}
				if (cell.select("div.sub-txt") != null && cell.select("div.sub-txt").text().toString().contains("kg")
						&& cellCounter == 3) {
					String[] lbs = cell.select("div.main-txt").text().toString().split(" ");
					weight = Integer.parseInt(lbs[0]);
					cellCounter++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void parseFighterRankings() {
		try {
			Document doc = Jsoup.connect(Constants.rankingsURL).get();
			String title = doc.title();
			System.out.println(title);

			Elements rankingCells = doc.select("div.content-inner > div#fighter-rankings > div.content-section > "
					+ "div.content-section-inner > div.main-section > div#ranking-lists");
			
			//TODO add ranking for champions 
			for (Element cell : rankingCells) {
				// used to check if in pound for pound ranking list
				boolean pfp = false;
				if (cell.select("div.ranking-list > div.weight-class-name").text().toString().contains("Pound")) {
					pfp = true;
				}
				
				// then set fighter rank to 0 (Champion)
				System.out.println(cell.select("div.rankings-champions > div > span.fighter-name > a").text());
				
				int i = 1;
				for (Element rank : cell.select("div.rankings-table > table > tbody > tr > td.name-column > a")) {
					//TODO set player rank here
					//System.out.println(rank.text() + " " + i++);
					
					if (pfp) {
						pfp = false;
					}
					// loop back around to for the next class of fighters
					if (i == 16) {
						i = 1;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Fighter> getFighters() {
		return this.fighters;
	}

}
