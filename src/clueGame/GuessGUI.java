package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuessGUI extends JPanel{
	
	private JPanel textDisplay;
	private JPanel guessHandler;
	private JPanel room;
	
	public GuessGUI() {
		
	}
	
	public void createRoom() {
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(1,2));
		JLabel yourRoom = new JLabel("Your room");
		
	}
}
