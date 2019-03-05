/**
 * @author Emily Christensen
 * @author Dane Pham
 * Board Class: handles creating the gameboard and interacting with it
 */
package clueGame;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
		this.legend = new HashMap<Character, String>();
		try {
			this.loadBoardConfig();
		}catch(BadConfigFormatException e) {
			System.out.println(e);
			for(int i = 0; i<MAX_BOARD_SIZE; i++) {
				for(int j = 0; j<MAX_BOARD_SIZE; j++) {
					BoardCell b = new BoardCell(i,j,' ');
					this.gameBoard[i][j] = b;
				}
			}
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
		for(int i = 0; i<this.numColumns; i++) {
			for(int j = 0; j<this.numRows; j++) {
				this.gameBoard[i][j].calcAdjCells(getInstance());
			}
		}
		return;
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
				readConfig.useDelimiter(",");
				Character key =  readConfig.next().charAt(0);
				String roomName = readConfig.next();
				readConfig.useDelimiter("\n");
				String isCard = readConfig.next();
				legend.put(key, roomName);
			}
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Room Config File not found");
		}catch(Exception e) {
			throw new BadConfigFormatException("Error: Room config file formatted incorrectly.");
		}
	}

	
	/*
	 * loadBoardConfig method
	 * Reads in data from CSV file, using \n as delimiters. 
	 * If it can't open the file, throws specific BadConfigFormatException that matches roomConfigFile
	 * Assuming nothing causes an error, read in each new line from readConfig, if the character is a D, read in next char for direction, then set direction on cell
	 * After creating cell, add it to gameboard in proper index
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		try {
			File boardConfigFile = new File(this.boardConfigFile);
			Scanner readConfig = new Scanner(boardConfigFile);
			readConfig.useDelimiter("\n");
			int i = 0;
			while(readConfig.hasNext()) {
				String nline = readConfig.nextLine();
				int k = 0;
				for(int j = 0; j<nline.length(); j++) {
					Character a = nline.charAt(j);
					if(a != ',') {
						BoardCell b = new BoardCell(i,k,a);
						k++;
						if(j<nline.length() - 1) {
							if(nline.charAt(j+1) != ',') {
								j++;
								Character direction = nline.charAt(j);
								if(direction == 'R') {
									b.setDoorDirection(DoorDirection.RIGHT);
								}else if(direction == 'L') {
									b.setDoorDirection(DoorDirection.LEFT);
								}else if(direction == 'U') {
									b.setDoorDirection(DoorDirection.UP);
								}else if(direction == 'D') {
									b.setDoorDirection(DoorDirection.DOWN);
								}else {
									b.setDoorDirection(DoorDirection.NONE);
								}
							}
						}
						gameBoard[i][k] = b;
					}
				}
			}
			numRows = i;
			numColumns = gameBoard[i].length;
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Board Config File not found");
		}catch(Exception e) {
			throw new BadConfigFormatException("Error: Board config file formatted incorrectly.");
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
