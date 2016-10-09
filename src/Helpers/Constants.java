package Helpers;

public class Constants {
	public static final String[] WEIGHT_CLASSES = {
			"Flyweight",
			"Bantamweight",
			"Featherweight",
			"Lightweight",
			"Welterweight",
			"Middleweight",
			"Light_Heavyweight",
			"Heavyweight",
			"Women_Strawweight",
			"Women_Bantamweight"
	};
	
	public static final String weightClassURL = "http://www.ufc.com/fighter/Weight_Class/W_CLASS?offset=PAGE&max=20&sort=lastName&order=asc";
	public static final String fallbackWeightClassURL = "http://www.ufc.com/fighter/Weight_Class?offset=PAGE&max=20&sort=lastName&order=asc";
	public static final String rankingsURL = "http://www.ufc.com/rankings";
}
