
public class Player {
	// Instance variables
	String name;
	Card[] hand;
	String[]status;
	
	// Default constructor
	public Player() {
		this.name = "";
		this.hand = new Card[0];
		this.status = new String[0];
	}
	
	//Parameter constructor
	public Player(int n, Card[]h, String[]s) {
		this.name = "Player "+ n;
		this.hand = h;
		this.status = s;
	}
	
	// ToString
	public String toString() {
		String showHand = "";
		for(Card x : hand) {
			showHand += x + "  ";
		}
		
		String showStatus = "";
		for(String x : status) {
			showStatus += x + "  ";
		}
		return(this.name + "Hand: "+ showHand + "\nStatus: "+ showStatus);
	}
	
}

