package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel{
	
	public ControlGUI() {
		setLayout(new GridLayout(3,1)); //3 rows, each with one panel element
		add(this.createTextDisplays());
		add(this.createButtons()); // bottom row contains the buttons
	}
	
	private JPanel createTextDisplays() {
		JPanel textDisplay = new JPanel();
		textDisplay.setLayout(new GridLayout(1,2)); //one row, 2 items: die roll and guess handler
		JPanel guessHandler = new JPanel(); 
		guessHandler.setLayout(new GridLayout(2,1)); //2 rows, 1 column: top is the guess, bottom is the result
		JTextField lastGuess = new JTextField();
		JTextField lastGuessResult = new JTextField();
		JTextField dieRoll = new JTextField();
	}
	
	private JPanel createButtons() {
		JPanel buttonMenu = new JPanel();
		buttonMenu.setLayout(new GridLayout(1,2));
		JButton makeAccusation = new JButton("Make Accusation");
		JButton nextPlayer = new JButton("Next Player");
		buttonMenu.add(makeAccusation);
		buttonMenu.add(nextPlayer);
		return buttonMenu;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(500,200);
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public String getStringGuess() {
		return "It was: "; 
	}
}
