import Helpers.Constants;

public class ClientLoader {
	public static void main(String[] args) {
		FightFinder finder = new FightFinder();
		// 0 is the first page
		finder.getWeightClass(Constants.WEIGHT_CLASSES[2],0);
	}
}
