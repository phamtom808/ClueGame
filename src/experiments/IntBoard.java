package experiments;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class IntBoard {
	
	private Map<BoardCell, HashSet<BoardCell>> adjMatrix; 
	private BoardCell[][] gameBoard;
	private HashSet<BoardCell> targets;
	
	public IntBoard() {
		this.gameBoard = new BoardCell[4][4];
		this.adjMatrix = new HashMap<BoardCell, HashSet<BoardCell>>();
		this.targets = new HashSet<BoardCell>();
	}
	
	public HashSet<BoardCell> getAdjList(BoardCell cell){
		HashSet<BoardCell> adjCells = new HashSet<BoardCell>();
		return adjCells;
	}
	
	public void calcAdjacencies() {
		return;
	}
	
	public HashSet<BoardCell> getTargets(){
		return this.targets;
	}
	
	public HashSet<BoardCell> calcTargets(BoardCell startCell, int pathLength){
		HashSet<BoardCell> targets= new HashSet<BoardCell>();
		return targets;
	}
}
