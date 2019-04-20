package clueGame;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String color, BoardCell thisCell) throws BadConfigFormatException {
		super(name,color,thisCell);
		this.setIsHuman(true);
	}
	
	@Override
	public void makeMove(Board thisBoard) {
		BoardCell cellClicked = null;
		while(!thisBoard.getTargets().contains(cellClicked)) {
			cellClicked = thisBoard.getCellClicked();
		}
		this.setCellFromCell(cellClicked);
		//at end of player turn, wipe targets
	}
	
	
	
}
