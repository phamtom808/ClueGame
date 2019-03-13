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
			if(thisBoard.getCellAt(row-1,column).isWalkway()) {
				this.adjCells.add(thisBoard.getCellAt(row-1,column));
			}
			if(thisBoard.getCellAt(row-1,column).isDoorway()) {
				if(thisBoard.getCellAt(row-1, column).getDoorNeighbor(thisBoard).equals(this)) {
					this.adjCells.add(thisBoard.getCellAt(row-1, column));
				}
			}
		}
		if(this.column > 0) {
			if(thisBoard.getCellAt(row,column-1).isWalkway()) {
				this.adjCells.add(thisBoard.getCellAt(row,column-1));
			}
			if(thisBoard.getCellAt(row,column-1).isDoorway()) {
				if(thisBoard.getCellAt(row, column-1).getDoorNeighbor(thisBoard).equals(this)) {
					this.adjCells.add(thisBoard.getCellAt(row, column-1));
				}
			}
		}
		if(this.row < thisBoard.getNumRows()-1) {
			if(thisBoard.getCellAt(row+1,column).isWalkway()) {
				this.adjCells.add(thisBoard.getCellAt(row+1,column));
			}
			if(thisBoard.getCellAt(row+1,column).isDoorway()) {
				if(thisBoard.getCellAt(row+1, column).getDoorNeighbor(thisBoard).equals(this)) {
					this.adjCells.add(thisBoard.getCellAt(row+1, column));
				}
			}
		}
		if(this.column < thisBoard.getNumColumns()-1) {
			if(thisBoard.getCellAt(row,column+1).isWalkway()) {
				this.adjCells.add(thisBoard.getCellAt(row,column+1));
			}
			if(thisBoard.getCellAt(row,column+1).isDoorway()) {
				if(thisBoard.getCellAt(row, column+1).getDoorNeighbor(thisBoard).equals(this)) {
					this.adjCells.add(thisBoard.getCellAt(row, column+1));
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
	
	public BoardCell getDoorNeighbor(Board thisBoard) {
		if(this.doorDirection == DoorDirection.LEFT) {
			return thisBoard.getCellAt(row, column-1);
		}else if(this.doorDirection == DoorDirection.RIGHT) {
			return thisBoard.getCellAt(row, column+1);
		}else if(this.doorDirection == DoorDirection.DOWN) {
			return thisBoard.getCellAt(row, column-1);
		}else if(this.doorDirection == DoorDirection.UP) {
			return thisBoard.getCellAt(row, column+1);
		}else {
			return null; //just in case a cell that is not a door is passed in
		}
	}
	
	public boolean equals(BoardCell other) {
		if(this == null || other == null) {
			return false;
		}
		if(this.row == other.getRow() && this.column == other.getColumn() && this.initial == other.getInitial() && this.doorDirection == other.getDoorDirection()) {
			return true;
		}
		return false;
	}
}
