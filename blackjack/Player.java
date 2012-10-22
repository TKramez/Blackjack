import java.util.ArrayList;


public class Player {
	private String name;
	private int wallet;
	private ArrayList<Hand> myHand = new ArrayList<Hand>();

	public Player() {
		this.name = "Joe";
		wallet = 100;
	}

	public Player(String name) {
		this.name = name;
		wallet = 100;
	}

	public String getName() {
		return name;
	}

	public void addHand(Hand a) {
		myHand.add(a);
	}

	public void removeHand() {
		myHand.remove(1);
	}

	public Hand getHand() {
		return myHand.get(0);
	}

	public int getNumberOfHands() {
		return myHand.size();
	}

	public int getWallet() {
		return wallet;
	}

	public void addToWallet(int winnings) {
		wallet += winnings;
	}

	public void takeFromWallet(int lose) {
		wallet -= lose;
	}
	
	public boolean canSplit() {
		boolean canSplit = false;
		if ((getNumberOfHands() == 1) && myHand.get(0).isSplittable())
			canSplit = true;
		return canSplit;
	}
}
