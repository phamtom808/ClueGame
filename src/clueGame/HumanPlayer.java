package clueGame;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String color, BoardCell thisCell) throws BadConfigFormatException {
		super(name,color,thisCell);
		this.setIsHuman(true);
	}
	
	@Override
	public void makeMove(Board thisBoard) {
		ClueGame.rollDie();
		int dieRoll = ClueGame.getDieRoll();
		thisBoard.calcTargets(getCurrentCell().getRow(), getCurrentCell().getColumn(), dieRoll);
		thisBoard.setTargets();
		
		
		//at end of player turn, wipe targets
		thisBoard.clearTargets();
	}
	
	
	
}
