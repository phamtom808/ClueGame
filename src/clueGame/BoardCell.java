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
	private DoorDirection doorDirection;
	
	
	//BoardCell constructor
	public BoardCell(int x, int y, char initial) {
		this.x = x;
		this.y = y;
		this.initial = initial;
		adjCells = new HashSet<BoardCell>();
		doorDirection = DoorDirection.NONE;
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
		if(this.doorDirection != DoorDirection.NONE) {
			return true;
		}
		return false;
	}
	
	public boolean isRoom() {
		if(this.initial != 'W') {
			return true;
		}
		return false;
	}
	
	public boolean isWalkway() {
		if(this.initial == 'W') {
			return true;
		}
		return false;
	}
	
	public void calcAdjCells(Board thisBoard) {
		if(this.x > 0) {
			adjCells.add(thisBoard.getCellAt(x-1, y));
		}
		if(this.y > 0) {
			adjCells.add(thisBoard.getCellAt(x, y-1));
		}
		if(this.x < thisBoard.numRows) {
			adjCells.add(thisBoard.getCellAt(x+1, y));
		}
		if(this.y < thisBoard.numColumns) {
			adjCells.add(thisBoard.getCellAt(x, y+1));
		}
		return;
	}
	
	public HashSet<BoardCell> getAdjCells() {
		return this.adjCells;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
}
