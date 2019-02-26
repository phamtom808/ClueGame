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
	private ArrayList<ArrayList<BoardCell>> gameBoard;
	private HashSet<BoardCell> targets;
	
	public IntBoard() {
		gameBoard = new ArrayList<ArrayList<BoardCell>>();
		for(int i = 0; i<4; i++) {
			ArrayList<BoardCell> column = new ArrayList<BoardCell>();
			for(int j = 0; j<4; j++) {
				BoardCell b = new BoardCell(i,j);
				column.add(b);
			}
		}
		this.adjMatrix = new HashMap<BoardCell, HashSet<BoardCell>>();
		this.targets = new HashSet<BoardCell>();
		this.calcAdjacencies();
	}
	
	public BoardCell getCell(int x, int y) {
		ArrayList<BoardCell> column = this.gameBoard.get(x);
		BoardCell b = column.get(y);
		return b;
	}
	
	public void editCell(int x, int y, BoardCell cell) {
		ArrayList<BoardCell> column = this.gameBoard.get(x);
		column.add(y,cell);
		this.gameBoard.add(x,column);
	}
	
	public HashSet<BoardCell> getAdjList(BoardCell cell){
		HashSet<BoardCell> adjCells = new HashSet<BoardCell>();
		if(cell.getX() > 0) {
			adjCells.add(this.getCell(cell.getX()-1,cell.getY()));
		}
		if(cell.getX() < gameBoard.get(0).size()-1) {
			adjCells.add(this.getCell(cell.getX()+1, cell.getY()));
		}
		if(cell.getY() > 0) {
			adjCells.add(this.getCell(cell.getX(), cell.getY()-1));
		}
		if(cell.getY() < gameBoard.size()-1) {
			adjCells.add(this.getCell(cell.getX(), cell.getY()+1));
		}
		return adjCells;
	}
	
	/*
	 * For all the cells in each column of gameboard
	 * 
	 */
	public void calcAdjacencies() {
		for(int i = 0; i<gameBoard.size(); i++) {
			for(int j = 0; j<gameBoard.size(); j++) {
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
		this.targets= new HashSet<BoardCell>();
		HashSet<BoardCell> visitedCells = new HashSet<BoardCell>();
		
		visitedCells.add(startCell); //add startCell to the visited list
		
		findAllTargets(startCell, pathLength, targets, visitedCells);
		
		return targets;
	}
}
