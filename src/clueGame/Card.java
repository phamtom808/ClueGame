package clueGame;

public class Card {
	private CardType cardType;
	private String name;
	
	public Card(String n, CardType ctype) {
		this.name = n;
		this.cardType = ctype;
	}
	
	public boolean equals(Card other) {
		if(this.name == other.getName() && this.cardType == other.getCardType()) {
			return true;
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public CardType getCardType() {
		return this.cardType;
	}
	
}