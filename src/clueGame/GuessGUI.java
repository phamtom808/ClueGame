package clueGame;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuessGUI extends JPanel{
	
	private JPanel textDisplay;
	private JPanel guessHandler;

	
	public GuessGUI() {
		
	}
	
	public void createRoom() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
	}
}
