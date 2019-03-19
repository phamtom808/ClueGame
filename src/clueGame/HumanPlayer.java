package clueGame;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String color, int row, int column, Board thisBoard) throws BadConfigFormatException {
		super(name,color,row,column,thisBoard);
		this.setIsHuman(true);
	}
}
