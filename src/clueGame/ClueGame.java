package clueGame;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class ClueGame extends JFrame {
	
	private Board board;
	
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		
	}
}
