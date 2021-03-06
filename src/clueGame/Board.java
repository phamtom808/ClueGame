/**
 * @author Emily Christensen
 * @author Dane Pham
 * Board Class: handles creating the gameboard and interacting with it
 */

package clueGame;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.*;

public class Board extends JPanel implements MouseListener{
	
	public static final int MAX_BOARD_SIZE = 50;
	public static final int CELL_SIZE = 20; //cells are pixels of size 10x10, diameter of player token is also 10.
	private int numRows; 
	private int numColumns; 
	private static Guess lastSentGuess;
	private BoardCell[][] gameBoard;
	private BoardCell cellClicked;
	private Map<Character, String> legend;
	private ArrayList<Player> players;
	private int currentPlayerIndex;
	private Set<BoardCell> doorList; //may have become irrelevant for now, but may have use later
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<Card> deck;
	private Set<Card> cardsSeen;
	private Card playerCard;
	private Card weaponCard;
	private Card roomCard;
	private String roomConfigFile;
	private String boardConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private boolean makeAccusation = false;
	private Guess nextAccusation = new Guess();
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
		this.cardsSeen = new HashSet<Card>();
		this.visited = new HashSet<BoardCell>(); 
		this.targets = new HashSet<BoardCell>();
		this.gameBoard = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		this.legend = new HashMap<Character, String>();
		this.doorList = new HashSet<BoardCell>();
		this.deck = new HashSet<Card>();
		this.players = new ArrayList<Player>();
		lastSentGuess = new Guess();
		try {
			this.loadBoardConfig();
		}catch(BadConfigFormatException e) {
			e.printStackTrace();
			System.exit(1); 
		}
		try {
			this.dealDeck();
		}catch(BadConfigFormatException e) {
			e.printStackTrace();
			System.exit(2);
		}
		this.calcAdjacencies(); 
		currentPlayerIndex = 0;
		cellClicked = gameBoard[0][0];
		this.addMouseListener(this);
	}
	
	public Card handleSuggestion(Guess suggestion, int suggesterIndex) {
			//the split for loops prevent the suggester from being the one to disprove
		Card c = null;
		for(int i = suggesterIndex+1; i<players.size(); i++) {
			Player p = players.get(i);
			if(p.getIsHumanPlayer()) {
				p.disproveHumanSuggestion(suggestion);
				c = p.getCardSelected();
				if(p == null && p.wasCardSelected()) {
					i--;
				}
			}else {
				c = p.disproveSuggestion(suggestion);
			}
		}
		for(int i = 0; i<suggesterIndex-1; i++){
			Player p = players.get(i);
			if(p.getIsHumanPlayer()) {
				p.disproveHumanSuggestion(suggestion);
				c = p.getCardSelected();
				if(p == null && p.wasCardSelected()) {
					i--;
				}
			}else {
				c = p.disproveSuggestion(suggestion);
			}
		}
		if(c!= null) {
			cardsSeen.add(c); 
		}
		if(c == null) {
			makeAccusation = true;
			nextAccusation = suggestion;
		}
		return c;
	}
	
	public boolean checkAccusation(Guess accusation) {
		if(accusation.getPlayer().equals(this.playerCard) && accusation.getRoom().equals(this.roomCard)&& accusation.getWeapon().equals(this.weaponCard)  ) {
			return true;
		}
		return false;
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
	public void calcTargets(int row, int column, int pathLength) {
		targets.clear();
		BoardCell startCell = this.getCellAt(row, column);
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
		visited.clear();
	}
	
	/*
	 * Recursive function for calcTargets
	 * Algorithm detailed in slides
	 * Determines correct targets for current cell recursively
	 */
	public void findAllTargets(BoardCell b, int pathLength) {
		for(BoardCell adj: b.getAdjCells()) {
			if(!visited.contains(adj)) {
				visited.add(adj);
				if(pathLength == 1 || adj.isDoorway()) {
					targets.add(adj);
				}else {
					findAllTargets(adj, pathLength-1);
				}
			}
		}
	}
	
	public String getName(Character c) {
		if(legend.get(c) != null) {
			return legend.get(c);
		}else {
			return "ERROR: NAME NOT FOUND";
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
					String[] lineComponents = data.split(",");
					if(lineComponents.length < 3) {
						throw new BadConfigFormatException("Error: missing or incorrectly formatted data");
					}
					Character key = lineComponents[0].charAt(0);
					String value = lineComponents[1].trim();
					this.legend.put(key, value);
					if(lineComponents[2].trim().toLowerCase().equals("card")) {
						Card c = new Card(value, CardType.ROOM);
						this.deck.add(c);
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
						}else if(direction == 'N'){
							gameBoard[row][column].setIsNameCell(true);
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
					String[] lineComponents = data.split(",");
					if(lineComponents.length < 3) {
						throw new BadConfigFormatException("Error: missing or incorrectly formatted data");
					}
					String name = lineComponents[0].trim();
					String color = lineComponents[1].trim();
					boolean isHuman = false;
					lineComponents[2] = lineComponents[2].trim();
					if(lineComponents[2].charAt(0) == 'P') {
						isHuman = true;
					}
					BoardCell thisCell;
					try {
						int row = Integer.parseInt(lineComponents[3].trim());
						int column = Integer.parseInt(lineComponents[4].trim());
						thisCell = this.getCellAt(row, column);
					}catch (Exception e) {
						throw new BadConfigFormatException("Incorrect start location data");
					}
					Player p;
					if(isHuman) {
						p = new HumanPlayer(name, color, thisCell);
						p.setIsHuman(true);
					}else {
						p = new ComputerPlayer(name, color, thisCell);
					}
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
			e.printStackTrace();
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
					data.trim();
					Card c = new Card(data, CardType.WEAPON);
					deck.add(c);
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
	
	/*
	 * deadDeck() function
	 * reads in config files (except board config) and then uses them to build the decks
	 * Randomly chooses a card from player cards, weapon cards, room cards
	 * Removes those cards from the deck, then deals the remaining cards to the players
	 * Iterates through list of players and pulls random card to ensure that player hands are approximately equal in size
	 * This means, however, that the first few players will ALWAYS have the extra cards, including the human player
	 */
	public void dealDeck() throws BadConfigFormatException {
		try {
			this.loadRoomConfig();
			this.loadPlayerConfig();
			this.loadWeaponConfig();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BadConfigFormatException(e.getMessage());
		}
		int deckSize = deck.size();
		Set<Card> playerCards = new HashSet<Card>();
		Set<Card> weaponCards = new HashSet<Card>();
		Set<Card> roomCards = new HashSet<Card>();
		Set<Card> tempDeck = new HashSet<Card>();
		tempDeck.addAll(deck); //copy deck so that cards can be removed as they are dealt, then deck can be restored after
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
		deck.remove(this.playerCard);
		this.roomCard = getRandomCard(rand.nextInt(roomCards.size()), roomCards);
		roomCards.remove(this.roomCard);
		deck.remove(this.roomCard);
		this.weaponCard = getRandomCard(rand.nextInt(weaponCards.size()), weaponCards);
		weaponCards.remove(this.weaponCard);
		deck.remove(this.weaponCard);
		int i = 0;
		while(deck.size() > 0) {
			for(Player p: players) {
				if(deck.size() > 0) {
					Card nextCard = getRandomCard(rand.nextInt(deck.size()), deck);
					p.dealCard(nextCard);
					deck.remove(nextCard);
				}
			}
		}
		deck.addAll(tempDeck);
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
	
	public Card getWeaponCard() {
		return this.weaponCard;
	}
	
	public Card getRoomCard() {
		return this.roomCard;
	}
	
	public Card getPlayerCard() {
		return this.playerCard;
	}
	
	public Set<Card> getCardsSeen(){
		return this.cardsSeen;
	}
	
	public Map<Character,String> getLegend(){
		return this.legend;
	}
	
	public Card getCardFromLegend(Character c) {
		String cardName = legend.get(c);
		for(Card i: deck) {
			if(i.getName().equals(cardName)) {
				return i;
			}
		}
		return new Card();
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
		this.playerConfigFile = plCfgFile;
		this.weaponConfigFile = wpCfgFile;
	}
	
	public void setConfigFiles(String bdCfgFile, String rmCfgFile) {
		this.boardConfigFile = bdCfgFile;
		this.roomConfigFile = rmCfgFile;
	}

	public Set<Card> getDeck(){
		return this.deck;
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	
	public void addPlayer(Player x){
		this.players.add(x);
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}
	
	public void clearTargets() {
		for(int row = 0; row<numRows; row++) {
			for(int column = 0; column<numColumns; column++) {
				gameBoard[row][column].setIsTarget(false);
			}
		}
	}
	
	public void setTargets() {
		for(int row = 0; row<numRows; row++) {
			for(int column = 0; column<numColumns; column++) {
				if(targets.contains(gameBoard[row][column])) {
					gameBoard[row][column].setIsTarget(true);
				}else {
					gameBoard[row][column].setIsTarget(false);
				}
			}
		}
	}
	
	
	// ---------------GUI FUNCTIONS--------------- //
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int row = 0; row<numRows; row++) {
			for(int col = 0; col<numColumns; col++) {
				gameBoard[row][col].draw(g,this);
			}
		}
		for(Player p: players) {
			p.draw(g);
		}
	}
	
	// ------------GAME LOGIC FUNCTIONS----------- //
	
	public void takeTurn() {
		boolean doRoll = true;
		if(currentPlayerIndex == 1) {
			players.get(0).updateLocation(this);
			if(!targets.contains(players.get(0).getCurrentCell())) {
				currentPlayerIndex = 0;
				displayError();
				doRoll = false;
			}else {
				if(players.get(0).getCurrentCell().isDoorway()) {
					GuessGUI guessGUI = new GuessGUI(this, getCardFromLegend(players.get(0).getCurrentCell().getInitial())); 
					handleSuggestion(Board.lastSentGuess, 0);
				}
			}
		}
		Player currentPlayer = players.get(currentPlayerIndex);
		
		//if previous player wasn't disproved next computer player makes an accusation
		if(makeAccusation && !currentPlayer.getIsHumanPlayer()) {
			if(checkAccusation(nextAccusation)){
				ClueGame.winMessage();
				System.exit(0);
			}
			else {
				ClueGame.loseMessage();
				System.exit(0);
			}
		}
		
		if(doRoll) {
			ClueGame.rollDie();
		}
		this.calcTargets(currentPlayer.getCurrentCell().getRow(), currentPlayer.getCurrentCell().getColumn(), ClueGame.getDieRoll());
		if(currentPlayer.getIsHumanPlayer()) {
			this.setTargets();
		}else {
			this.clearTargets();
		}
		currentPlayer.makeMove(this);
		repaint();
		ClueGame.update();
		//if computer player moves to a room
		if(!currentPlayer.getIsHumanPlayer() && (currentPlayer.getCurrentCell().isRoom() || currentPlayer.getCurrentCell().isDoorway())) {
			ComputerPlayer compPlayer = (ComputerPlayer) currentPlayer;
			Guess compPlayerGuess = new Guess();
			Card throwSuggestion = new Card();
			compPlayerGuess = compPlayer.createSuggestion(this, getCardFromLegend(compPlayer.getCurrentCell().getInitial()));
			throwSuggestion = this.handleSuggestion(compPlayerGuess, currentPlayerIndex);
			if(throwSuggestion == null) {
				makeAccusation = true;
				nextAccusation = compPlayerGuess;
			}else {
				makeAccusation = false;
			}
			ClueGame.showSuggestion(compPlayerGuess, throwSuggestion);
		}
		//if human player moves to a room
		if(currentPlayer.getIsHumanPlayer() && currentPlayer.getCurrentCell().isRoom()) {
			char roomInitial = currentPlayer.getCurrentCell().getInitial();
			String roomName = this.getName(roomInitial);
			Card currentRoom = new Card(roomName, CardType.ROOM);
			ClueGame.makeGuess(this, currentRoom);
		}
		
		currentPlayerIndex++;
		currentPlayerIndex %= players.size();
	}
	
	public BoardCell getCellClicked() {
		return cellClicked;
	}
	
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mouseClicked(MouseEvent event) {
		int column = event.getX()/CELL_SIZE;
		int row = event.getY()/CELL_SIZE;
		cellClicked = getCellAt(row, column);
	}
	
	private void displayError() {
		JOptionPane.showMessageDialog(null, "Please choose one of the highlighted targets.", "Error: Invalid Target", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sendGuess(Guess guess) {
		lastSentGuess = guess;
	}
	
	public static Guess getGuess() {
		return lastSentGuess;
	}
}
