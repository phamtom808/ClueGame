package clueGame;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String color, int row, int column, Board thisBoard) throws BadConfigFormatException {
		super(name,color,thisBoard.getCellAt(row, column));
		this.setIsHuman(true);
	}
	
	public void makeMove(Board thisBoard) {
		
		thisBoard.setTargets();
		
	}
	
}
