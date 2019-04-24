package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class GuessGUI extends JPanel{
	
	private JFrame guessHandler;
	private JPanel roomDisplay;
	private JPanel playerDisplay;
	private JPanel weaponDisplay;
	private JComboBox<String> playerSelector;
	private JComboBox<String> weaponSelector;
	private JButton doneButton;
	private JButton cancelButton;
	private Guess suggestion;
	
	public GuessGUI(Board board, Card room) {
		guessHandler = new JFrame();
		guessHandler.setSize(400, 400);
		guessHandler.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //doesn't quit window unless submit is clicked
		suggestion = new Guess();
		suggestion.setRoom(room);
		guessHandler.setLayout(new GridLayout(4,1));
		JPanel selectorRow = new JPanel();
		selectorRow.setLayout(new GridLayout(1,2));
		createRoomDisplay(room);
		createPlayerSelector(board);
		createWeaponSelector(board);
		createDoneButton();
		createCancelButton();
		selectorRow.add(doneButton);
		selectorRow.add(cancelButton);
		guessHandler.add(roomDisplay);
		guessHandler.add(playerDisplay);
		guessHandler.add(weaponDisplay);
		guessHandler.add(selectorRow);
		guessHandler.setVisible(true);
		//shows player dialog telling them they cannot exit
		guessHandler.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	JOptionPane.showMessageDialog(guessHandler, "Cannot Exit. Player must make a guess.");
		    }
		});
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
				guessHandler.dispose();
			}
		});
	}
	
	public void createCancelButton() {
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(guessHandler, "Cannot Exit. Player must make a guess.");
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
	
	public static void main(String args []) {
		JFrame frame = new JFrame();
		Board board;
		Card card = new Card("Kitchen" , CardType.ROOM);
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setDeckConfigFiles("CluePlayers.txt","ClueWeapons.txt");
		board.initialize();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(500,200);
		GuessGUI gui = new GuessGUI(board, card);
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
