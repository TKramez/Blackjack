package blackjack;

import java.util.Vector;

public class Hand {
	private Vector<Card> playerHand;

	public Hand() {
		playerHand = new Vector<Card>();
	}

	public Hand(Card one) {
		playerHand = new Vector<Card>();
		playerHand.add(one);
	}

	public Hand(Card one, Card two) {
		playerHand = new Vector<Card>();
		playerHand.add(one);
		playerHand.add(two);
	}

	public int getPoints() {
		int points = 0;

		for (int i = 0; i < playerHand.size(); i++) {
			points += playerHand.get(i).getFaceValue();
		}
		
		if (points > 21 && this.hasAce())
			points -= 10;

		return points;
	}

	public boolean isSplittable() {
		boolean splittable;
		if (playerHand.size() == 2 && (playerHand.get(0).getFaceValue() == playerHand.get(1).getFaceValue()))
			splittable = true;
		else
			splittable = false;
		return splittable;
	}

	public Hand split() {
		Hand newHand = new Hand(playerHand.get(1));
		playerHand.remove(1);

		return newHand;
	}
	
	public boolean hasAce() {
		for (Card h : playerHand) {
			if (h.getNumber().equalsIgnoreCase("ace"))
				return true;
		}
		return false;
	}

	public void addCard(Card a) {
		playerHand.add(a);
	}

	public void printHand() {
		for (int i = 0; i < playerHand.size(); i++)
			System.out.println(playerHand.get(i).toString());
	}
	
	public Card getCard(int num) {
		return playerHand.get(num);
	}
	
	public int sizeOfHand() {
		return playerHand.size();
	}
}
