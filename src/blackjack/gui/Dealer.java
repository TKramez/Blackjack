package blackjack.gui;
/*
 * @Author Jordan Greenfield, Tyler Kramer,
 * 		   Brandon Turner, Kyle Nyland 
 */

import java.util.Vector;

import javax.swing.JTextArea;

public class Dealer extends Player {

	public Dealer() {
		super("House");
	}

	public void playHand(Deck deck, JTextArea dialogue, Game table) throws InterruptedException {
		boolean bust = false;

		while (!bust) {
			if (this.getHand().getPoints() > 21) {
				dialogue.append("\nHOUSE BUST!");
				bust = true;
			} 
			else if (((this.getHand().getPoints() >= 17) && !this.getHand().hasAce()) || ((this.getHand().getPoints() > 17) && this.getHand().hasAce())) {
				dialogue.append("\nThe house stays.");
				bust = true;
			}
			else if ((this.getHand().getRawPoints() > this.getHand().getPoints()) && (this.getHand().getPoints() >= 17)) {
				dialogue.append("\nThe house stays.");
				bust = true;
			}
			else {
				Card draw;
				this.getHand().addCard(draw = deck.getNextCard());
				dialogue.append("\nThe house drew " + draw.toString());
				table.redrawDealerCards(table);
			}
		}
	}

	public void deal(Vector<Player> players, Deck deck, Dealer deal) {
		for (Player p : players) {
			p.addHand(new Hand(deck.getNextCard(), deck.getNextCard()));
		}

		deal.addHand(new Hand(deck.getNextCard(), deck.getNextCard()));
	}
}