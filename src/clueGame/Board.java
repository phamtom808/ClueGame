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
import java.util.Random;
import java.util.Set;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows; 
	private int numColumns; 
	private BoardCell[][] gameBoard;
	private Map<Character, String> legend;
	private Set<Player> players;
	private Set<BoardCell> doorList; //may have become irrelevant for now, but may have use later
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<Card> deck;
	private Card playerCard;
	private Card weaponCard;
	private Card roomCard;
	private String roomConfigFile;
	private String boardConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private static Board theInstance = new Board();
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	/*
	 * initialize():
	 * sets up various local variables and allocates memory for them
	 * initializes visited, target, gameBoard, legend so that even if there is an issue with
	 * the input files, the memory is still there and available
	 * Added instance variable doorList is meant to track the doors in the board as a set so 
	 * that functions that need to know where there are doors can do so easily
	 */
	public void initialize() {
		this.visited = new HashSet<BoardCell>(); 
		this.targets = new HashSet<BoardCell>();
		this.gameBoard = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		this.legend = new HashMap<Character, String>();
		this.doorList = new HashSet<BoardCell>();
		this.deck = new HashSet<Card>();
		try {
			this.loadBoardConfig();
		}catch(BadConfigFormatException e) {
			System.out.println(e);
			System.exit(1); 
		}
		try {
			this.dealDeck();
		}catch(BadConfigFormatException e) {
			System.out.println(e);
			System.exit(2);
		}
		this.calcAdjacencies(); 
	}
	
	//Function called to calculate adjacent cells for all cells in BoardCell class
	public void calcAdjacencies() {
		for(int row = 0; row<this.numRows; row++) {
			for(int columns = 0; columns<this.numColumns; columns++) {
				this.gameBoard[row][columns].calcAdjCells(this);
			}
		}
	}
	
	/*
	 * Set-up function for recursive findAllTargets that takes in path length and current row/column
	 * Algorithm detailed in slides
	 */
	public void calcTargets(int column, int row, int pathLength) {
		this.targets.clear();
		BoardCell startCell = this.getCellAt(row, column);
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
				try {
					String data = readConfig.nextLine();
					String[] lineComponents = data.split(", ");
					if(lineComponents.length < 3) {
						throw new BadConfigFormatException("Error: missing or incorrectly formatted data");
					}
					Character key = lineComponents[0].charAt(0);
					String value = lineComponents[1];
					this.legend.put(key, value);
					if(lineComponents[2] == "card") {
						Card c = new Card(value,CardType.ROOM);
						deck.add(c);
					}
				}catch(Exception e){
					throw new BadConfigFormatException("Incorrect data format for room legend");
				}
			}
			readConfig.close();
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Room Config File not found");
		}catch(BadConfigFormatException e) {
			throw e;
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
			this.numRows = row;
			this.numColumns = column;
			readConfig.close();
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Board Config File not found");
		}catch(Exception e) {
			throw new BadConfigFormatException(e.getMessage());
		}
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException {
		try {
			File playerConfigFile = new File(this.playerConfigFile);
			Scanner readConfig = new Scanner(playerConfigFile);
			while(readConfig.hasNext()) {
				try {
					String data = readConfig.nextLine();
					String[] lineComponents = data.split(", ");
					if(lineComponents.length < 3) {
						throw new BadConfigFormatException("Error: missing or incorrectly formatted data");
					}
					String name = lineComponents[0];
					String color = lineComponents[1];
					boolean isHuman = false;
					if(lineComponents[2].charAt(0) == 'P') {
						isHuman = true;
					}
					BoardCell thisCell;
					try {
						String[] a = lineComponents[3].split(" ");
						int row = Integer.parseInt(a[0]);
						int column = Integer.parseInt(a[1]);
						thisCell = this.getCellAt(row, column);
					}catch(Exception e) {
						throw new BadConfigFormatException("Incorrect start location data");
					}
					Player p = new Player(name,color,thisCell);
					this.players.add(p);
					Card c = new Card(name, CardType.PLAYER);
					this.deck.add(c);
				}catch (BadConfigFormatException e) {
					throw e;
				}catch(Exception e){
					throw new BadConfigFormatException("Incorrect data format for player legend");
				}
			}
			readConfig.close();
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Player Config File not found");
		}catch(BadConfigFormatException e){
			throw e;
		}
		catch(Exception e) {
			throw new BadConfigFormatException("Error: Player config file formatted incorrectly.");
		}
	}
	
	public void loadWeaponConfig() throws BadConfigFormatException {
		try {
			File weaponConfigFile = new File(this.weaponConfigFile);
			Scanner readConfig = new Scanner(weaponConfigFile);
			readConfig.useDelimiter("\n");
			while(readConfig.hasNext()) {
				try {
					String data = readConfig.nextLine();
					Card c = new Card(data, CardType.WEAPON);
					this.deck.add(c);
				}catch(Exception e){
					throw new BadConfigFormatException("Incorrect data format for weapon legend");
				}
			}
			readConfig.close();
		}catch(FileNotFoundException e) {
			throw new BadConfigFormatException("Error: Weapon Config File not found");
		}catch(BadConfigFormatException e) {
			throw e;
		}
		catch(Exception e) {
			throw new BadConfigFormatException("Error: Weapon config file formatted incorrectly.");
		}
	}
	
	public void dealDeck() throws BadConfigFormatException {
		try {
			this.loadRoomConfig();
			this.loadPlayerConfig();
			this.loadWeaponConfig();
		} catch(Exception e) {
			throw new BadConfigFormatException(e.getMessage());
		}
		int deckSize = deck.size();
		Set<Card> playerCards = new HashSet<Card>();
		Set<Card> weaponCards = new HashSet<Card>();
		Set<Card> roomCards = new HashSet<Card>();
		for(Card i: deck) {
			if(i.getCardType() == CardType.PLAYER) {
				playerCards.add(i);
			}else if(i.getCardType() == CardType.ROOM) {
				roomCards.add(i);
			}else if(i.getCardType() == CardType.WEAPON) {
				weaponCards.add(i);
			}
		}
		Random rand = new Random();
		this.playerCard = getRandomCard(rand.nextInt(playerCards.size()), playerCards);
		playerCards.remove(this.playerCard);
		this.roomCard = getRandomCard(rand.nextInt(roomCards.size()), roomCards);
		roomCards.remove(this.roomCard);
		this.weaponCard = getRandomCard(rand.nextInt(weaponCards.size()), weaponCards);
		weaponCards.remove(this.weaponCard);
		int handSize = Math.floorDiv(deck.size(), players.size());
		for(Player p: players) {
			Set<Card> playerHand = dealCards(playerCards, roomCards, weaponCards, handSize);
			p.dealHand(playerHand);
			for(Card c: playerHand) {
				if(c.getCardType() == CardType.PLAYER) {
					playerCards.remove(c);
				}else if(c.getCardType() == CardType.ROOM) {
					roomCards.remove(c);
				}else if(c.getCardType() == CardType.WEAPON) {
					weaponCards.remove(c);
				}
			}
		}
	}
	
	public Set<Card> dealCards(Set<Card> playerCards, Set<Card> roomCards, Set<Card> weaponCards, int handSize){
		Set<Card> hand = new HashSet<Card>();
		Random dealer = new Random();
		while(hand.size() < handSize) {
			int deckToDrawFrom = dealer.nextInt(3);
			if(deckToDrawFrom == 0) {
				if(playerCards.size() > 0) {
					hand.add(getRandomCard(dealer.nextInt(playerCards.size()), playerCards));
				}
			}else if(deckToDrawFrom == 1) {
				if(roomCards.size() > 0) {
					hand.add(getRandomCard(dealer.nextInt(roomCards.size()), roomCards));
				}
			}else {
				if(weaponCards.size() > 0) {
				hand.add(getRandomCard(dealer.nextInt(weaponCards.size()),weaponCards));
				}
			}
		}
		return hand;
	}
	
	private Card getRandomCard(int cardNum, Set<Card> cards) {
		int i = 0;
		for(Card c: cards) {
			if(i == cardNum) {
				return c;
			}
			i++;
		}
		return null;
	}
	
	public BoardCell getCellAt(int row, int column) {
		return this.gameBoard[row][column];
	}
	
	public int getNumColumns() {
		return this.numColumns;
	}
	
	public int getNumRows() {
		return this.numRows;
	}
	
	public Map<Character,String> getLegend(){
		return this.legend;
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

	public void setDeckConfigFiles(String plCfgFile, String wpCfgFile) {
		playerConfigFile = plCfgFile;
		weaponConfigFile = wpCfgFile;
	}
	
	public void setConfigFiles(String bdCfgFile, String rmCfgFile) {
		boardConfigFile = bdCfgFile;
		roomConfigFile = rmCfgFile;
	}

	public Set<Card> getDeck(){
		return this.deck;
	}
	
	public Set<Player> getPlayers(){
		return this.players;
	}
	
}
