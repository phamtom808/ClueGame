package clueGame;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Emily Christensen
 * @author Dane Pham
 * BoardCell Class: handles the individual cells of the clue board
 */


public class BoardCell {
	
	//Instance Variables: 
	private int row;
	private int column;
	private Character initial;
	private HashSet<BoardCell> adjCells;
	private DoorDirection doorDirection;
	
	
	//BoardCell constructor
	public BoardCell(int row, int column, Character initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		adjCells = new HashSet<BoardCell>();
		doorDirection = DoorDirection.NONE;
	}	
	
	//Getter for row 
	public int getRow() {
		return this.row;
	}
	
	//Getter for column 
	public int getColumn() {
		return this.column;
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
		if(!this.isWalkway() && !this.isDoorway()) {
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
		if(this.isRoom()) {
			return;
		}
		if(this.isDoorway()) {
			if(this.doorDirection == DoorDirection.RIGHT) {
				this.adjCells.add(thisBoard.getCellAt(row, column+1));
			}else if(this.doorDirection == DoorDirection.LEFT) {
				this.adjCells.add(thisBoard.getCellAt(row, column-1));
			}else if(this.doorDirection == DoorDirection.UP) {
				this.adjCells.add(thisBoard.getCellAt(row-1, column));
			}else if(this.doorDirection == DoorDirection.DOWN) {
				this.adjCells.add(thisBoard.getCellAt(row+1,column));
			}
			return;
		}
		if(this.row > 0) {
			BoardCell i = thisBoard.getCellAt(row-1, column);
			if(i.isWalkway()) {
				this.adjCells.add(i);
			}else if(i.isDoorway()) {
				if(i.getDoorDirection() == DoorDirection.RIGHT) {
					this.adjCells.add(i);
				}
			}
		}
		if(this.column > 0) {
			BoardCell i = thisBoard.getCellAt(row, column-1);
			if(i.isWalkway()) {
				this.adjCells.add(i);
			}else if(i.isDoorway()) {
				if(i.getDoorDirection() == DoorDirection.DOWN) {
					this.adjCells.add(i);
				}
			}
		}
		if(this.row < thisBoard.getNumRows()-1) {
			BoardCell i = thisBoard.getCellAt(row+1,column);
			if(i.isWalkway()) {
				this.adjCells.add(i);
			}else if(i.isDoorway()) {
				if(i.getDoorDirection() == DoorDirection.LEFT) {
					this.adjCells.add(i);
				}
			}
		}
		if(this.column < thisBoard.getNumColumns()-1) {
			BoardCell i = thisBoard.getCellAt(row,column+1);
			if(i.isWalkway()) {
				this.adjCells.add(i);
			}else if(i.isDoorway()) {
				if(i.getDoorDirection() == DoorDirection.UP) {
					this.adjCells.add(i);
				}
			}
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
