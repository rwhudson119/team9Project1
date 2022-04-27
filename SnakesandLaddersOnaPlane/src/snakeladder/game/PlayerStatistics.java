package snakeladder.game;

import java.util.ArrayList;

public class PlayerStatistics {
	
	private String playerName;
	private int numberOfDice;
	private ArrayList<Integer> playerRolls;
	private int traverseUp;
	private int traverseDown;
	
	public PlayerStatistics(String playerName, int numberOfDice) {
		this.playerName = playerName;
		this.numberOfDice = numberOfDice;
		this.traverseUp = 0;
		this.traverseDown = 0;
		playerRolls = new ArrayList<Integer>();
	}
	
	public void addUp() {
		traverseUp++;
	}
	
	public void addDown() {
		traverseDown++;
	}
	
	public void addRoll(int val) {
		playerRolls.add(val);
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	private void printRolls() {
		int arrayLength = 6*this.numberOfDice - this.numberOfDice + 1;
		int[] rollTotals = new int[arrayLength];
		for(int i = 0;i<arrayLength;i++) {
			rollTotals[i] = 0;
		}
		for(int num : playerRolls) {
			rollTotals[num-numberOfDice]++;
		}
		int counter = numberOfDice;
		System.out.print(playerName + " rolled: " + counter + "-" + rollTotals[0]);
		counter++;
		for(int i = 1; i<arrayLength; i++) {
			System.out.print(", " + counter + "-" + rollTotals[i]);
			counter++;
		}
		System.out.print("\n");
	}
	
	private void printTraversal() {
		System.out.println(playerName + " traversed: up-"+ traverseUp +", down-" + traverseDown);
	}
	
	public void printStatistics() {
		printRolls();
		printTraversal();
		
	}

}
