package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.*;

public abstract class Player {
	public String name;
	protected boolean didMove = false;
	private BoardCell currentCell;
	private Color color;
	private boolean isHumanPlayer;
	private Set<Card> hand;
	private boolean makeAccusation = false;
	
	public Player(String name, String color, BoardCell thisCell) throws BadConfigFormatException {
		this.hand = new HashSet<Card>();
		this.name = name;
		this.setCellFromCell(thisCell);
		this.isHumanPlayer = false; //default to false since computer players more likely than human players
		this.setCellFromCell(thisCell);
		try {
			this.setColor(color);
		}catch (BadConfigFormatException e) {
			this.color = Color.white; //uses white as a default color in case color fails to be read properly
		}
	}
	
	public void dealCard(Card c) {
		this.hand.add(c);
	}
	
	public Set<Card> getHand(){
		return this.hand;
	}
	
	public void setCell(int row, int column, Board thisBoard) throws BadConfigFormatException {
		try {
			this.currentCell = thisBoard.getCellAt(row, column);
		}catch(NullPointerException e) {
			throw new BadConfigFormatException("Location out of bounds on player: " + this.name);
		}
	}
	
	public void setIsHuman(boolean isHuman) {
		this.isHumanPlayer = isHuman;
	}
	
	public void setCellFromCell(BoardCell b) {
		this.currentCell = b;
	}
	
	public void setColor(String c) throws BadConfigFormatException {
		c = c.trim().toLowerCase();
		try {
			Field field = Class.forName("java.awt.Color").getField(c);
			this.color = (Color) field.get(null);
		}catch(Exception e) {
			this.color = null;
			e.printStackTrace();
			throw new BadConfigFormatException("Error: color not valid on player: " + this.name);
		}
	}
	
	public BoardCell getCurrentCell() {
		return this.currentCell;
	}
	
	public int getRow() {
		return this.currentCell.getRow();
	}
	
	public int getColumn() {
		return this.currentCell.getColumn();
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean getIsHumanPlayer() {
		return this.isHumanPlayer;
	}
	
	public Card disproveSuggestion(Guess suggestion) {
		Card player = suggestion.getPlayer();
		Card room = suggestion.getRoom();
		Card weapon = suggestion.getWeapon();
		if(this.hand.contains(suggestion.getRoom()) && this.hand.contains(suggestion.getWeapon()) && this.hand.contains(suggestion.getPlayer())) {
			Random rand = new Random();
			int randomChoice = rand.nextInt(3);
			switch(randomChoice) {
			case 1:
				return room;
			case 2:
				return player;
			default:
				return weapon;
			}
		}else if(this.hand.contains(player) && this.hand.contains(room)) {
			Random rand = new Random();
			int randomChoice = rand.nextInt(2);
			switch(randomChoice) {
			case 1:
				return player;
			default:
				return room;
			}
		}else if(this.hand.contains(player) && this.hand.contains(weapon)) {
			Random rand = new Random();
			int randomChoice = rand.nextInt(2);
			switch(randomChoice) {
			case 1:
				return player;
			default:
				return weapon;
			}
		}else if(this.hand.contains(room) && this.hand.contains(weapon)) {
			Random rand = new Random();
			int randomChoice = rand.nextInt(2);
			switch(randomChoice) {
			case 1:
				return room;
			default:
				return weapon;
			}
		}
		else if(this.hand.contains(player)) {
			return player;
		}else if(this.hand.contains(weapon)) {
			return weapon;
		}else if(this.hand.contains(room)) {
			return room;
		}else {
			return null;
		}
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval(this.currentCell.getColumn()*Board.CELL_SIZE, this.currentCell.getRow()*Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE);
	}

	//method must have body because player class is not abstract and I don't want to deal with MAKING it abstract
	public void makeMove(Board thisBoard) {
		return;
	}
	
	public boolean didMove() {
		return this.didMove;
	}
	
	//I don't know why it wants this, but at this point, I'm no longer asking...
	public void updateLocation(Board thisBoard) {
		return;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public void setMakeAccusation(boolean b) {
		makeAccusation = b;
	}
	
	public boolean doMakeAccusation() {
		return makeAccusation;
	}
	
	public void disproveHumanSuggestion(Guess suggestion) {
		return;
	}
	
	public Card getCardSelected() {
		return null;
	}
	
	public boolean wasCardSelected() {
		return false;
	}
}

