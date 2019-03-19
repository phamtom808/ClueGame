package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import clueGame.Board;

public class MyGameSetupTests {
	
	private static Board board;
	
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setNewConfig("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
	}
	
	
	


}
