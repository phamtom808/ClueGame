package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel{
	
	private JTextField dieRoll;
	private JTextField myGuess;
	private JPanel textDisplay;
	private JPanel guessHandler;
	private JTextField lastGuess;
	private JTextField lastGuessResult;
	
	public ControlGUI(JFrame framePass) {
		JFrame frame = framePass;
		setLayout(new GridLayout(3,1)); //3 rows, each with one panel element
		createTextDisplays();
		add(textDisplay);
		add(guessHandler);
		add(this.createButtons(frame)); // bottom row contains the buttons
	}
	
	private void createTextDisplays() {
		textDisplay = new JPanel();
		textDisplay.setLayout(new GridLayout(1,2)); //one row, 2 items: die roll and guess handler
		myGuess = new JTextField(10);
		dieRoll = new JTextField(3);
		myGuess.setEditable(false);
		dieRoll.setEditable(false);
		JLabel rollLabel = new JLabel("Die Roll");
		JLabel guessLabel = new JLabel("Previous Guess");
		
		textDisplay.add(rollLabel, BorderLayout.WEST);
		textDisplay.add(dieRoll, BorderLayout.EAST);
		textDisplay.setBorder(new TitledBorder(new EtchedBorder(), "Die and Last Guess"));
		textDisplay.add(guessLabel, BorderLayout.WEST);
		textDisplay.add(myGuess);
	}
	
	private void createGuessHandler() {
		guessHandler = new JPanel(); 
		guessHandler.setLayout(new GridLayout(2,1)); //2 rows, 1 column: top is the guess, bottom is the result
		lastGuess = new JTextField();
		lastGuessResult = new JTextField();
		lastGuessResult.setEditable(false);
		lastGuess.setEditable(false);
		JLabel lastGuessLabel = new JLabel ("My Guess");
		JLabel lastGuessResultLabel = new JLabel ("Result");
		guessHandler.add(lastGuessLabel, BorderLayout.WEST);
		guessHandler.add(lastGuess, BorderLayout.EAST);
		guessHandler.add(lastGuessResultLabel);
		guessHandler.add(lastGuessResult);
	}
	
	private JPanel createButtons(JFrame framePass) {
		JFrame frame = framePass;
		JPanel buttonMenu = new JPanel();//creates the action buttons on the bottom
		buttonMenu.setLayout(new GridLayout(1,2));
		JButton makeAccusation = new JButton("Make Accusation");
		JButton nextPlayer = new JButton("Next Player");
		buttonMenu.add(makeAccusation);
		buttonMenu.add(nextPlayer);
		nextPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "This hopefully will work...");
			}
		});
		
		return buttonMenu;
	}
	
	public void updateDieRoll(Integer roll) {
		dieRoll.setText(roll.toString());
	}
	
	public void updateMyGuess(Guess guess) {
		myGuess.setText(guess.toString());
	}
	
	public void updateLastGuess(Guess guess) {
		lastGuess.setText(guess.toString());
	}
	
	public void updateLastGuessResult(Boolean result) {
		lastGuessResult.setText(result.toString());
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(500,200);
		ControlGUI gui = new ControlGUI(frame);
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
}
