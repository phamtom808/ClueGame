package clueGame;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	public int numRows; //Corresponds to y
	public int numColumns; //Corresponds to x
	private BoardCell[][] gameBoard;
	private Map<Character, String> legend;
	private Set<BoardCell> targets;
	private static final String ROOM_CONFIG_FILE = "roomConfig.txt";
	private static final String BOARD_CONFIG_FILE = "boardConfig.csv";
	private static Board theInstance = new Board();
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		gameBoard = new BoardCell[numColumns][numRows];
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
		return this.gameBoard[x][y];
		/*BoardCell b = new BoardCell(0,0,' ');
		return b;*/
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			File roomConfigFile = new File(ROOM_CONFIG_FILE);
			Scanner readConfig = new Scanner(roomConfigFile);
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: File not found");
		}
		return;
	}
	
	public void loadBoardConfig() {
		return;
	}
}
