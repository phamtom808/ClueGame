package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<Card> cardsSeen;
	private Card lastRoomVisited;
	private boolean inRoom;
	private Random rand;
	
	public ComputerPlayer(String name, String color, int row, int column, Board thisBoard) throws BadConfigFormatException {
		super(name,color,thisBoard.getCellAt(row,column));
		lastRoomVisited = null;
		inRoom = false;
		rand = new Random();
		cardsSeen = new HashSet<Card>();
	}
	
	public ArrayList<Card> createSuggestion(Card room){
		
	}
	
	public void addCardSeen(Card c) {
		cardsSeen.add(c);
	}
	
	public BoardCell selectTarget(Board board) {
		Set<BoardCell> targets = board.getTargets();
		int spaceNum = rand.nextInt(targets.size());
		int i = 0;
		BoardCell targetCell = null;
		for(BoardCell b: targets) {
			if(b.isDoorway() && !board.getCardFromLegend(b.getInitial()).equals(lastRoomVisited)) {
				inRoom = true;
				lastRoomVisited = board.getCardFromLegend(b.getInitial());
				return b;
			}else if(inRoom) {
				inRoom = false;
			}else {
				lastRoomVisited = null;
			}
			if(i == spaceNum) {
				targetCell = b;
			}
			i++;
		}
		return targetCell;
	}
	
}
