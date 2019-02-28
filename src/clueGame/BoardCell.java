package clueGame;
/*
 * Emily Christensen and Dane Pham
 * BoardCell Class: handles the individual cells of the clue board
 */


public class BoardCell {
	
	//Instance Variables: x (column), y (row)
	private int x;
	private int y;
	
	//BoardCell constructor
	public BoardCell(int x, int y) {
		this.x = x;
		this.y = y;
	}	
	
	//Getter for X 
	public int getX() {
		return this.x;
	}
	
	//Getter for Y
	public int getY() {
		return this.y;
	}
	
}
