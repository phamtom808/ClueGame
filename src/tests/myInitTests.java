/**
 * @author Dane Pham

 * @author Emily Christensen
 * myInitTests Class: tests the various aspects of creating the gameboard are being conducted correctly
 */
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
	/**
	 * tests if the board was initialized correctly and config files are set correctly
	 */
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
	}
	
	@Test
	/**
	 * tests if the legend is loaded in correctly
	 */
	public void testLegend() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Conservatory", legend.get('C'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Bedroom", legend.get('B'));
		assertEquals("Theater", legend.get('T'));
		assertEquals("Living Room", legend.get('L'));
		assertEquals("Study", legend.get('S'));
		assertEquals("Pool", legend.get('P'));
		assertEquals("Garrage", legend.get('G'));
		assertEquals("Hall" , legend.get('H'));
		assertEquals("Closet", legend.get('X'));
		assertEquals("Walkway", legend.get('W'));	
	}
	
	@Test
	/**
	 * tests if the board dimensions were set correctly
	 */
	public void testDimensions() {
		assertEquals(NUMBER_COLUMNS, board.getNumColumns());
		assertEquals(NUMBER_ROWS, board.getNumRows());
	}
	
	@Test
	/**
	 * tests if specific door cells are intialized with the correct door direction
	 */
	public void doorDirection() {
		BoardCell cell = board.getCellAt(3, 9);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		cell = board.getCellAt(7, 15);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCellAt(17, 10);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCellAt(14, 1);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		cell = board.getCellAt(8, 10);
		assertFalse(cell.isDoorway());
		
		cell = board.getCellAt(16, 16);
		assertFalse(cell.isDoorway());
		
		cell = board.getCellAt(5, 19);
		assertFalse(cell.isDoorway());
	}
	
	@Test
	/**
	 * tests if the number of doors made are correct
	 */
	public void testNumberOfDoors() {
		int doors = 0;
		for(int i = 0; i < board.getNumRows(); i++) {
			for(int j = 0; j < board.getNumColumns(); j++) {
				BoardCell cell = board.getCellAt(i, j);
				if(cell.isDoorway()) {
					doors++;
				}
			}
		}
		assertEquals(22, doors);
	}
	
	@Test
	/**
	 * tests if the various cell initials match the cell type
	 */
	public void testInitials() {
		assertEquals('C', board.getCellAt(2, 3).getInitial());
		assertEquals('L', board.getCellAt(8, 5).getInitial());
		assertEquals('S', board.getCellAt(18, 8).getInitial());
		assertEquals('G', board.getCellAt(3, 8).getInitial());
		assertEquals('W', board.getCellAt(13, 12).getInitial());
		assertEquals('A', board.getCellAt(9, 15).getInitial());
		assertEquals('T', board.getCellAt(19, 18).getInitial());
		assertEquals('K', board.getCellAt(0, 23).getInitial());
		assertEquals('P', board.getCellAt(19, 0).getInitial());
		assertEquals('B', board.getCellAt(19, 23).getInitial());
		assertEquals('X', board.getCellAt(10, 10).getInitial());
	}

}
