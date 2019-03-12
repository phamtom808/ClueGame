package clueGame;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Emily Christensen
 * @author Dane Pham
 * BoardCell Class: handles the individual cells of the clue board
 */


public class BoardCell {
	
	//Instance Variables: x (column), y (row)
	private int x;
	private int y;
	private Character initial;
	private HashSet<BoardCell> adjCells;
	private DoorDirection doorDirection;
	
	
	//BoardCell constructor
	public BoardCell(int x, int y, Character initial) {
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
	
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}
	
	public char getInitial() {
		return this.initial;
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
	
	/*
	 * calcAdjCells(Board thisBoard)
	 * takes in board parameter in order to get the list of doors, size of board
	 * if the cell is a walkway, determine the cells that are adjacent to it
	 * otherwise, determine what, if any, doors are available
	 * this is called on all cells within board class
	 * 
	 */
	public void calcAdjCells(Board thisBoard) {
		if(this.initial != 'W') {
			for(BoardCell i: thisBoard.getDoorList()) {
				if(i.getInitial() == this.initial) {
					if(i.getDoorDirection() == DoorDirection.RIGHT) {
						this.adjCells.add(thisBoard.getCellAt(i.getX()+1, i.getY()));
					}else if(i.getDoorDirection() == DoorDirection.LEFT) {
						this.adjCells.add(thisBoard.getCellAt(i.getX()-1,  i.getY()));
					}else if(i.getDoorDirection() == DoorDirection.UP) {
						this.adjCells.add(thisBoard.getCellAt(i.getX(),  i.getY()-1));
					}else if(i.getDoorDirection() == DoorDirection.DOWN) {
						this.adjCells.add(thisBoard.getCellAt(i.getX(),  i.getY()+1));
					}
				}
			}
			return;
		}
		if(this.x > 0) {
			this.adjCells.add(thisBoard.getCellAt(x-1, y));
		}
		if(this.y > 0) {
			this.adjCells.add(thisBoard.getCellAt(x, y-1));
		}
		if(this.x < thisBoard.getNumRows()) {
			this.adjCells.add(thisBoard.getCellAt(x+1, y));
		}
		if(this.y < thisBoard.getNumColumns()) {
			this.adjCells.add(thisBoard.getCellAt(x, y+1));
		}
	}
	
	public Set<BoardCell> getAdjCells() {
		return this.adjCells;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	public void setInitial(Character i) {
		this.initial = i;
	}
	
}
