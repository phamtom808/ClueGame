package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccusationBoxGUI extends JFrame{
	
	
	private JFrame accusationHandler;
	private JPanel roomDisplay;
	private JPanel playerDisplay;
	private JPanel weaponDisplay;
	private JComboBox<String> playerSelector;
	private JComboBox<String> weaponSelector;
	private JComboBox<String> roomSelector;
	private JButton doneButton;
	private JButton cancelButton;
	private Guess suggestion;
	
	public AccusationBoxGUI(Board board) {
		accusationHandler = new JFrame();
		accusationHandler.setTitle("Make An Accusation");
		accusationHandler.setSize(400, 400);
		suggestion = new Guess();
		accusationHandler.setLayout(new GridLayout(4,1));
		JPanel selectorRow = new JPanel();
		selectorRow.setLayout(new GridLayout(1,2));
		createRoomSelector(board);
		createPlayerSelector(board);
		createWeaponSelector(board);
		createDoneButton();
		createCancelButton();
		selectorRow.add(doneButton);
		selectorRow.add(cancelButton);
		accusationHandler.add(roomDisplay);
		accusationHandler.add(playerDisplay);
		accusationHandler.add(weaponDisplay);
		accusationHandler.add(selectorRow);
		accusationHandler.setVisible(true);
	}
	
	public void createRoomSelector(Board board) {
		roomDisplay = new JPanel();
		roomDisplay.setLayout(new GridLayout(1,2));
		JLabel yourRoom = new JLabel("Room");
		roomSelector = new JComboBox<String>();
		for(Card c: board.getDeck()) {
			if(c.getCardType() == CardType.ROOM) {
				roomSelector.addItem(c.getName());
			}
		}
		roomDisplay.add(yourRoom);
		roomDisplay.add(roomSelector);
	}
	
	//Use dropdown menu to select
	public void createWeaponSelector(Board board) {
		weaponDisplay = new JPanel();
		weaponDisplay.setLayout(new GridLayout(1,2));
		JLabel yourWeapon = new JLabel("Weapon");
		weaponSelector = new JComboBox<String>();
		for(Card c: board.getDeck()) {
			if(c.getCardType() == CardType.WEAPON) {
				weaponSelector.addItem(c.getName());
			}
		}
		weaponDisplay.add(yourWeapon);
		weaponDisplay.add(weaponSelector);
	}
	
	public void createPlayerSelector(Board board) {
		playerDisplay = new JPanel();
		playerDisplay.setLayout(new GridLayout(1,2));
		JLabel yourPlayer = new JLabel("Person");
		playerSelector = new JComboBox<String>();
		for(Card c: board.getDeck()) {
			if(c.getCardType() == CardType.PLAYER) {
				playerSelector.addItem(c.getName());
			}
		}
		playerDisplay.add(yourPlayer);
		playerDisplay.add(playerSelector);
	}
	
	public void createDoneButton() {
		doneButton = new JButton();
		doneButton.setText("Submit");
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendGuessFromGUI();
				accusationHandler.dispose();
			}
		});
	}
	
	public void createCancelButton() {
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accusationHandler.dispose();
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

	
	public static void main(String args []) {
		JFrame frame = new JFrame();
		Board board;
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt","ClueWeapons.txt");
		board.initialize();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(500,200);
		AccusationBoxGUI gui = new AccusationBoxGUI(board);
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
}
