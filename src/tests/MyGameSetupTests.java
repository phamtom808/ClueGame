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
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setBoardConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayers() {
		//arrayList or Set players
		Set<Player> players = board.getPlayers();
		Player arrayOfPlayers[] = new Player[6];
		arrayOfPlayers = players.toArray(arrayOfPlayers);
		//tests if the correct number of players are loaded
		assertEquals(NUM_PLAYERS, players.size());
		//test if set contains a human player
		assertTrue(players.contains());
		//test if set contains all computer players
		assertTrue(players.contains(Colonel Mustard));
		assertTrue(players.contains(Mrs. White));
		assertTrue(players.contains(Mr. Green));
		assertTrue(players.contains(Professor Plum));
		assertTrue(players.contains(Miss Peach));
		//test if first player is correct
		assertEquals("Miss Scarlet", arrayOfPlayers[0].name);
		assertEquals(Color.RED, arrayOfPlayers[0].getColor());
		assertTrue(arrayOfPlayers[0].getHumanPlayer());
	}
	
	@Test
	public void testDeck() {
		Set<Card> deck = board.getDeck();
		int numberOfWeapons = 0;
		int numberOfPlayers = 0;
		int numberOfRooms = 0;
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
	
	


}
