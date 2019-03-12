package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class MyCalcAdjacencyTests {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.initialize();
	}
	
	@Test
	public void testAdjacencyWalkways() {
		// Test on piece in middle of board, walkways top, bottom, left
		Set<BoardCell> testList = board.getAdjList(8, 11);
		assertTrue(testList.contains(board.getCellAt(8, 12)));
		assertTrue(testList.contains(board.getCellAt(8, 10)));
		assertTrue(testList.contains(board.getCellAt(7, 11)));
		assertEquals (3, testList.size());
		
		//Test on piece at top of board
		testList = board.getAdjList(12, 0);
		assertTrue(testList.contains(board.getCellAt(11, 0)));
		assertTrue(testList.contains(board.getCellAt(12, 1)));
		assertEquals(2, testList.size());
		
		//Test piece surrounded by 4 walkways
		testList = board.getAdjList(20, 8);
		assertTrue(testList.contains(board.getCellAt(21, 8)));
		assertTrue(testList.contains(board.getCellAt(20, 7)));
		assertTrue(testList.contains(board.getCellAt(20, 9)));
		assertTrue(testList.contains(board.getCellAt(19, 8)));
		assertEquals(4, testList.size());
		
		//Test on bottom side of board, next to 1 room piece
		testList = board.getAdjList(12, 19);
		assertTrue(testList.contains(board.getCellAt(12, 18)));
		assertTrue(testList.contains(board.getCellAt(11, 19)));
		assertEquals(2, testList.size());
		
		//Test on walkway next to door that isn't the correct direction
		testList = board.getAdjList(2, 5);
		assertTrue(testList.contains(board.getCellAt(2, 4)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertTrue(testList.contains(board.getCellAt(2, 6)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacenciesInsideRooms() {
		// Test corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		//Test room cell with a walkway to the right
		testList = board.getAdjList(4, 2);
		assertEquals(0, testList.size());
		//Test room cell with a walkway above
		testList = board.getAdjList(5, 6);
		assertEquals(0, testList.size());
		//Test room cell in the middle of a room
		testList = board.getAdjList(21, 1);
		assertEquals(0, testList.size());
		//Test a corner a room in the middle
		testList = board.getAdjList(18, 6);
		assertEquals(0, testList.size());
		//Test a room cell beside a door
		testList = board.getAdjList(10, 18);
		assertEquals(0, testList.size());
	}
	
	@Test
	public void testAdjacencyRoomExit() {
		//Test doorway right
		Set<BoardCell> testList = board.getAdjList(10, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 16)));
		//Test doorway left
		testList = board.getAdjList(2, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(1, 9)));
		//Test doorway down
		testList = board.getAdjList(0, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(0, 6)));
		//test doorway up
		testList = board.getAdjList(1, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(1, 13)));
		//test doorway up where there's a walkway to the left
		testList = board.getAdjList(13, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 11)));
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
