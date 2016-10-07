
public class Fighter {
	private String name;
	private String nickname;
	private int weight;
	private int wins, losses, draws;

	// Height is in cm, weight is in lbs
	public Fighter(String name, String nickname, int height, int weight, int wins, int losses, int draws) {
		this.name = name;
		this.nickname = nickname;
		this.weight = weight;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
	}
	
	public Fighter() {}
	
	public void setName(String name) { this.name = name; }
	public void setNickname(String nickname) { this.nickname = nickname; }
	public void setWeight(int weight) { this.weight = weight; }
	public void setWins(int wins) { this.wins = wins; }
	public void setLosses(int losses) { this.losses = losses; }
	public void setDraws(int draws) { this.draws = draws; }
	
	public String getName() { return this.name; }
	public String getNickname() { return this.nickname; }
	public int getWeight() { return this.weight; }
	public int getWins() { return this.wins; }
	public int getLosses() { return this.losses; }
	public int getDraws() { return this.draws; }
}
