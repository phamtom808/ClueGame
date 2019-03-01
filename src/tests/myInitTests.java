package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

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
	public void testDimensions() {
		assertEquals(NUMBER_COLUMNS, board.getNumColumns());
		assertEquals(NUMBER_ROWS, board.getNumRows());
	}
	
	@Test
	public void doorDirection() {
		BoardCell cell = board.getCellAt(9,4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		cell = board.getCellAt(16,8);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCellAt(11, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCellAt(2, 15);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		cell = board.getCellAt(11, 9);
		assertFalse(cell.isDoorway());
		
		cell = board.getCellAt(17, 17);
		assertFalse(cell.isDoorway());
		
		cell = board.getCellAt(20, 6);
		assertFalse(cell.isDoorway());
	}
	
	@Test
	public void testNumberOfDoors() {
		int doors = 0;
		for(int i = 0; i < board.numRows; i++) {
			for(int j = 0; j < board.numColumns; j++) {
				BoardCell cell = board.getCellAt(i, j);
				if(cell.isDoorway()) {
					doors++;
				}
			}
		}
		assertEquals(22, doors);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
