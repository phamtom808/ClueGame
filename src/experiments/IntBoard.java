package experiments;

import java.util.ArrayList;
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
	
	/*
	 * For all the cells in each column of gameboard
	 * 
	 */
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
	
	public void findAllTargets(BoardCell thisCell, int numSteps, HashSet<BoardCell> visitedCells, HashSet<BoardCell> targets){
		HashSet<BoardCell> adjacentCells = this.getAdjList(thisCell);
		for(BoardCell adjCell : adjacentCells) {
			for(BoardCell visited : visitedCells) {
				if(adjCell == visited) {
					continue;
				}
				else
				{
					visitedCells.add(adjCell);
					if(numSteps == 1) {
						targets.add(adjCell);
					} else {
						this.findAllTargets(adjCell, numSteps-1, visitedCells, targets);
					}
				}
			}
			visitedCells.remove(adjCell);
		}
	}
	
	public HashSet<BoardCell> calcTargets(BoardCell startCell, int pathLength){
		HashSet<BoardCell> targets= new HashSet<BoardCell>();
		HashSet<BoardCell> visitedCells = new HashSet<BoardCell>();
		
		visitedCells.add(startCell); //add startCell to the visited list
		
		findAllTargets(startCell, pathLength, targets, visitedCells);
		
		return targets;
	}
}
