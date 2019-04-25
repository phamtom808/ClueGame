package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class HumanPlayer extends Player {
	
	private Card cardSelected;
	private boolean cardWasSelected = false;
	
	public HumanPlayer(String name, String color, BoardCell thisCell) throws BadConfigFormatException {
		super(name,color,thisCell);
		this.setIsHuman(true);
		cardSelected = new Card();
	}
	
	@Override
	public void makeMove(Board thisBoard) {
		updateLocation(thisBoard);
	}
	
	@Override
	public void updateLocation(Board thisBoard) {
		if(thisBoard.getTargets().contains(thisBoard.getCellClicked())) {
			setCellFromCell(thisBoard.getCellClicked());
		}else {
			return;
		}
	}

	@Override
	public void disproveHumanSuggestion(Guess suggestion) {
		JFrame disproveBox = new JFrame();
		disproveBox.setSize(500,500);
		disproveBox.setLayout(new GridLayout(2,1));
		JComboBox<Card> cards = new JComboBox<Card>();
		JButton doneButton = new JButton();
		doneButton.setText("DONE");
		doneButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardSelected = (Card) cards.getSelectedItem();
				cardWasSelected = true;
				disproveBox.dispose();
			}
		});
		for(Card c: this.getHand()) {
			if(suggestion.getPlayer() == c ||suggestion.getWeapon() == c || suggestion.getRoom() == c) {
				cards.addItem(c);
			}
		}
		disproveBox.add(cards);
		disproveBox.add(doneButton);
	}
	
	@Override
	public Card getCardSelected() {
		if(cardWasSelected) {
			return cardSelected;
		}else {
			return null;
		}
	}
	
	public boolean wasCardSelected() {
		if(cardWasSelected) {
			cardWasSelected = false;
			return true;
		}
		return false;
	}
}
