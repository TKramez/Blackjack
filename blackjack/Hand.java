import java.util.ArrayList;


public class Hand {
	private ArrayList<Card> playerHand = new ArrayList<Card>();

	public Hand() {

	}

	public Hand(Card one) {
		playerHand.add(one);
	}

	public Hand(Card one, Card two) {
		playerHand.add(one);
		playerHand.add(two);
	}

	public int getPoints() {
		int points = 0;

		for (int i = 0; i < playerHand.size(); i++) {
			points += playerHand.get(i).getFaceValue();
		}

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

	public void addCard(Card a) {

		playerHand.add(a);
	}

	public void printHand() {
		for (int i = 0; i < playerHand.size(); i++)
			System.out.println(playerHand.get(i).getNumber() + " " + playerHand.get(i).getSuit());
	}
}
