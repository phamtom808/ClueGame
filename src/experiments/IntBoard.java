package experiments;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * Emily Christensen and Dane Pham
 * IntBoard Class: Handles the entire board, adjancies for each cell, possible targets for a given roll
 */

public class IntBoard {
	
	//Instance Variables
	private Map<BoardCell, HashSet<BoardCell>> adjMatrix; 
	private BoardCell[][] gameBoard;
	private HashSet<BoardCell> targets;
	
	public IntBoard() {
		this.gameBoard = new BoardCell[4][4];
		this.adjMatrix = new HashMap<BoardCell, HashSet<BoardCell>>();
		this.targets = new HashSet<BoardCell>();
	}
	
	public BoardCell getCell(int x, int y) {
		return gameBoard[x][y];
	}
	
	public HashSet<BoardCell> getAdjList(BoardCell cell){
		HashSet<BoardCell> adjCells = new HashSet<BoardCell>();
		if(cell.getX() > 0) {
			adjCells.add(this.getCell(cell.getX()-1,cell.getY()));
		}
		if(cell.getX() < gameBoard[0].length-1) {
			adjCells.add(this.getCell(cell.getX()+1, cell.getY()));
		}
		if(cell.getY() > 0) {
			adjCells.add(this.getCell(cell.getX(), cell.getY()-1));
		}
		if(cell.getY() < gameBoard.length-1) {
			adjCells.add(this.getCell(cell.getX(), cell.getY()+1));
		}
		return adjCells;
	}
	
	public void calcAdjacencies() {
		for(int i = 0; i<gameBoard.length; i++) {
			for(int j = 0; j<gameBoard.length; j++) {
				adjMatrix.put(this.getCell(i, j),this.getAdjList(this.getCell(i, j)));
			}
		}
	}
	
	public HashSet<BoardCell> getTargets(){
		return this.targets;
	}
	
	public HashSet<BoardCell> calcTargets(BoardCell startCell, int pathLength){
		HashSet<BoardCell> targets= new HashSet<BoardCell>();
		return targets;
	}
}
