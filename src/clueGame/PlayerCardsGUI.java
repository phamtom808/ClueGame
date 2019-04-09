package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerCardsGUI extends JPanel{
	public PlayerCardsGUI() {
		setLayout(new GridLayout(6,1));
		add(this.createPeopleCardDisplay());
		add(this.createRoomsCardDisplay());
		add(this.createWeaponsCardDisplay());
	}

	private JPanel createPeopleCardDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JTextField peopleCard = new JTextField("Miss Scarlet"); //placeholder to make sure this works
		/* 
		Have to get player name(card) from Human Player and assign it to peopleCard
		String playerName = player.getName() 
		*/
		peopleCard.setEditable(false);
		panel.add(peopleCard);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		return panel;
	}

	private JPanel createRoomsCardDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JTextField roomCard = new JTextField("Conservatory"); //placeholder to make sure this works
		/* 
		Have to get room card from Human Player and assign it to roomCard
		String roomName = player.getCurrentCell() ??? 
		*/
		roomCard.setEditable(false);
		panel.add(roomCard);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		return panel;
	}
	
	private JPanel createWeaponsCardDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JTextField weaponCard = new JTextField("Candlestick"); //placeholder to make sure this works
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
	frame.setSize(70,600);
	PlayerCardsGUI gui = new PlayerCardsGUI();
	frame.add(gui, BorderLayout.CENTER);
	frame.setVisible(true);
}
}