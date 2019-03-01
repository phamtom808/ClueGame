package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	public int numRows; //Corresponds to y
	public int numColumns; //Corresponds to x
	private BoardCell[][] gameBoard;
	private Map<Character, String> legend;
	private Set<BoardCell> targets;
	
	private static Board theInstance;
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		this.gameBoard = new BoardCell[numColumns][numRows];
		this.calcAdjacencies();
	}
	
	//Function to calculate adjacent cells stored in BoardCell class, so is adjacency data
	public void calcAdjacencies() {
		for(int i = 0; i<this.numColumns; i++) {
			for(int j = 0; j<this.numRows; j++) {
				this.gameBoard[i][j].calcAdjCells(getInstance());
			}
		}
		return;
	}
	
	public BoardCell getCellAt(int x, int y) {
		//return this.gameBoard[x][y];
		BoardCell b = new BoardCell(0,0,' ');
		return b;
	}
	
	public void loadRoomConfig() {
		return;
	}
	
	public void loadBoardConfig() {
		return;
	}
}
