package tests;
//NOTE: format for getAdjLists, calcTargets parameters is row,column (y,x)

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
		Set<BoardCell> testList = board.getAdjList(11, 8);
		assertTrue(testList.contains(board.getCellAt(12, 8)));
		assertTrue(testList.contains(board.getCellAt(10, 8)));
		assertTrue(testList.contains(board.getCellAt(11, 7)));
		assertEquals (3, testList.size());
		
		//Test on piece at top of board
		testList = board.getAdjList(0, 12);
		assertTrue(testList.contains(board.getCellAt(0, 11)));
		assertTrue(testList.contains(board.getCellAt(1, 12)));
		assertEquals(2, testList.size());
		
		//Test piece surrounded by 4 walkways
		testList = board.getAdjList(8, 20);
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertTrue(testList.contains(board.getCellAt(9, 20)));
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertEquals(4, testList.size());
		
		//Test on bottom side of board, next to 1 room piece
		testList = board.getAdjList(19, 12);
		assertTrue(testList.contains(board.getCellAt(18, 12)));
		assertTrue(testList.contains(board.getCellAt(19, 11)));
		assertEquals(2, testList.size());
		
		//Test on walkway next to door that isn't the correct direction
		testList = board.getAdjList(5, 2);
		assertTrue(testList.contains(board.getCellAt(4, 2)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacenciesInsideRooms() {
		// Test corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		//Test room cell with a walkway to the right
		testList = board.getAdjList(2, 4);
		assertEquals(0, testList.size());
		//Test room cell with a walkway above
		testList = board.getAdjList(6, 5);
		assertEquals(0, testList.size());
		//Test room cell in the middle of a room
		testList = board.getAdjList(1, 21);
		assertEquals(0, testList.size());
		//Test a corner a room in the middle
		testList = board.getAdjList(6, 18);
		assertEquals(0, testList.size());
		//Test a room cell beside a door
		testList = board.getAdjList(18, 10);
		assertEquals(0, testList.size());
	}
	
	@Test
	public void testAdjacencyRoomExit() {
		//Test doorway right
		Set<BoardCell> testList = board.getAdjList(16, 10);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 11)));
		//Test doorway left
		testList = board.getAdjList(9, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 1)));
		//Test doorway down
		testList = board.getAdjList(5, 0);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		//test doorway up
		testList = board.getAdjList(14, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		//test doorway up where there's a walkway to the left
		testList = board.getAdjList(12, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 13)));
	}
}
