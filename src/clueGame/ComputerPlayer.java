package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Card lastRoomVisited;
	private boolean inRoom;
	private Random rand;
	
	public ComputerPlayer(String name, String color, BoardCell thisCell) throws BadConfigFormatException {
		super(name,color,thisCell);
		lastRoomVisited = null;
		inRoom = false;
		rand = new Random();
		didMove = true;
	}
	

	public Guess createSuggestion(Board board, Card room){
		Guess suggestion = new Guess();
		suggestion.setRoom(room);
		Set<Card> deck = board.getDeck();
		deck.removeAll(this.getHand());
		deck.removeAll(board.getCardsSeen());
		Set<Card> playerCards = new HashSet<Card>();
		Set<Card> weaponCards = new HashSet<Card>();
		for(Card c: deck) {
			if(c.getCardType() == CardType.PLAYER) {
				playerCards.add(c);
			}else if(c.getCardType() == CardType.WEAPON) {
				weaponCards.add(c);
			}
		}
		int i = 0;
		int randomCard = rand.nextInt(playerCards.size());
		for(Card c: playerCards) {
			if(i == randomCard) {
				suggestion.setPlayer(c); 
			}
			i++;
		}
		int j = 0;
		int randomCard2 = rand.nextInt(weaponCards.size());
		for(Card c: weaponCards) {
			if(j == randomCard2) {
				suggestion.setWeapon(c);
			}
			j++;
		}
		Board.sendGuess(suggestion);
		return suggestion;
	}
	
	public BoardCell selectTarget(Board board) {
		Set<BoardCell> targets = board.getTargets();
		int spaceNum = rand.nextInt(targets.size());
		int i = 0;
		BoardCell targetCell = null;
		for(BoardCell b: targets) {
			if(!(b == null)) {
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
		}
		return targetCell;
	}
	
	public Card getLastRoomVisited() {
		return lastRoomVisited;
	}
	
	@Override
	public void makeMove(Board thisBoard) {
		BoardCell targetCell = selectTarget(thisBoard);
		if(targetCell != null) {
			this.setCellFromCell(targetCell);
		}
	}
}

