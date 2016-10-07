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

			System.out.println(infoCells);
			int cellCounter = 0;
			for (Element cell : infoCells) {
				if (cellCounter >= 4) {
					Fighter f = new Fighter(name, nickname, height, weight, wins, losses, draws);
					f.getName();
					fighters.add(f);
					cellCounter = 0;
				}
				if (cell.select("div.fighter-info") != null) {
					name = cell.select("div.fighter-info > div > a").text().toString();
					nickname = cell.select("div.fighter-nickname").text().toString();
					System.out.println("name at counter - " + cellCounter);
					cellCounter++;
				}
				if (cell.select("div.sub-txt") != null && cell.select("div.sub-txt").text().toString().contains("W")) {
					String[] record = cell.select("div.main-txt").text().toString().split("-|\\,");
					wins = Integer.parseInt(record[0]);
					losses = Integer.parseInt(record[1]);
					draws = Integer.parseInt(record[2]);

					System.out.println("wins/losses/draws at counter - " + cellCounter);
					cellCounter++;
				}
				if (cell.select("div.sub-txt") != null && cell.select("div.sub-txt").text().toString().contains("cm")) {
					String[] cm = cell.select("div.sub-txt").text().toString().split(" ");
					height = Integer.parseInt(cm[1]);
					System.out.println("height at counter - " + cellCounter);
					cellCounter++;
				}
				if (cell.select("div.sub-txt") != null && cell.select("div.sub-txt").text().toString().contains("kg")) {
					String[] lbs = cell.select("div.main-txt").text().toString().split(" ");
					weight = Integer.parseInt(lbs[0]);
					System.out.println("weight at counter - " + cellCounter);
					cellCounter++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
