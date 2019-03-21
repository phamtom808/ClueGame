package tests;

import static org.junit.Assert.*;

import java.awt.Color;
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
	public static final int NUM_ROOMS = 9;
	public static final int TOTAL_NUM_CARDS = 21;
	public static final int NUM_HUMAN_PLAYERS = 1;
	public static final int NUM_COMP_PLAYERS = 5;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayerType() {
		Set<Player> players = board.getPlayers();
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
		Set<Player> players = board.getPlayers();
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
			case "Mr.Green":
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
		Set<Player> players = board.getPlayers();
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
			case "Mr.Green":
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
			else
			{
				numberOfRooms++;
			}
		}
		assertEquals(NUM_WEAPONS, numberOfWeapons);
		assertEquals(NUM_PLAYERS, numberOfPlayers);
		assertEquals(NUM_ROOMS, numberOfRooms);
		//check the deck to see it contains specific cards
		for (Card y: deck) {
			if((y.getName() == "Pool") || (y.getName() == "Knife") || (y.getName() == "Professor Plum")) {
				numberOfCorrectNames++;
			}
		}
		assertEquals(3, numberOfCorrectNames);
	}
	
	@Test
	public void testNumberOfPlayerCards() {
		Set<Player> players = board.getPlayers();
		//check if the correct number of player cards dealt
		assertEquals(NUM_PLAYERS, players.size());
	}
	
	@Test
	public void testNumberOfWeaponCards() {
	//check if the number of weapons dealt are correct
		Set<Player> players = board.getPlayers();
		int numberOfWeapons = 0;
		for(Player x: players) {
			for(Card y: x.getHand()) {
				if(y.getCardType() == CardType.WEAPON) {
					numberOfWeapons++;
				}
			}
		}
		assertEquals(NUM_WEAPONS, numberOfWeapons);
	}
	
	@Test
	public void testNumberOfRoomCards() {
	//check if the number of rooms dealt are correct
		Set<Player> players = board.getPlayers();
		int numberOfRooms = 0;
		for(Player x: players) {
			for(Card y: x.getHand()) {
				if(y.getCardType() == CardType.ROOM) {
					numberOfRooms++;
				}
			}
		}
		assertEquals(NUM_ROOMS, numberOfRooms);
	}
	
	@Test
	public void testNumberOfCardsDealt() {
		
	}

}
