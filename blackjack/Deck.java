package blackjack;

import Card;
import java.util.ArrayList;

public class Deck{

	/*
	 * Instantiates the ArrayList of Cards. Calls fillDeck.
	 */
	private static ArrayList<Card> deck = new ArrayList<Card>();
	public Deck() 
	{
		fillDeck();
	}
	/*
	 * Fills the ArrayList "deck" with Cards. It starts with four (4) aces, then goes to four (4) deuces,
	 * and then so on up to Kings (13). It adds all the cards, one at a time, to the deck.
	 */
	public void fillDeck()
	{
		for (int i = 0; i < 13; i++)
			{
			for (int j = 0; j < 4; j++)
			{
				Card newCard = new Card(i, j);
				deck.add(newCard);
			}
		}
	}
	
	/*
	 * Does pretty much the same thing as fillDeck(), but it multiplies the number of decks you have.
	 * For example, if you call fillDeck(2), you'll come up with 2 decks consisting of 104 cards.
	 * They are added to the deck.
	 */
	public void fillDeck(int times)
	{
		for (int i = 0; i < times; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				for (int k = 0; k < 4; k++)
				{
					Card newCard = new Card(j, k);
					deck.add(newCard);
				}
			}
		}
	}
	
	/*
	 * Returns the size of the deck.
	 */
	public int getSize()
	{
		return deck.size();
	}
	
	/*
	 * Not sure how to do this part??
	 */
	public Card getCard(int n, int s)
	{
		Card newCard = new Card();
		return newCard;
	}
	
	/*
	 * If the deck is empty, it returns true. If not, it returns false.
	 */
	public boolean isEmpty()
	{
		if (getSize() == 0) return true;
		else return false;
	}
}
