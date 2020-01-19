
public class Card {
	int suit; 
	int rank;
	
	// Default constructor
	public Card () {
	this.suit = -1;  
	this.rank = 0; // If a rank and suit are not given, the suit and rank do not correspond to anything
	}
	
	// Parameter constructor
	public Card (int suit, int rank) {
	this.suit = suit; 
	this.rank = rank;
	}
	
	// ToString 
	public String toString() {
		String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
		String[] ranks = { "narf", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
		return (ranks[this.rank] + " of " + suits[this.suit]);
	}
	
}