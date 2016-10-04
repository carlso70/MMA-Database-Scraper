import Helpers.Constants;

public class ClientLoader {
	public static void main(String[] args) {
		FightFinder finder = new FightFinder();
		finder.getWeightClass(Constants.WEIGHT_CLASSES[1]);
	}
}
