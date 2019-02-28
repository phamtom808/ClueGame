package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class myInitTests {
	
	public static final int LEGEND_SIZE = 11;
	public static final int NUMBER_COLUMNS = 24;
	public static final int NUMBER_ROWS = 20;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.initialize();
	}
	
	@Test
	public void testLegend() {
		Map<String, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		
		assertEquals("Conservatory", legend.get("C"));
		assertEquals("Kitchen", legend.get("K"));
		assertEquals("Bedroom", legend.get("B"));
		assertEquals("Theater", legend.get("T"));
		assertEquals("Living Room", legend.get("L"));
		assertEquals("Study", legend.get("S"));
		assertEquals("Pool", legend.get("P"));
		assertEquals("Garrage", legend.get("G"));
		assertEquals("Hall" , legend.get("H"));
		assertEquals("Closet", legend.get("X"));
		assertEquals("Walkway", legend.get("W"));	
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
