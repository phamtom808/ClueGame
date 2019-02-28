package clueGame;

import java.util.HashSet;

/*
 * Emily Christensen and Dane Pham
 * BoardCell Class: handles the individual cells of the clue board
 */


public class BoardCell {
	
	//Instance Variables: x (column), y (row)
	private int x;
	private int y;
	private char initial;
	private HashSet<BoardCell> adjCells;
	
	
	//BoardCell constructor
	public BoardCell(int x, int y, char initial) {
		this.x = x;
		this.y = y;
		this.initial = initial;
		adjCells = new HashSet<BoardCell>();
	}	
	
	//Getter for X 
	public int getX() {
		return this.x;
	}
	
	//Getter for Y
	public int getY() {
		return this.y;
	}
	
	public boolean isDoorway() {
		return true;
	}
	
	public boolean isRoom() {
		return true;
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public void calcAdjCells() {
		return;
	}
	
	public HashSet<BoardCell> getAdjCells() {
		return this.adjCells;
	}
	
}
