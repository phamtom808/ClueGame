package tests;
//NOTE: format for getAdjLists, calcTargets parameters is row,column (y,x)
/*
 * @author Emily Christensen
 * @author Dane Pham
 */

import static org.junit.Assert.*;

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
		board.setDeckConfigFiles("CluePlayers.txt","ClueWeapons.txt");
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
		
		//Test piece inside room corner
		testList = board.getAdjList(8, 20);
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertTrue(testList.contains(board.getCellAt(9, 20)));
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertEquals(4, testList.size());
		
		//Test on bottom side of board, next to 1 room piece
		testList = board.getAdjList(17, 11);
		assertTrue(testList.contains(board.getCellAt(18, 11)));
		assertTrue(testList.contains(board.getCellAt(16, 11)));
		assertTrue(testList.contains(board.getCellAt(17, 10)));
		assertTrue(testList.contains(board.getCellAt(17, 12)));
		assertEquals(4, testList.size());
		
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
	
	@Test
	public void testTargetsOneStep() {
		//test cell on bottom of board with 2 targets 1 cell away
		board.calcTargets(19, 5, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(19, 6)));
		//test cell on left of board with 2 targets 1 cell away
		board.calcTargets(9, 0, 1);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(8, 0)));
		assertTrue(targets.contains(board.getCellAt(10, 0)));
		assertTrue(targets.contains(board.getCellAt(9, 1)));
	}
	
	@Test
	public void testTargetsTwoStep() {
		//test cell on bottom of board with 2 targets, 2 cells away
		board.calcTargets(19, 6, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		//test cell in middle of board with 7 targets, 2 cells away
		board.calcTargets(8, 20, 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 21)));
		assertTrue(targets.contains(board.getCellAt(10, 20)));
		assertTrue(targets.contains(board.getCellAt(7, 19)));
		assertTrue(targets.contains(board.getCellAt(9, 19)));
		assertTrue(targets.contains(board.getCellAt(6, 20)));
		assertTrue(targets.contains(board.getCellAt(8, 22)));
	}
	
	@Test
	public void testTargetsFourStep() {
		//test cell on bottom of board with 4 targets, 4 cells away
		board.calcTargets(19, 6, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		assertTrue(targets.contains(board.getCellAt(16, 5)));
		assertTrue(targets.contains(board.getCellAt(15, 6)));
		//test cell in middle of board
		board.calcTargets(11, 12, 4);
		targets = board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 12)));
		assertTrue(targets.contains(board.getCellAt(10, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		assertTrue(targets.contains(board.getCellAt(10, 15)));
		assertTrue(targets.contains(board.getCellAt(13, 12)));
		assertTrue(targets.contains(board.getCellAt(11, 14)));
		assertTrue(targets.contains(board.getCellAt(11, 16)));
		assertTrue(targets.contains(board.getCellAt(12, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 10)));
		assertTrue(targets.contains(board.getCellAt(9, 14)));
		assertTrue(targets.contains(board.getCellAt(10, 11)));
		assertTrue(targets.contains(board.getCellAt(8, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 11)));
		assertTrue(targets.contains(board.getCellAt(14, 11)));
		assertTrue(targets.contains(board.getCellAt(12, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 9)));
	}
	
	@Test
	public void testTargetsSixStep() {
		//test cell on top of board
		board.calcTargets(0, 12, 6);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 13)));
		assertTrue(targets.contains(board.getCellAt(2, 12)));
		assertTrue(targets.contains(board.getCellAt(3, 11)));
		assertTrue(targets.contains(board.getCellAt(5, 11)));
		assertTrue(targets.contains(board.getCellAt(1, 11)));
		assertTrue(targets.contains(board.getCellAt(5, 13)));
		assertTrue(targets.contains(board.getCellAt(4, 14)));
		assertTrue(targets.contains(board.getCellAt(4, 10)));
		assertTrue(targets.contains(board.getCellAt(4, 12)));
	}
	
	@Test
	public void testTargetsIntoRoom() {
		//room is exactly 2 away
		board.calcTargets(16, 12, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		//directly left
		assertTrue(targets.contains(board.getCellAt(16, 10)));
		//directly up and down
		assertTrue(targets.contains(board.getCellAt(14, 12)));
		assertTrue(targets.contains(board.getCellAt(18, 12)));
		//one away
		assertTrue(targets.contains(board.getCellAt(15, 11)));
		assertTrue(targets.contains(board.getCellAt(17, 11)));
	}
	
	@Test
	public void testTargetsIntoRoomShortcut() {
		board.calcTargets(12, 19, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(12, targets.size());
		//directly up and down
		assertTrue(targets.contains(board.getCellAt(9, 19)));
		assertTrue(targets.contains(board.getCellAt(15, 19)));
		//directly left
		assertTrue(targets.contains(board.getCellAt(12, 16)));
		//into the rooms
		assertTrue(targets.contains(board.getCellAt(11, 18)));
		assertTrue(targets.contains(board.getCellAt(11, 17)));
		//three away
		assertTrue(targets.contains(board.getCellAt(10, 20)));
		assertTrue(targets.contains(board.getCellAt(14, 20)));
		assertTrue(targets.contains(board.getCellAt(11, 19)));
		assertTrue(targets.contains(board.getCellAt(12, 18)));
		assertTrue(targets.contains(board.getCellAt(13, 19)));
		assertTrue(targets.contains(board.getCellAt(13, 17)));
		assertTrue(targets.contains(board.getCellAt(12, 20)));
	}
	
	@Test
	public void testRoomExit() {
		board.calcTargets(3, 8, 1);
		Set<BoardCell> targets = board.getTargets();
		//test for one away, can only go forward not into the adjacent door
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 8)));
		//take two steps
		board.calcTargets(3, 8, 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(4, 9)));
		assertTrue(targets.contains(board.getCellAt(4, 7)));
	}
}
