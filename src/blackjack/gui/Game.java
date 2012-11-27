package blackjack.gui;
/*
 * @Author Jordan Greenfield, Tyler Kramer,
 * 		   Brandon Turner, Kyle Nyland 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Game extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7966706612011315270L;
	private static final int MAX_PLAYERS = 3;
	private static final int MAX_HANDS_PER_PLAYER = 3;
	private static final int MAX_CARDS_PER_HAND = 10;
	
	private static Vector<Player> playerList = new Vector<Player>();
	private static Dealer dealer = new Dealer();
	private static Deck deck = new Deck();
	private static String playerMove = "";
	private static boolean dealerInitialized;
	
	private static JTextArea dialogue = new JTextArea();
	private static JTextField[] putBets = new JTextField[MAX_PLAYERS];
		
	private static JLabel[] playerSpots = new JLabel[MAX_PLAYERS];
	private static JLabel[] playerWallets = new JLabel[MAX_PLAYERS];
	private static JLabel[] currentBets = new JLabel[MAX_PLAYERS];
	
	private static JButton jbtHit = new JButton("Hit");
	private static JButton jbtStay = new JButton("Stay");
	private static JButton jbtDouble = new JButton("Double");
	private static JButton jbtSplit = new JButton("Split");
	private static JButton[] jbtBets = new JButton[MAX_PLAYERS];
	
	private static Image playerCards[][][] = new Image[MAX_PLAYERS][MAX_HANDS_PER_PLAYER][MAX_CARDS_PER_HAND];
	private static Image dealerCards[] = new Image[10];
	
	public Game() {
		//Draws the background of the window and makes it possible to draw
		//and add graphical components
		setLayout(null);
		GetBlackjackTable blkjck = new GetBlackjackTable();
		blkjck.setBounds(0, 0, 900, 600);
		
		//Creates a box that will display messages to the user(s)
		dialogue.setLineWrap(true);
		dialogue.setWrapStyleWord(true);
		dialogue.setEditable(false);
		dialogue.setForeground(Color.WHITE);
		dialogue.setOpaque(false);
		
		//Allows the text to "scroll" after the original box has no more space,
		//the box will automatically scroll to the bottom of the text block
		JScrollPane scroller= new JScrollPane(dialogue);
		scroller.setOpaque(false);
		scroller.getViewport().setOpaque(false);
		scroller.setBounds(550, 10, 340, 150);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			public void adjustmentValueChanged(AdjustmentEvent e) {  
			e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}}); 
		add(scroller);
		
		//Adds a "Hit" button to the window
		jbtHit.setBounds(545, 170, 80, 30);
		jbtHit.addActionListener(this);
		add(jbtHit);
		
		//Adds a "Stay" button to the window
		jbtStay.setBounds(635, 170, 80, 30);
		jbtStay.addActionListener(this);
		add(jbtStay);
		
		//Adds a "Double" button to the window
		jbtDouble.setBounds(725, 170, 80, 30);
		jbtDouble.addActionListener(this);
		add(jbtDouble);
		
		//Adds a "Split" button to the window
		jbtSplit.setBounds(815, 170, 80, 30);
		jbtSplit.addActionListener(this);
		add(jbtSplit);
		
		//Sets up each player's seat in the game window
		for (int i = 0; i < playerSpots.length; i++) {
			
			//If there was a name entered for the player spot, their name will appear on
			//the board. Spots where there was no name entered will display "(Empty Seat)"
			if (i < playerList.size())
				playerSpots[i] = new JLabel(playerList.get(i).getName());
			else
				playerSpots[i] = new JLabel("(Empty Seat)");
			
			playerSpots[i].setBounds(10 + 300 * i, 300, 300, 15);
			playerSpots[i].setForeground(Color.WHITE);	//Sets the text to white
			add(playerSpots[i]);
			
			//Displays how much money each player currently has
			playerWallets[i] = new JLabel();
			playerWallets[i].setBounds(10 + 300 * i, 315, 280, 15);
			playerWallets[i].setForeground(Color.WHITE);	//Sets the text to white
			add(playerWallets[i]);
			
			//Displays how much money each player is betting on their current hand(s)
			currentBets[i] = new JLabel();
			currentBets[i].setBounds(10 + 300 * i, 330, 280, 15);
			currentBets[i].setForeground(Color.WHITE);
			add(currentBets[i]);
			
			//Creates a text field that allows the user to type in how much they would like to bet
			putBets[i] = new JTextField();
			putBets[i].setBounds(10 + 300 * i, 570, 80, 20);
			add(putBets[i]);
			
			//Creates a button that, when pressed, submits the user's bet they placed in the text field
			jbtBets[i] = new JButton("Bet");
			jbtBets[i].setBounds(100 + 300 * i, 570, 65, 20);
			jbtBets[i].addActionListener(this);
			add(jbtBets[i]);
		}
		
		//Creates the dealers spot on the table
		JLabel dealerSeat = new JLabel("The Dragon (Dealer)");
		dealerSeat.setBounds(170, 100, 150, 15);
		dealerSeat.setForeground(Color.WHITE);
		add(dealerSeat);
		
		//Displays the background
		add(blkjck);
		

		dialogue.append("Welcome to Blackjack by Team Dragon!");
	}
	
	public Game(Game table) throws InterruptedException {
		do {
			clearAllCards(table);
			preGameSetup();				//Prepares for the game to be played
			playGame(table);			//Plays through each player's hand in the game
			determineWinners();			//After everyone has played their hands, the game calculates the dinners!
			removeBrokePlayers();		//If a player has run out of money, they are kicked out of the game.
			dealerInitialized = false;	//Remove initialization trigger for dealer (in preparation for reset)
		} while (playAgain());			//Checks if each players wants to play again.

		dialogue.append("\nThere are no more players in the game. \nThanks for playing!");
			
		//After there are no players left in the game, asks the user if they want to restart the game and add more player
		int again;
		again = JOptionPane.showOptionDialog(this, "There are no more players, reset game?", "Reset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		//Close the old window
		table.dispose();
		//Clear the text area to prepare it for the game reset
		dialogue.setText("");
		
		//Reset game if play player requests
		if (again == 0)
			main(new String[0]);
		else
			System.exit(0);
	}
	
	public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtHit)	//When the hit button is pressed, gives the player another card
				playerMove = "hit";
			if (e.getSource() == jbtStay)	//When the stay button is pressed, ends the players turn (on the current hand)
				playerMove = "stay";
			if (e.getSource() == jbtDouble)	//When the double button is pressed, doubles the players bet, adds 1 card to the
				playerMove = "double";		//hand and ends the players turn
			if (e.getSource() == jbtSplit)	//When the split button is pressed, splits the players hand
				playerMove = "split";
			if (e.getSource() == jbtBets[0]) //When this button is hit, sets player1's bet to the value in the box to the left
				placeBet(0, putBets[0].getText(), jbtBets[0]);
			if (e.getSource() == jbtBets[1]) //When this button is hit, sets player2's bet to the value in the box to the left
				placeBet(1, putBets[1].getText(), jbtBets[1]);
			if (e.getSource() == jbtBets[2]) //When this button is hit, sets player3's bet to the value in the box to the left
				placeBet(2, putBets[2].getText(), jbtBets[2]);
	}
	
	class GetBlackjackTable extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9148830009531016643L;

		@Override
		protected void paintComponent(Graphics g) {
			//Clears the window so it can be updated
			super.paintComponent(g);

			//Paints the updated image(s) inside of the window
			ImageIcon icon1 = new ImageIcon(GetBlackjackTable.class.getResource("resources/DragonBoard.jpg"));
			Image background = icon1.getImage();
			
			ImageIcon icon2 = new ImageIcon(GetBlackjackTable.class.getResource("resources/Face Down.png"));
			Image facedown = icon2.getImage();
			
			g.drawImage(background, 0, 0, 900, 600, this);
			
			//Draws the players' cards on the screen
			for (int i = 0; i < playerCards.length; i++) {
				for (int j = 0; j < playerCards[0].length; j++) {
					for (int k = 0; k < playerCards[0][0].length; k++) {
						g.drawImage(playerCards[i][j][k], 10 + 300 * i + 30 * k, 350 + 70 * j, 49, 65, this);
					}
				}
			}
			
			//Draws the dealer's cards on the screen
			for (int i = 0; i < dealerCards.length; i++) {
				if (dealerInitialized) {
					if (dealer.getHand().sizeOfHand() == 2) {
						if (i == 0)
							g.drawImage(dealerCards[i], 170, 120, 49, 65, this);
						else if (i == 1)
							g.drawImage(facedown, 170 + 30 * i, 120, 49, 65, this);
					}
				}
				else if (dealerCards[i] != null)
					g.drawImage(dealerCards[i], 170 + 30 * i, 120, 49, 65, this);
			}
		}
		
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		//Creates a window which greets the player(s) and requests the name(s).
		//This window cannot be closed or resized and opens in the center of the screen.
		GreetAndMakePlayerList greeter = new GreetAndMakePlayerList();
		greeter.setSize(306, 268);
		greeter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		greeter.setLocationRelativeTo(null);
		greeter.setTitle("Blackjack by Team Dragon");
		greeter.setResizable(false);
		greeter.setVisible(true);
		
		//Waits for input from the greeting window.  Once the user(s) has entered
		//the name(s), the list of players is created.
		while (playerList.isEmpty()) {
			Thread.sleep(500);
			playerList = greeter.getPlayerList();
		}

		//Disposes of the greeting window once the player list is created.
		greeter.dispose();

		//Creates and draws the main playing board in which the player(s) is/are
		//seated to play the game.
		Game table = new Game();
		table.setSize(906, 628);
		table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		table.setLocationRelativeTo(null);
		table.setTitle("Blackjack by Team Dragon");
		table.setResizable(false);
		table.setVisible(true);
		table.setBackground(new Color(12, 24, 2));
		
		new Game(table);
	}
	
	public void preGameSetup() {
		//Re-sets the buttons and repositions the players (if any were dropped) after the first round is played.
		for (int i = 0; i < playerSpots.length; i++) {
			if (i < playerList.size())
				playerSpots[i].setText(playerList.get(i).getName());
			else {
				playerSpots[i].setText("(Empty Seat)");
				playerWallets[i].setText("");
				currentBets[i].setText("");
				jbtBets[i].setVisible(false);
				putBets[i].setVisible(false);
			}
		}
		
		//Initializes & updates the player's wallets and re-sets game components after the first round
		for (int i = 0; i < playerList.size(); i++) {
			playerWallets[i].setText(String.format("Wallet: $%.2f", playerList.get(i).getWallet()));
			currentBets[i].setText(String.format("Current bet: $%.2f", playerList.get(i).getBet()));
			jbtBets[i].setVisible(true);
			putBets[i].setVisible(true);
		}
		
		//Makes sure that all of the players' hands are empty before the game starts
		for (Player p : playerList) {
			p.removeAllHands();
		}

		//Makes sure that dealer's hand is empty before the game starts
		dealer.removeAllHands();

		//Puts the used cards back into the deck and shuffles it at the end of the round
		deck.shuffleDeck();

		//Resets the "Bet" buttons at the start of the new round so players can bet again
		for (int i = 0; i < playerList.size(); i++) {
			jbtBets[i].setEnabled(true);
		}
	}

	public void placeBet(int index, String bet, JButton button) {
		double newBet;
		
		try {
			newBet = Double.parseDouble(bet);
		}
		catch (NumberFormatException f) { //Checks to make sure that the player's input is a number
			dialogue.append("\nSorry, that is not a valid bet.");
			return; //Perform no action if the player's input was not a number
		}
		
		//Checks to make sure that the player is betting at least $1
		if (newBet < 1)
			dialogue.append("\nSorry, the minimum bet is $1.00. Please enter a new bet.");
		
		//Checks to make sure the player has enough money to cover their entered bet
		else if (newBet > playerList.get(index).getWallet())
			dialogue.append("\nThe amount bet exceeds the amount in the wallet. Please enter a new bet.");
		
		//Sets the player's bet if they have a valid input
		else {
			playerList.get(index).setBet(newBet);
			currentBets[index].setText(String.format("Current bet: $%.2f", playerList.get(index).getBet()));
			button.setEnabled(false);
		}
	}
	
	public void playGame(Game table) throws InterruptedException {
		dialogue.append("\nPlease place your bets!");

		//If there is only one player in the game...waits for that player to bet
		//before assigning a bet value and continuing with the rest of the program
		if (playerList.size() == 1)
			while (jbtBets[0].isEnabled()) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		//If there are two players in the game...waits for both players to make a bet
		//before assigning a bet value and continuing with the rest of the program
		if (playerList.size() == 2)
			while (jbtBets[0].isEnabled() || jbtBets[1].isEnabled()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		//If there are three players in the game...waits for all three players to make a bet
		//before assigning a bet value and continuing with the rest 
		if (playerList.size() == 3)
			while (jbtBets[0].isEnabled() || jbtBets[1].isEnabled() || jbtBets[2].isEnabled()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		//Deals the initial cards to each player and the dealer
		dealer.deal(playerList, deck, dealer);
		dealerInitialized = true;
		redrawPlayerCards(table);
		redrawDealerCards(table);
		
		//Displays the cards drawn in the text area
		dialogue.append(String.format("\n%s drew %s.", dealer.getName(), dealer.getHand().getCard(0)));
		for (Player p : playerList) {
			dialogue.append(String.format("\n%s was dealt %s and %s.", p.getName(), p.getHand().getCard(0), p.getHand().getCard(1)));
		}
		
		//Allows the players to play through their hand(s), one player at a time
		for (Player p : playerList) {
			playHand(p, p.getHand(), table);
		}
		
		//After all of the players have finished their turns, the dealer
		//is told to play through his/her hand according to a certain logic
		dealer.playHand(deck, dialogue, table);
	}

	public void playHand(Player player, Hand hand, Game table) {
		boolean stillPlayingHand = true;
		
		//If a player is dealt a total of 21 with the two initial cards, they win and there is no
		//need to take further action on the current hand
		if (hand.getPoints() == 21) {
			dialogue.append(String.format("\n%s scored a natural 21! You win this hand!", player.getName()));
			return;
		}
		
		dialogue.append(String.format("\nIt is %s's turn! \nWhat's your move?", player.getName()));
		
		//As long as the player is still able to take action on the hand, the following can be done
		while (stillPlayingHand) {
			//Shows that the player has not taken action yet
			playerMove = "";
			
			//Turns the split and double buttons off until it is known whether the player
			//is able to take these actions
			jbtSplit.setEnabled(false);
			jbtDouble.setEnabled(false);
			
			//If the player is allowed to split the current hand, the button will be usable
			if (player.canSplit(hand))
				jbtSplit.setEnabled(true);
			//If the player is allowed to double the current hand, the button will be usable
			if (player.canDouble(hand))
				jbtDouble.setEnabled(true);
			
			//Waits for the player to make a move (click a button)
			while (playerMove.equals("")) {
				try {
					Thread.sleep(300);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//Splits, doubles, stays, or hits - depending on what button the player hit
			if (playerMove.equals("split")) {
				hand = splitHand(player, hand, table);
				dialogue.append("\nWhat's your move for the next hand (above)?");
			}
			else if (playerMove.equals("double")) {
				player.setBet(player.getBet() * 2);
				Card draw = deck.getNextCard();
				dialogue.append(String.format("\n%s drew %s.", player.getName(), draw.toString()));
				hand.addCard(draw);
				stillPlayingHand = false;
				currentBets[playerList.indexOf(player)].setText(String.format("Current bet: $%.2f", player.getBet()));
			}
			else if (playerMove.equals("stay"))
				stillPlayingHand = false;
			else if (playerMove.equals("hit")) {
				Card draw = deck.getNextCard();
				dialogue.append(String.format("\n%s drew %s.", player.getName(), draw.toString()));
				hand.addCard(draw);
			}
			
			//If the player achieves a score of 21, the hand is complete and is no longer playable
			if (hand.getPoints() == 21) {
				dialogue.append("\nYou scored a 21!");
				stillPlayingHand = false;
			}
			//If the player goes over the score limit of 21, the hand is complete and is no longer playable
			else if (hand.getPoints() > 21) {
				dialogue.append("\nBUST!");
				stillPlayingHand = false;
			}
			redrawPlayerCards(table);
		}
	}
	
	public Hand splitHand(Player player, Hand oldHand, Game table) {
		//Draws two more cards, one for the new hand, one for the old hand.
		Card oldHandCard = deck.getNextCard();
		dialogue.append(String.format("\n%s was dealt %s for hand %d.", player.getName(), oldHandCard.toString(), player.getNumberOfHands()));
		Card newHandCard = deck.getNextCard();
		dialogue.append(String.format("\n%s was dealt %s for hand %d.", player.getName(), newHandCard.toString(), player.getNumberOfHands() + 1));
		
		//Creates a new hand, using one card from the old hand and drawing one card from the deck; then associates
		//that hand with the player that the old hand came from
		Hand newHand = new Hand(oldHand.getCard(1), newHandCard);
		player.addHand(newHand);

		//Removes the card from the old hand that is not being used in the new hand.
		//Then draws another card from the deck and puts it in the old hand.
		oldHand.removeCard(1);
		oldHand.addCard(oldHandCard);
		
		//Update card drawings.
		redrawPlayerCards(table);

		//Plays through the player's new hand
		dialogue.append(String.format("\nWhat's your move for hand %d", player.getNumberOfHands()));
		playHand(player, newHand, table);
				
		//Plays through the player's old hand
		return oldHand;
	}
	
	public void determineWinners() {
		//Goes through each player and each hand that they have to check for win/loss conditions
		//Subtracts and adds money to the wallets accordingly
		for (Player p : playerList) {
			for (int i = p.getNumberOfHands() - 1; i >= 0; i--) {	
				if (p.getHand(i).getPoints() > 21) {
					dialogue.append(String.format("\n%s loses hand %d!", p.getName(), i + 1));
					p.takeFromWallet(p.getBet());
				}
				else if (dealer.getHand().getPoints() > 21) {
					dialogue.append(String.format("\n%s wins hand %d!", p.getName(), i + 1));
					p.addToWallet(p.getBet());
				}
				else if (p.getHand(i).getPoints() > dealer.getHand().getPoints()) {
					dialogue.append(String.format("\n%s wins hand %d!", p.getName(), i + 1));
					if (p.getHand(i).getPoints() == 21 && p.getHand(i).sizeOfHand() == 2) {
						p.addToWallet(p.getBet() * 1.5);
					}
					else
						p.addToWallet(p.getBet());
				}
				else if (p.getHand(i).getPoints() == dealer.getHand().getPoints()) {
					dialogue.append(String.format("\n%s pushes hand %d!", p.getName(), i + 1));
				}
				else {
					dialogue.append(String.format("\n%s loses hand %d!", p.getName(), i + 1));
					p.takeFromWallet(p.getBet());
				}
			}
			//Updates the values displayed for each player's wallet on the window
			playerWallets[playerList.indexOf(p)].setText(String.format("Wallet: $%.2f", p.getWallet()));
		}
	}

	public void removeBrokePlayers() {
		//Removes players that have no money at the end of the round.
		for (int i = playerList.size() - 1; i >= 0; i--) {
			if (playerList.get(i).getWallet() == 0) {
				dialogue.append(String.format("\n" + playerList.get(i).getName() + " is broke and kicked out of the game!"));
				playerList.remove(playerList.indexOf(playerList.get(i)));
			}
		}
	}

	public boolean playAgain() throws InterruptedException {
		Vector<Player> tempList = new Vector<Player>();
		boolean playAgain;
		
		//Creates a window that asks each player if they would like to play again (1 player at a time)
		for (Player p : playerList) {
			playAgain = JOptionPane.showOptionDialog(this, p.getName() + ", would you like to play again?",
					"Play Again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == 0;
			
			if (playAgain)
				tempList.add(p); //If yes, stored to a temporary list
			else {
				dialogue.append(String.format("\n%s has been removed from the game!", p.getName()));
			}
		}

		//Takes the players from the temporary list and adds them back into the game
		playerList = tempList;

		//If the list is empty, the game is over. Otherwise the next round starts
		if (playerList.isEmpty())
			playAgain = false;
		else {
			playAgain = true;
		}
		
		return playAgain;
	}

	public void redrawPlayerCards(Game table) {
		for (int i = 0; i <= playerList.lastIndexOf(playerList.lastElement()); i++) {
			for (int j = 0; j < playerList.get(i).getNumberOfHands(); j++) {
				for (int k = 0; k < playerList.get(i).getHand(j).sizeOfHand(); k++) {
					playerCards[i][j][k] = playerList.get(i).getHand(j).getCard(k).getCardImage();
				}
			}
		}
		
		table.repaint();
	}
	
	public void redrawDealerCards(Game table) {
		for (int i = 0; i < dealer.getHand().sizeOfHand(); i++) {
			dealerCards[i] = dealer.getHand().getCard(i).getCardImage();
		}
		table.repaint();
	}

	public void clearAllCards(Game table) {
		//Removes player card images
		for (int i = 0; i < playerCards.length; i++) {
			for (int j = 0; j < playerCards[0].length; j++) {
				for (int k = 0; k < playerCards[0][0].length; k++) {
					playerCards[i][j][k] = null;
				}
			}
		}
		
		//Removes dealer card images
		for (int i = 0; i < dealerCards.length; i++) {
			dealerCards[i] = null;
		}
		
		table.repaint();
	}
}