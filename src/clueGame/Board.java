package clueGame;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	public int numRows;
	public int numColumnss;
	
	
	private static Board theInstance;
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	
}
