package blackjack.text;

public class Card {
	private String number;
	private String suit;
	
	public Card(String number, String suit) {
		this.number = number;
		this.suit = suit;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public int getFaceValue() {
		int faceValue;
		
		if (number.equals("Ace"))
			faceValue = 11;
		else if (number.equals("Jack") || number.equals("Queen") || number.equals("King"))
			faceValue = 10;
		else
			faceValue = Integer.parseInt(number);

		return faceValue;
	}
	
	public String toString() {
		return getNumber() + " " + getSuit();
	}
}
