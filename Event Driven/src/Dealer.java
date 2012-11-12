import java.util.Vector;

import javax.swing.JTextArea;

public class Dealer extends Player {

	public Dealer() {
		super("House");
	}

	public void playHand(Deck deck, Game table, JTextArea dialogue) throws InterruptedException {
		boolean bust = false;

		while (!bust) {
			while (!bust) {
				Thread.sleep(500);
				if (this.getHand().getPoints() > 21) {
					dialogue.append("\nHOUSE BUST!");
					bust = true;
				} else if (((this.getHand().getPoints() >= 17) && !this.getHand().hasAce())
						|| ((this.getHand().getPoints() > 17) && this.getHand().hasAce()))
					bust = true;
				else if ((this.getHand().getRawPoints() > this.getHand().getPoints()) && (this.getHand().getPoints() >= 17))
					bust = true;
				else {
					Card draw;
					this.getHand().addCard(draw = deck.getNextCard());
					dialogue.append("\nThe house drew " + draw.toString());
				}
			}
		}
	}

	public void deal(Vector<Player> players, Deck deck, Dealer deal, Game table) {
		for (Player p : players) {
			p.addHand(new Hand(deck.getNextCard(), deck.getNextCard()));
		}

		deal.addHand(new Hand(deck.getNextCard(), deck.getNextCard()));
	}
}