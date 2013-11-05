package blackjack.core;

/*
 * @Author Jordan Greenfield, Tyler Kramer,
 * 		   Brandon Turner, Kyle Nyland 
 */

import java.util.Vector;

public class Dealer extends Player {

	private Utils print;
	
	public Dealer(Utils utils) {
		super("House");
		this.print = utils;
	}

	public void playHand(Deck deck) {
		boolean bust = false;

		while (!bust) {
			print.print(this.getName() + "'s hand.");
			
			if (this.getHand().getPoints() > 21) {
				print.print("HOUSE BUST!");
				//dialogue.append("\nHOUSE BUST!");
				bust = true;
			} 
			else if (((this.getHand().getPoints() >= 17) && !this.getHand().hasAce()) || ((this.getHand().getPoints() > 17) && this.getHand().hasAce())) {
				print.print("The house stays.");
				//dialogue.append("\nThe house stays.");
				bust = true;
			}
			else if ((this.getHand().getRawPoints() > this.getHand().getPoints()) && (this.getHand().getPoints() >= 17)) {
				print.print("The house stays.");
				//dialogue.append("\nThe house stays.");
				bust = true;
			}
			else {
				Card draw;
				this.getHand().addCard(draw = deck.getNextCard());
				print.print("The house drew " + draw.toString());
				//dialogue.append("\nThe house drew " + draw.toString());
				print.redraw();
				//table.redrawDealerCards(table);
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