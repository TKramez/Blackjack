/* Still need to do:
 *   - Add card graphics to board.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Game extends JFrame implements ActionListener{
	private final static int MAX_PLAYERS = 3;
	
	private static Scanner scan = new Scanner(System.in);
	private static Vector<Player> playerList;
	private static Deck deck = new Deck();
	private static Dealer dealer = new Dealer();
	private static String playerMove = "";
	
	private static JLabel[] playerSpots = new JLabel[MAX_PLAYERS];
	private static JLabel[] playerWallets = new JLabel[MAX_PLAYERS];
	private static JLabel[] currentBets = new JLabel[MAX_PLAYERS];
	private static JButton[] jbtBets = new JButton[MAX_PLAYERS];
	private static JTextField[] putBets = new JTextField[MAX_PLAYERS];
	private static JTextArea dialogue = new JTextArea();
	
	private static JButton jbtHit = new JButton("Hit");
	private static JButton jbtStay = new JButton("Stay");
	private static JButton jbtDouble = new JButton("Double");
	private static JButton jbtSplit = new JButton("Split");
		

	public Game() {
		setLayout(null);
		GetBlackjackTable blkjck = new GetBlackjackTable();
		blkjck.setBounds(0, 0, 900, 600);
		
		dialogue.setLineWrap(true);
		dialogue.setWrapStyleWord(true);
		dialogue.setEditable(false);
		dialogue.setForeground(Color.WHITE);
		dialogue.setOpaque(false);
		
		JScrollPane scroller= new JScrollPane(dialogue);
		scroller.setOpaque(false);
		scroller.getViewport().setOpaque(false);
		scroller.setBounds(550, 10, 340, 150);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		add(scroller);
		
		scroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			public void adjustmentValueChanged(AdjustmentEvent e) {  
			e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}}); 
		
		jbtHit.setBounds(545, 170, 80, 30);
		jbtHit.addActionListener(this);
		add(jbtHit);
		
		jbtStay.setBounds(635, 170, 80, 30);
		jbtStay.addActionListener(this);
		add(jbtStay);
		
		jbtDouble.setBounds(725, 170, 80, 30);
		jbtDouble.addActionListener(this);
		add(jbtDouble);
		
		jbtSplit.setBounds(815, 170, 80, 30);
		jbtSplit.addActionListener(this);
		add(jbtSplit);
		
		for (int i = 0; i < playerSpots.length; i++) {
			if (i < playerList.size())
				playerSpots[i] = new JLabel(playerList.get(i).getName());
			else
				playerSpots[i] = new JLabel("(Empty Seat)");
		
			playerSpots[i].setBounds(10 + 300 * i, 400, 300, 15);
			playerSpots[i].setForeground(Color.WHITE);
			add(playerSpots[i]);
			
			playerWallets[i] = new JLabel();
			playerWallets[i].setBounds(10 + 300 * i, 420, 280, 15);
			playerWallets[i].setForeground(Color.WHITE);
			add(playerWallets[i]);
			
			currentBets[i] = new JLabel();
			currentBets[i].setBounds(10 + 300 * i, 435, 280, 15);
			currentBets[i].setForeground(Color.WHITE);
			add(currentBets[i]);
			
			jbtBets[i] = new JButton("Bet");
			jbtBets[i].setBounds(100 + 300 * i, 570, 65, 20);
			jbtBets[i].addActionListener(this);
			add(jbtBets[i]);
			
			putBets[i] = new JTextField();
			putBets[i].setBounds(10 + 300 * i, 570, 80, 20);
			add(putBets[i]);
		}
		
		JLabel dealerSeat = new JLabel("The Dragon (Dealer)");
		dealerSeat.setBounds(170, 100, 150, 15);
		dealerSeat.setForeground(Color.WHITE);
		add(dealerSeat);
		
		add(blkjck);
	}
	
	public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtHit)
				playerMove = "hit";
			if (e.getSource() == jbtStay)
				playerMove = "stay";
			if (e.getSource() == jbtDouble)
				playerMove = "double";
			if (e.getSource() == jbtSplit)
				playerMove = "split";
			if (e.getSource() == jbtBets[0]) 
				placeBet(0, putBets[0].getText(), jbtBets[0]);
			if (e.getSource() == jbtBets[1])
				placeBet(1, putBets[1].getText(), jbtBets[1]);
			if (e.getSource() == jbtBets[2])
				placeBet(2, putBets[2].getText(), jbtBets[2]);
		}
	
	class GetBlackjackTable extends JPanel {

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			ImageIcon image = new ImageIcon(GetBlackjackTable.class.getResource("DragonBoard.jpg"));
			Image background = image.getImage();
			g.drawImage(background, 0, 0, 900, 600, this);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		playerList = new Vector<Player>();
		
		GreetAndMakePlayerList greeter = new GreetAndMakePlayerList();
		greeter.setSize(306, 268);
		greeter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		greeter.setLocationRelativeTo(null);
		greeter.setTitle("Blackjack by Team Dragon");
		greeter.setResizable(false);
		greeter.setVisible(true);
		
		while (playerList.isEmpty()) {
			playerList = greeter.getPlayerList();
		}
		
		playerList = greeter.getPlayerList();
		greeter.dispose();
		
		Game table = new Game();
		table.setSize(906, 628);
		table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		table.setLocationRelativeTo(null);
		table.setTitle("Blackjack by Team Dragon");
		table.setResizable(false);
		table.setVisible(true);
		table.setBackground(new Color(12, 24, 2));	
		
		dialogue.append("Welcome to Blackjack by Team Dragon!");
		
		do {
			preGameSetup();
			playGame(table);
			determineWinners();
			removeBrokePlayers();
		} while (playAgain());

		dialogue.append("\nThere are no more players in the game. \nThanks for playing!");
		
	}
	
	public static void preGameSetup() {
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
		
		for (int i = 0; i < playerList.size(); i++) {
			playerWallets[i].setText(String.format("Wallet: $%.2f", playerList.get(i).getWallet()));
			currentBets[i].setText(String.format("Current bet: $%.2f", playerList.get(i).getBet()));
			jbtBets[i].setVisible(true);
			putBets[i].setVisible(true);
		}
		
		for (Player p : playerList) {
			p.removeAllHands();
		}

		dealer.removeAllHands();

		deck.shuffleDeck();

		for (int i = 0; i < playerList.size(); i++) {
			jbtBets[i].setEnabled(true);
		}
	}

	public void placeBet(int index, String bet, JButton button) {
		double newBet;
		
		try {
			newBet = Integer.parseInt(bet);
		}
		catch (NumberFormatException f) {
			dialogue.append("\nSorry, that is not a valid bet.");
			return;
		}
		
		if (newBet < 1)
			dialogue.append("\nSorry, the minimum bet is $1.00. Please enter a new bet.");
		else if (newBet > playerList.get(index).getWallet())
			dialogue.append("\nThe amount bet exceeds the amount in the wallet. Please enter a new bet.");
		else {
			playerList.get(index).setBet(newBet);
			currentBets[index].setText(String.format("Current bet: $%.2f", playerList.get(index).getBet()));
			button.setEnabled(false);
		}
	}
	
	public static void playGame(Game table) throws InterruptedException {
		dialogue.append("\nPlease place your bets!");

		if (playerList.size() == 1)
			while (jbtBets[0].isEnabled()) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		if (playerList.size() == 2)
			while (jbtBets[0].isEnabled() || jbtBets[1].isEnabled()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		if (playerList.size() == 3)
			while (jbtBets[0].isEnabled() || jbtBets[1].isEnabled() || jbtBets[2].isEnabled()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		dealer.deal(playerList, deck, dealer, table);
		table.repaint();
		
		dialogue.append(String.format("\n%s drew %s.", dealer.getName(), dealer.getHand().getCard(0)));
		for (Player p : playerList) {
			dialogue.append(String.format("\n%s was dealt %s and %s.", p.getName(), p.getHand().getCard(0), p.getHand().getCard(1)));
		}
		
		for (Player p : playerList) {
			playHand(p, p.getHand(), table);
		}
		
		dealer.playHand(deck, table, dialogue);
	}

	public static void playHand(Player player, Hand hand, Game table) {
		boolean stillPlayingHand = true;
		
		if (hand.getPoints() == 21) {
			dialogue.append("\nNatural 21! You win this hand!");
			return;
		}
		
		dialogue.append(String.format("\nIt is %s's turn! \nWhat's your move?", player.getName()));
		
		while (stillPlayingHand) {
			playerMove = "";
			table.repaint();
			jbtSplit.setEnabled(false);
			jbtDouble.setEnabled(false);
			
			if (player.canSplit(hand) && player.canDouble(hand)) {
				jbtSplit.setEnabled(true);
				jbtDouble.setEnabled(true);
			}
			else if (player.canSplit(hand))
				jbtSplit.setEnabled(true);
			else if (player.canDouble(hand))
				jbtDouble.setEnabled(true);
			
			while (playerMove.equals("")) {
				try {
					Thread.sleep(300);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (playerMove.equals("split"))
				hand = splitHand(player, hand, table);
			else if (playerMove.equals("double")) {
				player.setBet(player.getBet() * 2);
				Card draw = deck.getNextCard();
				dialogue.append(String.format("\n%s drew %s.", player.getName(), draw.toString()));
				hand.addCard(draw);
				stillPlayingHand = false;
			}
			else if (playerMove.equals("stay"))
				stillPlayingHand = false;
			else if (playerMove.equals("hit")) {
				Card draw = deck.getNextCard();
				dialogue.append(String.format("\n%s drew %s.", player.getName(), draw.toString()));
				hand.addCard(draw);
			}
			
			if (hand.getPoints() == 21) {
				dialogue.append("\nYou scored a 21!");
				stillPlayingHand = false;
			}
			else if (hand.getPoints() > 21) {
				dialogue.append("\nBUST!");
				stillPlayingHand = false;
			}
		}
	}
	
	public static Hand splitHand(Player player, Hand oldHand, Game table) {
		Hand newHand = new Hand(oldHand.getCard(1), deck.getNextCard());
		player.addHand(newHand);

		oldHand.removeCard(1);
		oldHand.addCard(deck.getNextCard());

		playHand(player, newHand, table);

		return oldHand;
	}
	
	public static void determineWinners() {
		for (Player p : playerList) {
			for (int i = p.getNumberOfHands() - 1; i >= 0; i--) {
				if (p.getHand(i).getPoints() > 21) {
					dialogue.append(String.format("\n%s loses hand%d!", p.getName(), i + 1));
					p.takeFromWallet(p.getBet());
				}
				else if (dealer.getHand().getPoints() > 21) {
					dialogue.append(String.format("\n%s wins hand%d!", p.getName(), i + 1));
					p.addToWallet(p.getBet());
				}
				else if (p.getHand(i).getPoints() > dealer.getHand().getPoints()) {
					dialogue.append(String.format("\n%s wins hand%d!", p.getName(), i + 1));
					if (p.getHand(i).getPoints() == 21 && p.getHand(i).sizeOfHand() == 2) {
						p.addToWallet(p.getBet() * 1.5);
					}
					else
						p.addToWallet(p.getBet());
				}
				else if (p.getHand(i).getPoints() == dealer.getHand().getPoints()) {
					dialogue.append(String.format("\n%s pushes hand%d!", p.getName(), i + 1));
				}
				else {
					dialogue.append(String.format("\n%s loses hand%d!", p.getName(), i + 1));
					p.takeFromWallet(p.getBet());
				}
			}
			playerWallets[playerList.indexOf(p)].setText(String.format("Wallet: $%.2f", p.getWallet()));
		}
	}

	public static void removeBrokePlayers() {
		for (int i = playerList.size() - 1; i >= 0; i--) {
			if (playerList.get(i).getWallet() == 0) {
				dialogue.append(String.format("\n" + playerList.get(i).getName() + " is broke and kicked out of the game!"));
				playerList.remove(playerList.indexOf(playerList.get(i)));
			}
		}
	}

	public static boolean playAgain() throws InterruptedException {
		Vector<Player> tempList = new Vector<Player>();
		boolean playAgain;
		String wait;
		
		for (Player p : playerList) {
			PlayAgain frame = new PlayAgain(p.getName());
			frame.setVisible(true);
			frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			
			wait = "";
			while (wait.equals("")) {
				Thread.sleep(300);
				wait = frame.wake();
			}
			
			playAgain = frame.getAnswer();
			frame.dispose();

			if (playAgain)
				tempList.add(p);
			else {
				dialogue.append(String.format("\n%s has been removed from the game!", p.getName()));
			}
		}

		playerList = tempList;

		if (playerList.isEmpty())
			playAgain = false;
		else {
			playAgain = true;
		}

		return playAgain;
	}
}