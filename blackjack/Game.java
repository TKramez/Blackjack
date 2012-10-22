package blackjack;

/*
 * stuff we need to add
 * 
 * -place bet
 * -rules for "double bet"
 * -check if player won
 * -add dealer implementation
 * -add payout method
 * -input error checking
 */


import java.util.Scanner;

public class Game {
	static Scanner userIn = new Scanner(System.in);
	static Deck deck1 = new Deck();

	public static void main(String[] args) {
		Dealer dealer = new Dealer();
		Player playerList[] = getPlayerList();
		String playAgainTemp;
		boolean playAgain = true;

		while (playAgain) {
			deck1.shuffleDeck();

			for (int i = 0; i < playerList.length; i++) {
				Player currentPlayer = playerList[i];
				playHands(currentPlayer);
			}
			System.out.println("Would you like to play again (yes or no)?" );
			playAgainTemp = userIn.next().toLowerCase();
			
			if (playAgainTemp == "no") {
				playAgain = false;
			}
		}
	}

	public static Player[] getPlayerList() {
		final int MAX_PLAYERS = 3, MIN_PLAYERS = 1;
		Player playerList[];
		int numPlayers = 0;
		String playerName;


		System.out.print("Welcome to Blackjack 2.0! \nHow many people are playing (1-3)?  ");

		do {
			numPlayers = userIn.nextInt();
			if (numPlayers < MIN_PLAYERS)
				System.out.printf("Sorry, you must have at least %d player. \nHow many people are playing?  ", MIN_PLAYERS);
			else if (numPlayers > MAX_PLAYERS)
				System.out.printf("Sorry, you cannot have more than %d players. \nHow many people are playing?  ", MAX_PLAYERS);

		} while (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS);

		playerList = new Player[numPlayers];
		for (int i = 0; i < playerList.length; i++) {
			System.out.printf("What is player%d's name?  ", i + 1);
			playerName = userIn.next();

			playerList[i] = new Player(playerName);
		}

		return playerList;
	}

	public static void playHands(Player curPlayer) {
		boolean bust = false,
				invalidMove;
		String playerAction;

		Hand hand1;
		hand1 = new Hand(deck1.getNextCard(), deck1.getNextCard());
		curPlayer.addHand(hand1);

		while (!bust) {
			invalidMove = true;
			System.out.println(curPlayer.getName() + " here is your hand: ");
			curPlayer.getHand().printHand();

			if (curPlayer.canSplit())
				System.out.print("What would you like to do (Split, Double, Stay, Hit)?  ");
			else
				System.out.print("What would you like to do (Double, Stay, Hit)?  ");

			playerAction = userIn.next().toLowerCase();

			while (invalidMove) {			
				if(playerAction.equals("split")) {
					if (curPlayer.canSplit()) {
						curPlayer.addHand(curPlayer.getHand().split());
						invalidMove = false;
					}
					else
						System.out.println("Sorry, this hand can not be split.");
				}
				else if(playerAction.equals("double")) {
					invalidMove = false;
				}
				else if(playerAction.equals("stay")) {
					return;
				}
				else if(playerAction.equals("hit")) {
					curPlayer.getHand().addCard(deck1.getNextCard());
					invalidMove = false;
				}
				else {
					System.out.print("Invalid choice, choose again:  ");
					playerAction = userIn.next();
				}
			}
			
			if (curPlayer.getHand().getPoints() > 21)
				bust = true;
			else if (curPlayer.getHand().getPoints() == 21)
				return;
			
		}
	}
}
