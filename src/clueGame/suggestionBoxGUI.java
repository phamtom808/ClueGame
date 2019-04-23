package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class suggestionBoxGUI extends JPanel{
	
	public suggestionBoxGUI() {
		
	}
	
	public suggestionBoxGUI(Card suggestion, JFrame frame) {
		String cardName = suggestion.getName();
		JOptionPane.showMessageDialog(frame, cardName);
	}
	
}
