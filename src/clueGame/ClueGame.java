package clueGame;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Random;
import java.awt.Color;

public class ClueGame extends JFrame {
	
	private Board board;
	private JFrame game;
	private static int dieRoll;
	private static Random dieRoller = new Random();
	public static final int FRAME_SIZE = 1200;
	
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		game = new JFrame();
		ControlGUI gameControl = new ControlGUI(game);
		game.setLayout(new GridLayout(2,2));
		game.setSize(FRAME_SIZE, FRAME_SIZE);
		game.add(board);
		game.add(new DetectiveNotesGUI());
		game.add(gameControl);
		game.add(new DisplayGUI());
		game.setVisible(true);
	}
	
	public static void main(String args[]) {
		ClueGame thisGame = new ClueGame();
		thisGame.playGame();
	}
	
	public static void rollDie() {
		dieRoll = dieRoller.nextInt(6) + 1;
	}
	
	public static int getDieRoll() {
		return dieRoll;
	}
	
	public void playGame() {
		board.playGame();
	}
}
