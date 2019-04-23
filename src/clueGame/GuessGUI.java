package clueGame;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class GuessGUI extends JPanel{
	
	private JFrame guessHandler;
	private JPanel roomDisplay;
	private JComboBox<String> playerSelector;
	private JComboBox<String> weaponSelector;
	private JButton doneButton;
	private Guess suggestion;
	
	public GuessGUI(Board board, Card room) {
		guessHandler = new JFrame();
		guessHandler.setSize(400, 400);
		suggestion = new Guess();
		suggestion.setRoom(room);
		guessHandler.setLayout(new GridLayout(3,1));
		JPanel selectorRow = new JPanel();
		selectorRow.setLayout(new GridLayout(1,2));
		createRoomDisplay(room);
		createPlayerSelector(board);
		createWeaponSelector(board);
		createDoneButton();
		guessHandler.add(roomDisplay);
		selectorRow.add(playerSelector);
		selectorRow.add(weaponSelector);
		guessHandler.add(selectorRow);
		guessHandler.add(doneButton);
		guessHandler.setVisible(true);
	}
	
	public void createRoomDisplay(Card room) {
		roomDisplay = new JPanel();
		roomDisplay.setLayout(new GridLayout(1,2));
		JLabel yourRoom = new JLabel("Your Room");
		JTextField thisRoom = new JTextField();
		thisRoom.setText(room.getName());
		thisRoom.setEditable(false);
		roomDisplay.add(yourRoom);
		roomDisplay.add(thisRoom);
	}
	
	//Use dropdown menu to select
	public void createWeaponSelector(Board board) {
		weaponSelector = new JComboBox<String>();
		for(Card c: board.getDeck()) {
			if(c.getCardType() == CardType.WEAPON) {
				weaponSelector.addItem(c.getName());
			}
		}
	}
	
	public void createPlayerSelector(Board board) {
		playerSelector = new JComboBox<String>();
		for(Card c: board.getDeck()) {
			if(c.getCardType() == CardType.PLAYER) {
				playerSelector.addItem(c.getName());
			}
		}
	}
	
	public void createDoneButton() {
		doneButton = new JButton();
		doneButton.setText("Done");
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendGuessFromGUI();
				guessHandler.dispose();
			}
		});
	}
	
	public void sendGuessFromGUI() {
		Card player = new Card((String) playerSelector.getSelectedItem(), CardType.PLAYER);
		Card weapon = new Card((String) weaponSelector.getSelectedItem(), CardType.WEAPON);
		suggestion.setPlayer(player);
		suggestion.setWeapon(weapon);
		Board.sendGuess(suggestion);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
