package clueGame;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String color, BoardCell thisCell) throws BadConfigFormatException {
		super(name,color,thisCell);
		this.setIsHuman(true);
	}
	
	@Override
	public void makeMove(Board thisBoard) {
		updateLocation(thisBoard);
	}
	
	@Override
	public void updateLocation(Board thisBoard) {
		if(thisBoard.getTargets().contains(thisBoard.getCellClicked())) {
			setCellFromCell(thisBoard.getCellClicked());
		}else {
			return;
		}
	}
}
