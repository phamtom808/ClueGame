package clueGame;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Color;

public class ClueGame extends JFrame {
	
	private Board board;
	private JFrame game;
	public static final int FRAME_SIZE = 1200;
	
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		game = new JFrame();
		game.setLayout(new GridLayout(2,2));
		game.setSize(FRAME_SIZE, FRAME_SIZE);
		game.add(board);
		game.add(new DetectiveNotesGUI());
		game.add(new ControlGUI());
		game.add(new PlayerCardsGUI());
		game.setVisible(true);
	}
	
	public static void main(String args[]) {
		ClueGame thisGame = new ClueGame();
	}
}
