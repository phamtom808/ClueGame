/**
 * @author Emily Christensen
 * @author Dane Pham
 * Board Class: handles creating the gameboard and interacting with it
 */
package clueGame;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows; //Corresponds to y
	private int numColumns; //Corresponds to x
	private BoardCell[][] gameBoard;
	private HashMap<Character, String> legend;
	private Set<BoardCell> targets;
	private String roomConfigFile;
	private String boardConfigFile;
	private static Board theInstance = new Board();
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		this.gameBoard = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<Character, String>();
		for(int i = 0; i<MAX_BOARD_SIZE; i++) {
			for(int j = 0; j<MAX_BOARD_SIZE; j++) {
				BoardCell b = new BoardCell(i,j,' ');
				this.gameBoard[i][j] = b;
			}
		}
		try {
			this.loadBoardConfig();
		}catch(BadConfigFormatException e) {
			System.out.println(e);
		}
		try {
			this.loadRoomConfig();
		}catch(BadConfigFormatException e) {
			System.out.println(e);
			legend.put(' ', "FailedConfig");
		}
	}
	
	//Function to calculate adjacent cells stored in BoardCell class, so is adjacency data
	public void calcAdjacencies() {
		for(int i = 0; i<this.numRows; i++) {
			for(int j = 0; j<this.numColumns; j++) {
				this.gameBoard[j][i].calcAdjCells(getInstance());
			}
		}
		return;
	}
	
	//Function to return adjacent cells stored in BoardCell class
	public HashSet<BoardCell> getAdjList(int x, int y){
		return this.gameBoard[x][y].getAdjCells();
	}
	
	public BoardCell getCellAt(int x, int y) {
		return this.gameBoard[x][y];
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
			while(readConfig.hasNext()) {
				String data = readConfig.nextLine();
				String[] lineComponents = data.split(", ");
				Character key = lineComponents[0].charAt(0);
				String value = lineComponents[1];
				this.legend.put(key, value);
			}
			readConfig.close();
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Room Config File not found");
		}catch(Exception e) {
			System.out.println(e);
			throw new BadConfigFormatException("Error: Room config file formatted incorrectly.");
		}
	}

	
	/*
	 * loadBoardConfig method
	 * Reads in data from CSV file, using \n as delimiters. 
	 * If it can't open the file, throws specific BadConfigFormatException that matches roomConfigFile
	 * Assuming nothing causes an error, read in each new line from readConfig, if the character is a D, read in next char for direction, then set direction on cell
	 * After creating cell, add it to gameboard in proper index
	 * If cell has 2 characters, determine if it is a door, and, if so, what direction it should go
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		try {
			File boardConfigFile = new File(this.boardConfigFile);
			Scanner readConfig = new Scanner(boardConfigFile);
			int i = 0;
			int j = 0;
			int maxRowSize = 0;
			while(readConfig.hasNext()) {
				String data = readConfig.nextLine();
				String[] cells = data.split(",");
				if(maxRowSize == 0) {
					maxRowSize = cells.length;
				}else {
					if(maxRowSize != cells.length) {
						throw new BadConfigFormatException("Error: incorrect number of cells per row\n Row Number: " + i);
					}
				}
				for(j = 0; j<cells.length; j++) {
					String cellData = cells[j];
					Character initial = cellData.charAt(0);
					gameBoard[i][j].setInitial(initial);
					if(cellData.length() > 1) {
						Character direction = cellData.charAt(1);
						if(direction == 'R') {
							gameBoard[i][j].setDoorDirection(DoorDirection.RIGHT);
						}else if(direction == 'L') {
							gameBoard[i][j].setDoorDirection(DoorDirection.LEFT);
						}else if(direction == 'D') {
							gameBoard[i][j].setDoorDirection(DoorDirection.DOWN);
						}else if(direction == 'U') {
							gameBoard[i][j].setDoorDirection(DoorDirection.UP);
						}
					}
				}
				i++;
			}
			readConfig.close();
			this.numRows = i;
			this.numColumns = j;
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Board Config File not found");
		}catch(Exception e) {
			throw new BadConfigFormatException(e.getMessage());
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
	
	/*
	 * Takes in config files, sets the current instance's config files to the given ones
	 */
	public void setConfigFiles(String bdCfgFile, String rmCfgFile) {
		boardConfigFile = bdCfgFile;
		roomConfigFile = rmCfgFile;
	}
}
