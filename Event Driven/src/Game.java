import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame{
	private static Scanner scan = new Scanner(System.in);
	private static Vector<Player> playerList;
	private static Deck deck = new Deck();
	private static Dealer dealer = new Dealer();
	
	private JButton jbt1 = new JButton("Hit");
	private JButton jbt2 = new JButton("Stay");
	private JButton jbt3 = new JButton("Double");
	private JButton jbt4 = new JButton("Split");

	public Game() {
		setLayout(null);
		GetBlackjackTable blkjck = new GetBlackjackTable();
		blkjck.setBounds(0, 0, 900, 600);

		ButtonListener hitBtn = new ButtonListener();
		
		jbt1.setBounds(245, 560, 80, 30);
		jbt1.addActionListener(hitBtn);

		jbt2.setBounds(355, 560, 80, 30);
		jbt2.addActionListener(hitBtn);

		jbt3.setBounds(465, 560, 80, 30);
		jbt3.addActionListener(hitBtn);

		jbt4.setBounds(575, 560, 80, 30);
		jbt4.addActionListener(hitBtn);

		add(jbt1);
		add(jbt2);
		add(jbt3);
		add(jbt4);
		add(blkjck);
	}
	
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbt1)
				;
			if (e.getSource() == jbt2)
				;
			if (e.getSource() == jbt3)
				;
			if (e.getSource() == jbt4)
				;
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
		
		greeter.dispose();
		Game table = new Game();
		table.setSize(906, 628);
		table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		table.setLocationRelativeTo(null);
		table.setTitle("Blackjack by Team Dragon");
		table.setResizable(false);
		table.setVisible(true);
		
		do {
			preGameSetup();
			playGame();
			determineWinners();
			removeBrokePlayers();
		} while (playAgain());

		System.out.println("There are no more players in the game. \nThe Game will now exit, thanks for playing!");
	}

public static void preGameSetup() {
		for (Player p : playerList) {
			p.removeAllHands();
		}

		dealer.removeAllHands();

		deck.shuffleDeck();
	}

	public static void playGame() {
		for (Player p : playerList) {
			int bet;
			System.out.printf("%s, you have $%.2f in your wallet.\n",
					p.getName(), p.getWallet());
			System.out.print("How much do you want to bet? ");

			do {
				try {
					bet = scan.nextInt();
				} catch (InputMismatchException e) {
					bet = -1;
				}

				scan.nextLine();

				if (bet < 0)
					System.out
							.print("Invalid input. Please choose a new bet: ");
				else if (bet < 1)
					System.out
							.print("Sorry, the minimum bet is $1.00. Please choose a new bet: ");
				else if (bet > p.getWallet())
					System.out
							.print("The amount bet exceeds the amount in the wallet. Please choose a new bet: ");
				else
					p.setBet(bet);

			} while (bet < 1 || bet > p.getWallet());
		}

		dealer.deal(playerList, deck, dealer);

		for (Player p : playerList) {
			System.out.printf("%s's card is %s.\n", dealer.getName(), dealer
					.getHand().getCard(0));
			playHand(p, p.getHand());
		}

		dealer.playHand(deck);
	}

	public static void determineWinners() {
		System.out.printf("%s has %d points.\n", dealer.getName(), dealer
				.getHand().getPoints());

		for (Player p : playerList) {
			for (int i = p.getNumberOfHands() - 1; i >= 0; i--) {
				System.out.printf("%s has %d points for hand%d.\n",
						p.getName(), p.getHand(i).getPoints(), i + 1);
				if (p.getHand(i).getPoints() > 21) {
					System.out
							.printf("%s loses hand%d! \n", p.getName(), i + 1);
					p.takeFromWallet(p.getBet());
				} else if (dealer.getHand().getPoints() > 21) {
					System.out.printf("%s wins hand%d! \n", p.getName(), i + 1);
					p.addToWallet(p.getBet());
				} else if (p.getHand(i).getPoints() > dealer.getHand()
						.getPoints()) {
					System.out.printf("%s wins hand%d! \n", p.getName(), i + 1);
					if (p.getHand(i).getPoints() == 21
							&& p.getHand(i).sizeOfHand() == 2) {
						p.addToWallet(p.getBet() * 1.5);
					} else
						p.addToWallet(p.getBet());
				} else if (p.getHand(i).getPoints() == dealer.getHand()
						.getPoints()) {
					System.out.printf("%s pushes hand%d! \n", p.getName(),
							i + 1);
				} else {
					System.out
							.printf("%s loses hand%d! \n", p.getName(), i + 1);
					p.takeFromWallet(p.getBet());
				}
			}
		}
	}

	public static void removeBrokePlayers() {
		for (int i = playerList.size() - 1; i >= 0; i--) {
			if (playerList.get(i).getWallet() == 0) {
				System.out.println(playerList.get(i).getName()
						+ " is broke and kicked out of the game!");
				playerList.remove(playerList.indexOf(playerList.get(i)));
			}
		}
	}

	public static boolean playAgain() {
		Vector<Player> tempList = new Vector<Player>();
		boolean playAgain;
		String temp = "";

		for (Player p : playerList) {
			System.out.printf("%s, you have $%.2f.\n", p.getName(),
					p.getWallet());
			System.out.print("Would you like to play again (yes or no)? ");

			do {
				temp = scan.next();

				if (temp.equalsIgnoreCase("yes"))
					tempList.add(p);
				else if (temp.equalsIgnoreCase("no"))
					System.out.println(p.getName()
							+ " was removed from the game.");
				else {
					temp = "-1";
					System.out
							.print("Invalid input. Would you like to play again (yes or no)? ");
				}
			} while (!temp.equalsIgnoreCase("yes")
					&& !temp.equalsIgnoreCase("no"));
		}

		playerList = tempList;

		if (playerList.isEmpty())
			playAgain = false;
		else
			playAgain = true;

		return playAgain;
	}

	public static void playHand(Player player, Hand hand) {
		boolean bust = false, invalidMove;
		String playerAction;

		while (!bust) {
			invalidMove = true;
			System.out.println(player.getName() + " here is your hand: ");
			hand.printHand();

			if (hand.getPoints() == 21) {
				System.out.println("Natural 21!");
				return;
			} else if (player.canSplit(hand) && player.canDouble(hand))
				System.out
						.print("What would you like to do (Double, Split, Stay, Hit)? ");
			else if (player.canSplit(hand))
				System.out
						.print("What would you like to do (Split, Stay, Hit)? ");
			else if (player.canDouble(hand))
				System.out
						.print("What would you like to do (Double, Stay, Hit)? ");
			else
				System.out.print("What would you like to do (Stay, Hit)?  ");

			playerAction = scan.next();

			while (invalidMove) {
				if (playerAction.equalsIgnoreCase("split")) {
					if (player.canSplit(hand)) {
						hand = splitHand(player, hand);
						invalidMove = false;
					} else
						System.out
								.println("Sorry, this hand can not be split.");
				} else if (playerAction.equalsIgnoreCase("double")) {
					if (player.canDouble(hand)) {
						player.setBet(player.getBet() * 2.0);
						hand.addCard(deck.getNextCard());
						System.out.println(player.getName()
								+ " here is your hand: ");
						hand.printHand();

						invalidMove = false;
						bust = true;
					} else
						System.out
								.println("Sorry, this hand can not be doubled.");
				} else if (playerAction.equalsIgnoreCase("stay")) {
					invalidMove = false;
					bust = true;
				} else if (playerAction.equalsIgnoreCase("hit")) {
					Card draw;
					hand.addCard(draw = deck.getNextCard());
					System.out.println("You drew " + draw.toString());
					invalidMove = false;
				} else {
					System.out.print("Invalid choice, choose again:  ");
					playerAction = Game.scan.next();
				}
			}

			if (hand.getPoints() > 21) {
				bust = true;
				System.out.println("BUST!");
			} else if (hand.getPoints() == 21) {
				hand.printHand();
				System.out.println("21!");
				return;
			}
		}
	}

	public static Hand splitHand(Player player, Hand oldHand) {
		Hand newHand = new Hand(oldHand.getCard(1), deck.getNextCard());
		player.addHand(newHand);

		oldHand.removeCard(1);
		oldHand.addCard(deck.getNextCard());

		playHand(player, newHand);

		return oldHand;
	}
}


class GetBlackjackTable extends JPanel {

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		ImageIcon image = new ImageIcon(GetBlackjackTable.class.getResource("DragonBoard.jpg"));
		Image background = image.getImage();
		g.drawImage(background, 0, 0, 900, 600, this);
	}
}