package clueGame;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows; //Corresponds to y
	private int numColumns; //Corresponds to x
	private BoardCell[][] gameBoard;
	private Map<Character, String> legend;
	private Set<BoardCell> targets;
	private String roomConfigFile;
	private String boardConfigFile;
	private static Board theInstance = new Board();
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		gameBoard = new BoardCell[numColumns][numRows];
		try {
			this.loadRoomConfig();
			this.loadBoardConfig();
		}catch(BadConfigFormatException e) {
			System.out.println(e);
		}
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
	
	/*
	 * loadRoomConfig
	 * Attempt to read in config file. If file doesn't open/isn't found, throw BadConfigFormatException with message that file wasn't found
	 * If file opens, but there is another error (file text isn't in proper format), throw BadConfigFormatException with message that file wasn't formatted properly
	 * Otherwise, add char key, string roomName to legend map
	 */
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			File roomConfigFile = new File(this.roomConfigFile);
			Scanner readConfig = new Scanner(roomConfigFile);
			readConfig.useDelimiter(",");
			while(readConfig.hasNext()) {
					Character key =  readConfig.next().charAt(0);
					String roomName = readConfig.next();
					String isCard = readConfig.next();
					legend.put(key, roomName);
			}
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: File not found");
		}catch(Exception e) {
			throw new BadConfigFormatException("Error: Room config file formatted incorrectly.");
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException {
		try {
			File boardConfigFile = new File(this.boardConfigFile);
			Scanner readConfig = new Scanner(boardConfigFile);
			readConfig.useDelimiter(",");
			while(readConfig.hasNext()) {
				//Set up cells
			}
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: File not found");
		}catch(Exception e) {
			throw new BadConfigFormatException("Error: Room config file formatted incorrectly.");
		}
		return;
	}
	
	public int getNumRows() {
		return this.numRows;
	}
	
	public int getNumColumns() {
		return this.numColumns;
	}
	
	public Map<Character,String> getLegend(){
		return this.legend;
	}
	
	public void setConfigFiles(String bdCfgFile, String rmCfgFile) {
		boardConfigFile = bdCfgFile;
		roomConfigFile = rmCfgFile;
	}
}
