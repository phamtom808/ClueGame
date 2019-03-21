package clueGame;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int column, Board thisBoard) throws BadConfigFormatException {
		super(name,color,thisBoard.getCellAt(row,column));
	}
}
