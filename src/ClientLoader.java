import Helpers.Constants;

public class ClientLoader {
	public static void main(String[] args) {
		FightFinder finder = new FightFinder();
		
		/*
		finder.parseAllFighters();
		
		for (Fighter f: finder.getFighters()) {
			System.out.println(f.getName());
		}
		*/
		
		finder.parseFighterRankings();
	}
}