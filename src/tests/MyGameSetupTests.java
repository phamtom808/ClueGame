package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class MyGameSetupTests {
	
	private static Board board;
	
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_ROOMS = 10;
	public static final int TOTAL_NUM_CARDS = 22;
	public static final int NUM_HUMAN_PLAYERS = 1;
	public static final int NUM_COMP_PLAYERS = 5;
	public static final int MAX_HAND_SIZE = 4;
	public static final int MIN_HAND_SIZE = 3;
	
	@BeforeClass
	public static void setUp() {
		//creates an instance of the board and initializes configuration files and the board
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayerType() {
		//Checks if the number of human players and computer players are correct
		ArrayList<Player> players = board.getPlayers();
		int humanPlayers = 0;
		int computerPlayers = 0;
		for(Player x: players) {
			if(x.getIsHumanPlayer() == true) {
				humanPlayers++;
			}
			else if(x.getIsHumanPlayer() == false) {
				computerPlayers++;
			}
		}
		assertEquals(NUM_HUMAN_PLAYERS, humanPlayers);
		assertEquals(NUM_COMP_PLAYERS, computerPlayers);
	}
	
	@Test
	public void testPlayerColor() {
		//Creates an array of players and checks if their respective colors are correct
		ArrayList<Player> players = board.getPlayers();
		for(Player x: players) {
			String playerName = x.getName();
			switch(playerName) {
			case "Miss Scarlett":
				assertEquals(Color.RED, x.getColor());
				continue;
			case "Colonel Mustard":
				assertEquals(Color.YELLOW, x.getColor());
				continue;
			case "Mrs. White":
				assertEquals(Color.GRAY, x.getColor());
				continue;
			case "Mr. Green":
				assertEquals(Color.GREEN, x.getColor());
				continue;
			case "Professor Plum":
				assertEquals(Color.MAGENTA, x.getColor());
				continue;
			default:
				assertEquals(Color.PINK, x.getColor());
			}
		}
	}
	
	@Test
	public void testStartingLocation() {
		//Creates an array of players and checks if their starting positions are correct
		ArrayList<Player> players = board.getPlayers();
		for(Player x: players) {
			String playerName = x.getName();
			switch(playerName) {
			case "Miss Scarlett":
				assertEquals(0, x.getRow());
				assertEquals(5, x.getColumn());
				continue;
			case "Colonel Mustard":
				assertEquals(19, x.getRow());
				assertEquals(5, x.getColumn());
				continue;
			case "Mrs. White":
				assertEquals(19, x.getRow());
				assertEquals(12, x.getColumn());
				continue;
			case "Mr. Green":
				assertEquals(15, x.getRow());
				assertEquals(20, x.getColumn());
				continue;
			case "Professor Plum":
				assertEquals(8, x.getRow());
				assertEquals(19, x.getColumn());
				continue;
			default:
				assertEquals(0, x.getRow());
				assertEquals(11, x.getColumn());
				continue;
			}
		}
	}
	
	@Test
	public void testDeck() {
		Set<Card> deck = board.getDeck();
		int numberOfWeapons = 0;
		int numberOfPlayers = 0;
		int numberOfRooms = 0;
		int numberOfCorrectNames = 0;
		//check the deck contains the correct number of cards
		assertEquals(TOTAL_NUM_CARDS, deck.size());
		//check the deck contains the correct number of each card
		for (Card x: deck) {
			if(x.getCardType() == CardType.WEAPON) {
				numberOfWeapons++;
			}
			else if(x.getCardType() == CardType.PLAYER) {
				numberOfPlayers++;
			}
			else if(x.getCardType() == CardType.ROOM) {
				numberOfRooms++;
			}
		}
		assertEquals(NUM_WEAPONS, numberOfWeapons);
		assertEquals(NUM_PLAYERS, numberOfPlayers);
		assertEquals(NUM_ROOMS, numberOfRooms);
		//check the deck to see it contains specific cards
		for (Card y: deck) {
			if((y.getName().equals("Pool")) || (y.getName().equals("Knife")) || (y.getName().equals("Professor Plum"))) {
				numberOfCorrectNames++;
			}
		}
		assertEquals(3, numberOfCorrectNames);
	}
	
	@Test
	public void testNumberOfPlayerCards() {
		ArrayList<Player> players = board.getPlayers();
		//check if the correct number of player cards dealt
		int playerCardsDealt = 0;
		for(Player p: players) {
			Set<Card> hand = p.getHand();
			for(Card c: hand){
				if(c.getCardType() == CardType.PLAYER) {
					playerCardsDealt++;
				}
			}
		}
		playerCardsDealt++; //account for undealt card from solution
		assertEquals(NUM_PLAYERS, playerCardsDealt);
	}
	
	@Test
	public void testNumberOfWeaponCards() {
	//check if the number of weapons dealt are correct
		ArrayList<Player> players = board.getPlayers();
		int numberOfWeapons = 0;
		for(Player x: players) {
			for(Card y: x.getHand()) {
				if(y.getCardType() == CardType.WEAPON) {
					numberOfWeapons++;
				}
			}
		}
		numberOfWeapons++; //accounts for undealt solution card
		assertEquals(NUM_WEAPONS, numberOfWeapons);
	}
	
	@Test
	public void testNumberOfRoomCards() {
	//check if the number of rooms dealt are correct
		ArrayList<Player> players = board.getPlayers();
		int numberOfRooms = 0;
		for(Player x: players) {
			for(Card y: x.getHand()) {
				if(y.getCardType() == CardType.ROOM) {
					numberOfRooms++;
				}
			}
		}
		numberOfRooms++; //account for solution card not dealt to any player
		assertEquals(NUM_ROOMS, numberOfRooms);
	}
	
	//ensure that a) player hands are all between min and max hand sizes and b) all of the cards were dealt and c) no cards were dealt twice
	@Test
	public void testNumberOfCardsDealt() {
		int totalDeckSize = 0;
		Set<Card> deck = board.getDeck();
		Set<Card> testDeck = new HashSet<Card>();
		for(Player p: board.getPlayers()) {
			assertTrue(p.getHand().size() <= MAX_HAND_SIZE && p.getHand().size() >= MIN_HAND_SIZE);
			totalDeckSize += p.getHand().size();
			testDeck.addAll(p.getHand());
		}
		totalDeckSize += 3; //accounts for 3 cards NOT in player hand
		testDeck.add(board.getWeaponCard());
		testDeck.add(board.getRoomCard());
		testDeck.add(board.getPlayerCard());
		assertEquals(totalDeckSize, deck.size());
		assertEquals(testDeck,deck);
	}

}
