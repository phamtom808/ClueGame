package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DisplayGUI extends JPanel{
	
	private static Board board;
	
	public DisplayGUI() {
		
		splashScreen(); //calls the method to display the welcome message
		board = ClueGame.getBoard(); //creates game board for testing purposes
		setLayout(new GridLayout(6,1));
		displayHumanPlayerHand();
		
		this.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
	}

	
	private void splashScreen() {
		JOptionPane.showMessageDialog(null, "You are Miss Scarlet, press Next Player to begin play.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void displayHumanPlayerHand() {
		ArrayList<Player> players = new ArrayList<Player>();
		Set<Card> humanHand;
		players = board.getPlayers(); //get list of players from board
		Player humanPlayer = players.get(0); //get the human player from the list of players 
		humanHand = humanPlayer.getHand(); //get the human's hand from the human player
		for(Card x : humanHand) {
			if(x.getCardType() == CardType.PLAYER) {
				add(this.createPeopleCardDisplay(x));
			}else if(x.getCardType() == CardType.ROOM) {
				add(this.createRoomsCardDisplay(x));
			}else {
				add(this.createWeaponsCardDisplay(x));
			}
		}
		System.out.println(humanHand.size());
	}
	
	private JPanel createPeopleCardDisplay(Card x) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		String name = x.getName();
		JTextField peopleCard = new JTextField(name); //placeholder to make sure this works
		/* 
		Have to get player name(card) from Human Player and assign it to peopleCard
		String playerName = player.getName() 
		*/
		peopleCard.setEditable(false);
		panel.add(peopleCard);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		return panel;
	}
	
	private JPanel createRoomsCardDisplay(Card x) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		String name = x.getName();
		JTextField roomCard = new JTextField(name); //placeholder to make sure this works
		/* 
		Have to get room card from Human Player and assign it to roomCard
		String roomName = player.getCurrentCell() ??? 
		*/
		roomCard.setEditable(false);
		panel.add(roomCard);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		return panel;
	}
	
	private JPanel createWeaponsCardDisplay(Card x) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		String name = x.getName();
		JTextField weaponCard = new JTextField(name); //placeholder to make sure this works
		/* 
		Have to get weapon card from Human Player and assign it to weaponCard
		String weaponName = player.getWeapon() ???
		*/
		weaponCard.setEditable(false);
		panel.add(weaponCard);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		
		return panel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(100,500);
		DisplayGUI gui = new DisplayGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
