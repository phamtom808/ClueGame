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
	public void test() {
		fail("Not yet implemented");
	}

}
