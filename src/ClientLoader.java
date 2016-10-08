import Helpers.Constants;

public class ClientLoader {
	public static void main(String[] args) {
		FightFinder finder = new FightFinder();
		// 0 is the first page
		finder.getWeightClass(Constants.WEIGHT_CLASSES[1],0);
		
		for (Fighter f: finder.getFighters()) {
			System.out.println(f.getName());
		}
	}
}