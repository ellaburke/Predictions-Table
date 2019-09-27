package GroupA;

import java.io.Serializable;

public class MyPrediction implements Serializable {
	
	String name;
	String team;
	int score;
	double betAmount;
	
	public MyPrediction()
	{
		name = "";
		team = "";
		score = 0;
		betAmount = 0.00;
	}
	
	public MyPrediction(String n, String t, int s, double amt) {
		name = n;
		team = t;
		score = s;
		betAmount = amt;	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Double getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(double amt) {
		this.betAmount = amt;
	}
}
