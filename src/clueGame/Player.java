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
	
	public Player(String name, String color, int row, int column, Board thisBoard) throws BadConfigFormatException {
		this.hand = new HashSet<Card>();
		this.name = name;
		this.isHumanPlayer = false; //default to false since computer players more likely than human players
		try {
			this.setCell(row, column, thisBoard);
		}catch(BadConfigFormatException e) {
			throw e;
		}
		try {
			this.setColor(color);
		}catch (BadConfigFormatException e) {
			throw e;
		}
	}
	
	public void dealHand(Set<Card> dealtCards) {
		this.hand.addAll(dealtCards);
	}
	
	public void dealCard(Card c) {
		this.hand.add(c);
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
	
	public void setColor(String color) throws BadConfigFormatException {
		try {
			Field field = Class.forName("java.awt.Color").getField(color.trim());;
			this.color = (Color) field.get(null);
		}catch(Exception e) {
			this.color = null;
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
	
}

