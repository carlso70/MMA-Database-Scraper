import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import Helpers.Constants;

import java.util.*;
import java.io.*;

// This class scrapes through for fighter info

public class FightFinder {
	private String weightClassURL;
	private List<Fighter> fighters;

	public FightFinder() {
		this.weightClassURL = "http://www.ufc.com/fighter/Weight_Class/";
		fighters = new ArrayList<Fighter>();
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

		String name = "test";
		String nickname = "test";
		int wins = -1;
		int losses = -1;
		int draws = -1;
		int weight = -1;
		int height = -1;
		try {
			Document doc = Jsoup.connect(url).get();
			String title = doc.title();
			System.out.println(title);

			// Gets the "cell-inner" for each fighter. Each fighter has 4
			// "cell-inner'sections that contain data about them
			Elements infoCells = doc.select("div.content-inner > div.fighter-listing-page > "
					+ "div.content-section > div > div.main-section > div.tab-content > "
					+ "table > tbody > tr.fighter > td > div.cell-inner");

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

			for (Fighter f : fighters) {
				System.out.println(f.getName() + " has this many wins " + f.getWins());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
