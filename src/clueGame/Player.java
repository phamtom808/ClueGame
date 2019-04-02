package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Player {
	public String name;
	private BoardCell currentCell;
	private Color color;
	private boolean isHumanPlayer;
	private Set<Card> hand;
	
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
	
	public Card disproveSuggestion(Card player, Card weapon, Card room) {
		if(this.hand.contains(player)) {
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
}

