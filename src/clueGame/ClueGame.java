package clueGame;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Random;
import java.awt.Color;

public class ClueGame extends JFrame {
	
	private static Board board;
	private static JFrame game;
	private static ControlGUI gameControl;
	private static int dieRoll = 1;
	private static Random dieRoller = new Random();
	public static final int FRAME_SIZE = 1200;
	public static final String BOARD_CONFIG_FILE = "BoardLayout.csv";
	public static final String ROOM_CONFIG_FILE = "ClueRooms.txt";
	public static final String PLAYER_CONFIG_FILE = "CluePlayers.txt";
	public static final String WEAPON_CONFIG_FILE = "ClueWeapons.txt";
	
	
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles(BOARD_CONFIG_FILE, ROOM_CONFIG_FILE);
		board.setDeckConfigFiles(PLAYER_CONFIG_FILE, WEAPON_CONFIG_FILE);
		board.initialize();
		game = new JFrame();
		gameControl = new ControlGUI();
		game.setLayout(new GridLayout(2,2));
		game.setSize(FRAME_SIZE, FRAME_SIZE);
		game.add(board);
		game.add(new DetectiveNotesGUI());
		game.add(gameControl);
		game.add(new DisplayGUI());
		game.setVisible(true);
	}
	
	public static void rollDie() {
		dieRoll = dieRoller.nextInt(6);
		dieRoll++;
	}
	
	public static int getDieRoll() {
		return dieRoll;
	}

	public static void takeTurn() {
		board.takeTurn();
	}

	public static Board getBoard() {
		return board;
	}
	
	public static void makeGuess(Board board, Card room) {
		game.add(new GuessGUI(board, room));
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
	
	public static void showSuggestion(Guess guess, Card suggestion) {
		game.add(new SuggestionBoxGUI(guess, suggestion,game));
	}
	
	public static void update() {
		Player currentPlayer = board.getCurrentPlayer();
		gameControl.updatePlayer(currentPlayer);
		gameControl.updateDieRoll(dieRoll);
		if(Board.getGuess().getPlayer() != null && Board.getGuess().getWeapon() != null && board.getGuess().getRoom() != null) {
			gameControl.updateLastGuess(board.getGuess());
		}
	}
	
}
