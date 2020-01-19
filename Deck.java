
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
	private ArrayList <Card> cards;
	
	// Default constructor
	public Deck() {
		this.cards = null;
	}
	
	// Parameter constructor
	public Deck(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	// ToString
	public String toString() {
		return this.cards.toString()+"\n";
		}
	
	public void buildDeck() {		 
		for(int i=0; i<=3; i++) {
		    for(int j=1; j<=13; j++) {
		    	Card card = new Card(i,j);
		    	this.cards.add(card);
		    }
		}
	}
	public void printCardsInDeck() {
		Card card;
		int size = this.cards.size();
		for(int i=0;i<size;i++) {
			card = this.cards.get(i);
			System.out.println(card);
		}	
	}
	
	public void shuffle() {
		Collections.shuffle(Arrays.asList(cards));
		
		int size = this.cards.size();
		for (int i =0; i<size; i++) {
			int randomNumber1 = ((int) (Math.random()*(52)));
			int randomNumber2 = ((int) (Math.random()*(52)));
			Card temporaryCard1 = cards.get(randomNumber1);
			Card temporaryCard2 = cards.get(randomNumber2);
			cards.set(randomNumber1, temporaryCard2);
			cards.set(randomNumber2, temporaryCard1);
		}
	}
	
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	
	
}
