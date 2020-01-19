
import java.util.ArrayList;
import javax.swing.*;

public class Bat {
	static int count = 0;
	static int index = 0;
	static int numberOfPlayers = 0;
	static Deck deckOfCards = null;
	static Deck discardedDeck = null;
	static Card removedCard = null;
	static Player currentPerson;

	// Instance variables
	int[] deck;
	static Player[] allPlayers;
	Player currentPlayer;

	// Default constructor
	public Bat() {
		this.deck = new int[0];
		this.allPlayers = new Player[0];
		this.currentPlayer = null;
	}

	// Parameter constructor
	public Bat(int[] d, Player[] p, Player c) {
		this.deck = d;
		this.allPlayers = p;
		this.currentPlayer = c;
	}

	// ToString
	public String toString() {
		String showDeck = "";
		for (int x : deck) {
			showDeck += x + "  ";
		}

		String showAllPlayers = "";
		for (Player x : allPlayers) {
			showAllPlayers += x + "  ";
		}

		return ("Deck: " + showDeck + "\nAll Players: " + showAllPlayers + "Current Player: " + currentPlayer);
	}

	public static void main(String[] args) {
		// Asking user for the number of players and saving that as a variable
		Integer[] choosePlayers = new Integer[] { 2, 3 };
		JFrame frame = new JFrame("Input Dialog Example 3");
		Integer playersNumber = (Integer) JOptionPane.showInputDialog(frame, "Welcome to BAT! ",
				"How many players will play?", JOptionPane.QUESTION_MESSAGE, null, choosePlayers, choosePlayers[0]);
		try {
			numberOfPlayers = playersNumber.intValue(); // Creating an int variable for number of players

			// Message with the rules of the game
			JOptionPane.showMessageDialog(null, "Here are the rules of the game:\n\n"
					+ "An ace adds either 1 or 11 to the existing total.\nThe player who puts it down decides. Choose '1' for 1 and '11' for 11\n"
					+ "A nine of any suit reverses the direction of play without changing the total.\n"
					+ "A four of any suit is a pass; a player's turn is skipped without changing the total.\n"
					+ "A Jack of any suit subtracts 10 from the existing total.\n"
					+ "Any Queen will take the existing total immediately to 99 or if it is already 99, it remains at 99.\n"
					+ "Kings add 20 points to the existing total.\n"
					+ "All other cards add their face value to the total.\n"
					+ "To choose a card, press '1' for the first card, '2' for the second card, and '3' for the third card.\n\n"
					+ "If a player is unable to play without making the count go above 99, they receive a 'B' and a new round begins.\n"
					+ "The second time a player is unable to play without making the count go above 99, they will receive an 'A'.\n"
					+ "The third time a player is unable to play without making the count go above 99, they will receive a 'T' and will be eliminated from the game.\n"
					+ "The last player left is the winner.\nGood luck!");

			// Creating and shuffling the deck
			deckOfCards = new Deck(new ArrayList<Card>());
			discardedDeck = new Deck(new ArrayList<Card>());
			deckOfCards.buildDeck();
			deckOfCards.shuffle();

			allPlayers = new Player[numberOfPlayers];
			for (int i = 0; i < numberOfPlayers; i++) {
				Player player = new Player(i + 1, new Card[3], new String[3]);
				allPlayers[i] = player;
			}

			// Creating a new instance of player
			Player player = new Player();

			// Calling the deal method that will return an array of completed players with
			// cards
			deal();

			// Calling the method that starts the game
			playCard(allPlayers, player);

			// If the player failed to choose the number of players earlier on and closed
			// the window, the game
			// ends (assumed that the player wanted to quit
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "You exited the game");
			System.exit(0);
		}
	}

	public static void deal() {
		// Before dealing new cards, any previous cards that players still have in their
		// hand is removed
		for (int i = 0; i < allPlayers.length; i++) {
			allPlayers[i].hand = null;
		}

		// Dealing new cards
		for (int i = 0; i < allPlayers.length; i++) {
			Card[] cards = new Card[3];
			allPlayers[i].hand = cards;
			for (int j = 0; j < 3; j++) {
				allPlayers[i].hand[j] = deckOfCards.getCards().get(0);

				// Removing the card from the deck immediately after being dealt
				deckOfCards.getCards().remove(0);
			}
		}
	}

	public static void playCard(Player[] allPlayers, Player player) {

		// The game starts, and as long as the count is under 100, the round continues
		while (count <= 99) {
			// for loop allows players to take turns playing their cards
			for (int i = 1; i <= numberOfPlayers; i++) {
				currentPerson = allPlayers[i - 1];
				try {
					index = Integer.parseInt(JOptionPane
							.showInputDialog("It is " + allPlayers[i - 1].name + "'s turn.\nThe count is " + count + "."
									+ "\nHere are your cards, please pick one:\n1: " + allPlayers[i - 1].hand[0]
									+ "\n2: " + allPlayers[i - 1].hand[1] + "\n3: " + allPlayers[i - 1].hand[2]));

					int value = allPlayers[i - 1].hand[index - 1].rank;

					// The player picks up a new card and the card gets removed from the deck
					pickUp(i, allPlayers);

					// If a special card is played (other than a four), call the checkMove() method
					// that checks for the specific
					// instruction
					if (value == 9 || value == 11 || value == 12 || value == 13) {
						checkMove(i, value, allPlayers);

						// If an ace is played, the user must choose between a value of 1 or 11
					} else if (value == 1) {
						try {
							int newValue = Integer.parseInt(JOptionPane
									.showInputDialog("You chose an ace. Would you like to play a 1 or a 11?"));

							// If the user chooses a number other than 1 or 11, they are asked to choose
							// again
							while (newValue != 1 && newValue != 11) {
								newValue = Integer.parseInt(JOptionPane.showInputDialog("Incorrect value. Pick again"));
							}
							// Add either 1 or 11 to the current count depending on what the player chose
							count += newValue;

						} catch (Exception e) { // If the user did not input an int
							JOptionPane.showMessageDialog(null, "You exited the game.");
						}
					}

					// If a four is played, the for loop will skip the next player's turn.
					else if (value == 4) {
						JOptionPane.showMessageDialog(null, "A four has been played. Skip the next player's turn.");

						// If there are two players, the same player plays again
						if (numberOfPlayers == 2) {
							i--;
							// break;

							// If there are three players, the next player will be skipped
						} else {
							if (i == 1 || i == 2) {
								i++;
							} else {
								i = 0;
							}
							// break;
						}
					}

					// If a regular card is played, add its face value
					else {
						count += value;
					}
				}
				// If the player failed to select a card, the game exits
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "You exited the game.");
					System.exit(0);
				}

				// Once a player makes the count go past 99, roundOver assigns them a letter
				if (count > 99) {
					roundOver(i, allPlayers);

					// Count is reset to 0
					count = 0;
				}
			}
		}
	}

	public static void checkMove(int i, int value, Player[] allPlayers) {
		// Checking all the special cards (with the exception of '4', which was executed
		// earlier)
		switch (value) {
		// If a nine is played, switches the order of play
		case 9:
			// For two players, the reverse card does not change anything, so nothing
			// happens
			JOptionPane.showMessageDialog(null, "A nine has been played. The order has been reversed.");
			if (numberOfPlayers == 2) {
				break;
			}
			// For three players, depending on whose turn it is, the array is rearranged to
			// reverse the order of play
			else {
				// If player 1 played the card
				if (i == 1) {
					Player temporary = allPlayers[allPlayers.length - 2];
					allPlayers[allPlayers.length - 2] = allPlayers[allPlayers.length - 1];
					allPlayers[allPlayers.length - 1] = temporary;

					// If player 2 played the card
				} else if (i == 2) {
					Player temporary = allPlayers[0];
					allPlayers[0] = allPlayers[allPlayers.length - 1];
					allPlayers[allPlayers.length - 1] = temporary;
				}

				// If player 3 played the card
				else {
					Player temporary = allPlayers[0];
					allPlayers[0] = allPlayers[allPlayers.length - 2];
					allPlayers[allPlayers.length - 2] = temporary;
				}
				break;
			}

			// If a Jack is played, subtract 10 from the the total count only if the count
			// will still be zero or higher
		case 11:
			if (count >= 10) {
				count -= 10;
			} else {
				count = 0;
			}
			break;

		// If a Queen is played, immediately bring the count to 99
		case 12:
			count = 99;
			break;

		// If a King is played, add 20 to the count
		case 13:
			count += 20;
			break;
		}
	}

	public static void pickUp(int i, Player[] allPlayers) {
		// The player picks up a new card, and that card is removed from the deck
		allPlayers[i - 1].hand[index - 1] = deckOfCards.getCards().get(0);
		removedCard = deckOfCards.getCards().remove(0);
		deckOfCards.getCards().remove(0);
		discardedDeck.getCards().add(removedCard);
	}

	public static void roundOver(int i, Player[] allPlayers) {
		// At the end of the round, if the player who passed 99 currently does not have
		// any letters, give them a 'B'
		if (currentPerson.status[0] == null) {
			currentPerson.status[0] = "B";
			JOptionPane.showMessageDialog(null, currentPerson.name + " has passed 99. You have a "
					+ currentPerson.status[0] + "\nA new round will start.");

			// If they currently have a 'B', give them an 'A'
		} else if (currentPerson.status[1] == null) {
			currentPerson.status[1] = "A";
			JOptionPane.showMessageDialog(null, currentPerson.name + " has passed 99. You have a "
					+ currentPerson.status[0] + currentPerson.status[1] + "\nA new round will start.");
		}

		else { // If they currently have a 'B' and an 'A', call isWinner (the endgame method)
			isWinner(i, allPlayers);
		}
		// A new deck of cards is built and shuffled
		deckOfCards.buildDeck();
		deckOfCards.shuffle();
		deal();
	}

	public static void isWinner(int i, Player[] allPlayers) {
		// Add a 'T' to this player's status
		currentPerson.status[2] = "T";

		// If there were still three players in the game, a winner has not yet emerged
		if (numberOfPlayers == 3) {
			JOptionPane.showMessageDialog(null,
					currentPerson.name + " has passed 99. You have a " + currentPerson.status[0]
							+ currentPerson.status[1] + currentPerson.status[2] + ".\nPlayer " + i
							+ " is out.\nA new round will start.");
			Player[] tempPlayers = new Player[2];

			// Copy contents of players into tempPlayer and assign tempPlay to players
			if (i == 1) {
				tempPlayers[0] = allPlayers[i];
				tempPlayers[1] = allPlayers[i + 1];
			} else if (i == 2) {
				tempPlayers[0] = allPlayers[i - 1];
				tempPlayers[1] = allPlayers[i + 1];
			} else if (i == 3) {
				tempPlayers[0] = allPlayers[i - 3];
				tempPlayers[1] = allPlayers[i - 2];
			}
			allPlayers = tempPlayers;

			// Remove the eliminated player
			numberOfPlayers--;

		} else {
			JOptionPane.showMessageDialog(null,
					currentPerson.name + " has passed 99. You have a " + currentPerson.status[0]
							+ currentPerson.status[1] + currentPerson.status[2] + ".\nPlayer " + i
							+ " is out. Game over!");
			System.exit(0);
		}
	}

	/*
	 * Test cases: - Checked that all classes and their respective constructors
	 * generate instances as expected - Checked that shuffleDeck() shuffles the deck
	 * properly using swapCards() and that it deals out random cards to players -
	 * Checked that players always get a new, random card while their two other
	 * cards stay the same - Checked that the toString() method for Deck, Card and
	 * Player works as expected - Checked that the Card chosen adds the proper count
	 * - Checked that special cards play their role - Ace: checked to make sure that
	 * the user can only play a 1 or 11 - Checked that the the program doesn't crash
	 * when there is an unexpected input (int that is out of range, text instead of
	 * number) - Made sure there are no duplicates of cards (i.e. the user picks up
	 * a card from the stock pile and the card does not stay in the stock pile)
	 * -Made sure a player gets the proper letters based on the previous rounds, and
	 * when a player gets BAT, they are eliminated - New cards are dealt out in a
	 * new round from a shuffled deck
	 */
}

