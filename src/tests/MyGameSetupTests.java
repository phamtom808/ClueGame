package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class MyGameSetupTests {
	
	private static Board board;
	
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
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
		assertTrue(players.contains(Miss Scarlett));
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
	


}
