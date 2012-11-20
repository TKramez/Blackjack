package blackjack.text;

import java.util.Vector;


public class Player {
	private String name;
	private double wallet, bet;
	private Vector<Hand> myHands = new Vector<Hand>()
			;
	public Player() {
		name = "Joe";
		wallet = 100.0;
		bet = 0.0;
	}
	
	public Player(String name) {
		this.name = name;
		wallet = 100.0;
		bet = 0.0;
	}
	
	public void addHand(Hand newHand) {
		myHands.add(newHand);
	}
	
	public void removeAllHands() {
		myHands.clear();
	}
	
	public Hand getHand() {
		return myHands.get(0);
	}
	
	public Hand getHand(int i) {
		return myHands.get(i);
	}
	
	public int getNumberOfHands() {
		return myHands.size();
	}
	
	public String getName() {
		return name;
	}
	
	public double getBet() {
		return bet;
	}
	
	public void setBet(double newbet) {
		bet = newbet;
	}
	
	public double getWallet() {
		return wallet;
	}
	
	public void addToWallet(double win) {
		wallet += win;
	}
	
	public void takeFromWallet(double lose) {
		wallet -= lose;
	}
	
	public boolean canDouble(Hand hand) {
		return ((hand.sizeOfHand() == 2) && (wallet >= (bet * 2)));
	}
	
	public boolean canSplit(Hand hand) {
		boolean cardOneEqualCardTwo = (hand.getCard(0).getNumber().equals(hand.getCard(1).getNumber()));
		
		return ((hand.sizeOfHand() == 2) && cardOneEqualCardTwo && (wallet >= (bet * 2)));
	}
}
