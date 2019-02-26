package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import experiments.BoardCell;

class IntBoardTests {

	@Before
	public void beforeAll() {
		board = new IntBoard(); // constructor should call calcAdjacencies()
	}
	
	/**
	 * Test adjacencies for top left corner
	 */
	@Test
	public void testAdjacency0() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertEquals(2, testList.size());
	}
	
	/**
	 * Test adjacencies for bottom left corner
	 */
	@Test
	public void testAdjacency3() {
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2,3)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertEquals(2, testList.size());
	}
	
	/**
	 * Test adjacencies for a right edge
	 */
	@Test
	public void testAdjacencyRightEdge() {
		BoardCell cell= board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,3)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertEquals(3,testList.size());
	}
	
	/**
	 * Test adjacencies for a left edge
	 */
	public void testAdjacencyLeftEdge() {
		BoardCell cell = board.getCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2,0)));
		assertTrue(testList.contains(board.getCell(3,1)));
		assertEquals(2, testList.size());
	}
	
	/**
	 * Test adjacencies for second column middle of grid
	 */
	@Test
	public void testSecondColumn() {
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(1,3)));
		assertEquals(4, testList.size());
	}
	
	/**
	 * Test adjacencies for second from last column, middle of grid
	 */
	@Test
	public void testThirdColumn() {
		Board cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(testlist.conatins(4, testList.size()));
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}

}