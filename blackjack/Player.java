package blackjack;

import java.util.Vector;

public class Player {
	private String name;
	private double wallet;
	private double bet;
	private Vector<Hand> myHand = new Vector<Hand>();

	public Player() {
		this.name = "Joe";
		wallet = 100.00;
		bet = 0.0;
	}

	public Player(String name) {
		this.name = name;
		wallet = 100.00;
		bet = 0.0;
	}
	
	public void playHand(Deck deck) {
		boolean bust = false,
				invalidMove;
		String playerAction;

		while (!bust) {
			invalidMove = true;
			System.out.println(this.getName() + " here is your hand: ");
			this.getHand().printHand();

			if (this.canSplit())
				System.out.print("What would you like to do (Split, Stay, Hit)? ");
			else if (this.canDouble()) {
				System.out.print("What would you like to do (Double, Stay, Hit)? ");
			}
			else if (this.canSplit() && this.canDouble()) {
				System.out.println("What would you like to do (Double, Split, Stay, Hit)? ");
			}
			else
				System.out.print("What would you like to do (Stay, Hit)?  ");

			playerAction = Game.scan.next();

			while (invalidMove) {			
				if(playerAction.equalsIgnoreCase("split")) {
					if (this.canSplit()) {
						this.addHand(this.getHand().split());
						invalidMove = false;
					}
					else
						System.out.println("Sorry, this hand can not be split.");
				}
				else if(playerAction.equalsIgnoreCase("double")) {
					if (this.canDouble()) {
						this.setBet(this.getBet() * 2.0);
						this.getHand().addCard(deck.getNextCard());
						System.out.println(this.getName() + " here is your hand: ");
						this.getHand().printHand();
						
						if (this.getHand().getPoints() > 21) {
							bust = true;
							System.out.println("BUST!");
						}
						else if (this.getHand().getPoints() == 21)
							System.out.println("21!");
						
						invalidMove = false;
						bust = true;
					}
					else
						System.out.println("Sorry, this hand can not be doubled.");
				}
				else if(playerAction.equalsIgnoreCase("stay")) {
					invalidMove = false;
					bust = true;
				}
				else if(playerAction.equalsIgnoreCase("hit")) {
					Card draw;
					this.getHand().addCard(draw = deck.getNextCard());
					System.out.println("You drew " + draw.toString());
					invalidMove = false;
				}
				else {
					System.out.print("Invalid choice, choose again:  ");
					playerAction = Game.scan.next();
				}
			}
			
			if (this.getHand().getPoints() > 21) {
				bust = true;
				System.out.println("BUST!");
			}
			else if (this.getHand().getPoints() == 21) {
				System.out.println("21!");
				return;
			}
		}
	}

	private boolean canDouble() {
		boolean canDouble = false;
		
		if (this.getHand().sizeOfHand() == 2)
			canDouble = true;
		
		return canDouble;
	}

	public String getName() {
		return name;
	}

	public void addHand(Hand a) {
		myHand.add(a);
	}

	public void removeHand() {
		while (myHand.size() > 0)
			myHand.remove(0);
	}

	public Hand getHand() {
		return myHand.get(0);
	}

	public int getNumberOfHands() {
		return myHand.size();
	}

	public double getWallet() {
		return wallet;
	}
	
	public double getBet() {
		return bet;
	}
	
	public void setBet(double bet) {
		this.bet = bet;
	}

	public void addToWallet(double winnings) {
		wallet += winnings;
	}

	public void takeFromWallet(double lose) {
		wallet -= lose;
	}
	
	public boolean canSplit() {
		boolean canSplit = false;
		if ((getNumberOfHands() == 1) && myHand.get(0).isSplittable())
			canSplit = true;
		return canSplit;
	}
}
