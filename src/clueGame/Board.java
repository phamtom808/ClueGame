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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows; 
	private int numColumns; 
	private BoardCell[][] gameBoard;
	private Map<Character, String> legend;
	private Set<BoardCell> doorList; //may have become irrelevant for now, but may have use later
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String roomConfigFile;
	private String boardConfigFile;
	private static Board theInstance = new Board();
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		this.visited = new HashSet<BoardCell>();
		this.targets = new HashSet<BoardCell>();
		this.gameBoard = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		this.legend = new HashMap<Character, String>();
		this.doorList = new HashSet<BoardCell>();
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
		this.calcAdjacencies();
	}
	
	//Function to calculate adjacent cells stored in BoardCell class, so is adjacency data
	public void calcAdjacencies() {
		for(int row = 0; row<this.numRows; row++) {
			for(int columns = 0; columns<this.numColumns; columns++) {
				this.gameBoard[row][columns].calcAdjCells(this);
			}
		}
	}
	
	
	public BoardCell getCellAt(int row, int column) {
		return this.gameBoard[row][column];
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
			int row = 0;
			int column = 0;
			int maxRowSize = 0;
			while(readConfig.hasNext()) {
				String data = readConfig.nextLine();
				String[] cells = data.split(",");
				if(maxRowSize == 0) {
					maxRowSize = cells.length;
				}else {
					if(maxRowSize != cells.length) {
						throw new BadConfigFormatException("Error: incorrect number of cells per row\n Row Number: " + row);
					}
				}
				for(column = 0; column<cells.length; column++) {
					String cellData = cells[column];
					Character initial = cellData.charAt(0);
					gameBoard[row][column] = new BoardCell(row,column, ' ');
					gameBoard[row][column].setInitial(initial);
					if(cellData.length() > 1) {
						Character direction = cellData.charAt(1);
						if(direction == 'R') {
							gameBoard[row][column].setDoorDirection(DoorDirection.RIGHT);
							doorList.add(gameBoard[row][column]);
						}else if(direction == 'L') {
							gameBoard[row][column].setDoorDirection(DoorDirection.LEFT);
							doorList.add(gameBoard[row][column]);
						}else if(direction == 'D') {
							gameBoard[row][column].setDoorDirection(DoorDirection.DOWN);
							doorList.add(gameBoard[row][column]);
						}else if(direction == 'U') {
							gameBoard[row][column].setDoorDirection(DoorDirection.UP);
							doorList.add(gameBoard[row][column]);
						}
					}
				}
				row++;
			}
			readConfig.close();
			this.numRows = row;
			this.numColumns = column;
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
	
	public void setConfigFiles(String bdCfgFile, String rmCfgFile) {
		boardConfigFile = bdCfgFile;
		roomConfigFile = rmCfgFile;
	}
	
	public Set<BoardCell> getDoorList(){
		return this.doorList;
	}
	
	public Set<BoardCell> getAdjList(int row, int column){
		return this.getCellAt(row,column).getAdjCells();
	}

	public Set<BoardCell> getTargets(){
		return this.targets;
	}
	/*
	 * Set-up function for recursive findAllTargets that takes in path length and current row/column
	 * Algorithm detailed in slides
	 */
	public void calcTargets(int column, int row, int pathLength) {
		this.targets.clear();
		BoardCell startCell = this.getCellAt(column, row);
		this.visited.add(startCell);
		findAllTargets(startCell, pathLength);
		this.visited.clear();
	}
	
	/*
	 * Recursive function for calcTargets
	 * Algorithm detailed in slides
	 * Determines correct targets for current cell recursively
	 */
	public void findAllTargets(BoardCell b, int pathLength) {
		for(BoardCell adj: b.getAdjCells()) {
			if(!this.visited.contains(adj)) {
				visited.add(adj);
				if(pathLength == 1 || adj.isDoorway()) {
					this.targets.add(adj);
				}else {
					findAllTargets(adj, pathLength-1);
				}
				visited.remove(adj);
			}
		}
	}
	
}
