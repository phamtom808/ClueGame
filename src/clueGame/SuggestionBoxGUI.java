package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SuggestionBoxGUI extends JPanel{
	
	public SuggestionBoxGUI() {
		
	}
	
	public SuggestionBoxGUI(Guess guess, Card suggestion, JFrame frame) {
		String guessString = guess.toString();
		String cardName = "";
		if(suggestion == null) {
			cardName = "No Card to Disprove";
		}else {
			cardName = suggestion.getName();
		}
		String toDisplay = guessString + ": " + cardName;
		JOptionPane.showMessageDialog(frame, cardName);
	}
	
}
