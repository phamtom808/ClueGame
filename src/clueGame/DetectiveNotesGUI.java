package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesGUI extends JPanel {

	public DetectiveNotesGUI() {
		setLayout(new GridLayout(3,2)); //3 rows, each with 2 panel elements
		add(this.createPersonGuessDisplay(), BorderLayout.EAST);
		add(this.createPeopleDisplay(), BorderLayout.WEST);
		add(this.createRoomGuessDisplay(), BorderLayout.EAST);
		add(this.createRoomDisplay(), BorderLayout.WEST);
		add(this.createWeaponGuessDisplay(), BorderLayout.EAST);
		add(this.createWeaponDisplay(), BorderLayout.WEST);
	}
	
	private JPanel createPersonGuessDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		String[] players = {"Miss Scarlet","Colonel Mustard","Mrs.White","Mr.Green","Professor Plum","Miss Peach"};
		JComboBox<String> cb = new JComboBox<String>(players);
		cb.setVisible(true);
		panel.add(cb, BorderLayout.CENTER);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		return panel;
	}
	
	private JPanel createPeopleDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
		String[] players = {"Miss Scarlet","Colonel Mustard","Mrs.White","Mr.Green","Professor Plum","Miss Peach"};
		
		for(int i = 0; i < players.length; i++) {
			JCheckBox box = new JCheckBox(players[i], false);
			checkboxes.add(box);
		}
		for(int i = 0; i < checkboxes.size(); i++) {
			panel.add(checkboxes.get(i));
		}
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return panel;
	}
	
	private JPanel createRoomGuessDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		String[] rooms = {"Conservatory","Kitchen","Bedroom","Theater","Living Room","Study","Pool","Garrage","Hall","Closet","Walkway"};
		JComboBox<String> cb = new JComboBox<String>(rooms);
		cb.setVisible(true);
		panel.add(cb, BorderLayout.CENTER);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		return panel;
	}
	
	private JPanel createRoomDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
		String[] rooms = {"Conservatory","Kitchen","Bedroom","Theater","Living Room","Study","Pool","Garrage","Hall","Closet","Walkway"};
		
		for(int i = 0; i < rooms.length; i++) {
			JCheckBox box = new JCheckBox(rooms[i], false);
			checkboxes.add(box);
		}
		for(int i = 0; i < checkboxes.size(); i++) {
			panel.add(checkboxes.get(i));
		}
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return panel;
	}
	
	private JPanel createWeaponGuessDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		String[] weapons = {"Candlestick","Revolver","Knife","Lead Pipe","Rope","Wrench"};
		JComboBox<String> cb = new JComboBox<String>(weapons);
		cb.setVisible(true);
		panel.add(cb, BorderLayout.CENTER);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		return panel;
	}
	
	private JPanel createWeaponDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
		String[] weapons = {"Candlestick","Revolver","Knife","Lead Pipe","Rope","Wrench"};
		
		for(int i = 0; i < weapons.length; i++) {
			JCheckBox box = new JCheckBox(weapons[i], false);
			checkboxes.add(box);
		}
		for(int i = 0; i < checkboxes.size(); i++) {
			panel.add(checkboxes.get(i));
		}
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		return panel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(500,600);
		DetectiveNotesGUI gui = new DetectiveNotesGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
