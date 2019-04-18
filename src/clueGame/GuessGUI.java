package clueGame;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuessGUI extends JPanel{
	
	private JPanel textDisplay;
	private JPanel guessHandler;

	
	public GuessGUI() {
		
	}
	
	public void createRoom() {
		JPanel panel = new JPanel();
		panel.setLayout(gridLayout(1,2));
	}
}
