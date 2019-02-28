package tests;

import static org.junit.Assert.*;

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
		board = board.getInstances();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
