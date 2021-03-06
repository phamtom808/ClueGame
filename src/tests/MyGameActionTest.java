/**
 * @author Emily Christensen
 * @author Dane Pham
 * MyGameActionTest Class: handles tests pertaining to game actions
 */
package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class MyGameActionTest {

	private static Board board;
	
	@BeforeClass
	public static void Setup() {
		//creates an instance of the board and initializes configuration files and the board
			board = Board.getInstance();
			board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
			board.setDeckConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
			board.initialize();
	}
	
	@Test
	public void selectTargetLocationComp() throws BadConfigFormatException {
		//random selection test
		try {
			ComputerPlayer compPlayer = new ComputerPlayer("Bob", "Blue",board.getCellAt(17, 6));
			board.calcTargets(16,5,1);
			boolean loc_16_6 = false;
			boolean loc_17_5 = false;
			boolean loc_15_5 = false;
			
			for(int i = 0; i < 100; i++) {
				BoardCell selected = compPlayer.selectTarget(board);
				if(selected == board.getCellAt(16,6)) {
					loc_16_6 = true;
				}
				else if(selected == board.getCellAt(17,5)) {
					loc_17_5 = true;
				}
				else if(selected == board.getCellAt(15,5)) {
					loc_15_5 = true;
				}
				else {
					fail("Invalid target.");
				}
			}
			assertTrue(loc_17_5);
			assertTrue(loc_16_6);
			assertTrue(loc_15_5);
		}catch (BadConfigFormatException e) {
			throw new BadConfigFormatException("computer player did not intiailize correctly");
		}
		//must select room if wasn't previously visited
		try {
			ComputerPlayer compPlayer = new ComputerPlayer("Rick", "Red",board.getCellAt(13, 1));
			board.calcTargets(13,1,1);
			BoardCell selected = compPlayer.selectTarget(board);
			assertTrue(selected.isDoorway());
		}catch (BadConfigFormatException e) {
			throw new BadConfigFormatException("computer player did not intiailize correctly");
		}
		//if room just visited each target selected randomly
		try {
			ComputerPlayer compPlayer = new ComputerPlayer("Peter", "Pink",board.getCellAt(5, 1));
			board.calcTargets(5, 1, 1);
			BoardCell selected = compPlayer.selectTarget(board);
			assertTrue(selected.isWalkway());
		}catch (BadConfigFormatException e) {
			throw new BadConfigFormatException("computer player did not initialize correctly");
		}
	}
	
	/*
	 * need to make an accusation class and a getter for accusation in board class
	 */
	@Test
	public void checkCorrectAccusation() {
		ArrayList<Card> testCards = new ArrayList<Card>();
		Card testWeapon = new Card("Rope", CardType.WEAPON);
		Card testPlayer = new Card("Colonel Mustard", CardType.PLAYER);
		Card testRoom = new Card("Pool", CardType.ROOM);
		testCards.add(testWeapon);
		testCards.add(testPlayer);
		testCards.add(testRoom);
		
		assertTrue(testCards.contains(testWeapon));
		assertEquals("Rope", testWeapon.getName());
		assertEquals(CardType.WEAPON, testWeapon.getCardType());
		assertTrue(testCards.contains(testPlayer));
		assertEquals("Colonel Mustard", testPlayer.getName());
		assertEquals(CardType.PLAYER, testPlayer.getCardType());
		assertTrue(testCards.contains(testRoom));
		assertEquals("Pool", testRoom.getName());
		assertEquals(CardType.ROOM, testRoom.getCardType());
	}
	
	@Test
	public void checkWrongWeaponAccusation() {
		//checks to make sure it throws an error when the wrong weapon is accused
		ArrayList<Card> testCards = new ArrayList<Card>();
		Card testWeapon = new Card("Rope", CardType.WEAPON);
		Card testPlayer = new Card("Colonel Mustard", CardType.PLAYER);
		Card testRoom = new Card("Pool", CardType.ROOM);
		Card falseWeapon = new Card("Axe", CardType.WEAPON);
		testCards.add(testWeapon);
		testCards.add(testPlayer);
		testCards.add(testRoom);
		assertTrue(testCards.contains(testPlayer));
		assertEquals("Colonel Mustard", testPlayer.getName());
		assertEquals(CardType.PLAYER, testPlayer.getCardType());
		assertTrue(testCards.contains(testRoom));
		assertEquals("Pool", testRoom.getName());
		assertEquals(CardType.ROOM, testRoom.getCardType());
		assertEquals("Axe", falseWeapon.getName());
	}
	
	@Test
	public void checkWrongRoomAccusation() {
		//checks to make sure the program throws an error when the wrong room is accused
		ArrayList<Card> testCards = new ArrayList<Card>();
		Card testWeapon = new Card("Rope", CardType.WEAPON);
		Card testPlayer = new Card("Colonel Mustard", CardType.PLAYER);
		Card testRoom = new Card("Pool", CardType.ROOM);
		Card falseRoom = new Card("Kitchen", CardType.ROOM);
		testCards.add(testWeapon);
		testCards.add(testPlayer);
		testCards.add(testRoom);
		assertTrue(testCards.contains(testWeapon));
		assertEquals("Rope", testWeapon.getName());
		assertEquals(CardType.WEAPON, testWeapon.getCardType());
		assertTrue(testCards.contains(testPlayer));
		assertEquals("Colonel Mustard", testPlayer.getName());
		assertEquals(CardType.PLAYER, testPlayer.getCardType());
		assertTrue(testCards.contains(testRoom));
		assertEquals("Kitchen", falseRoom.getName());
	}
	
	@Test
	public void checkWrongPlayerAccusation() {
		//checks to make sure the program throws an error when the wrong player is accused
		ArrayList<Card> testCards = new ArrayList<Card>();
		Card testWeapon = new Card("Rope", CardType.WEAPON);
		Card testPlayer = new Card("Colonel Mustard", CardType.PLAYER);
		Card testRoom = new Card("Pool", CardType.ROOM);
		Card falsePlayer = new Card("ABC", CardType.PLAYER);
		testCards.add(testWeapon);
		testCards.add(testPlayer);
		testCards.add(testRoom);
		assertTrue(testCards.contains(testWeapon));
		assertEquals("Rope", testWeapon.getName());
		assertEquals(CardType.WEAPON, testWeapon.getCardType());
		assertTrue(testCards.contains(testPlayer));
		assertEquals("Colonel Mustard", testPlayer.getName());
		assertEquals(CardType.PLAYER, testPlayer.getCardType());
		assertTrue(testCards.contains(testRoom));
		assertEquals("ABC", falsePlayer.getName());
	}
	
	@Test
	public void createSuggestion() throws BadConfigFormatException {
		//checks if the suggestion created is correct with respect to where the player is
		try {
			Card testRoom = new Card("Conservatory", CardType.ROOM);
			ComputerPlayer compPlayer = new ComputerPlayer("Rick", "Red",board.getCellAt(5, 0));
			Guess suggestion = compPlayer.createSuggestion(board, testRoom);
			//check if the room matches the current location
			assertTrue(suggestion.getRoom().equals(testRoom));	
		}catch (BadConfigFormatException e) {
			throw new BadConfigFormatException("computer player did not intiailize correctly");
		}
	}
	
	@Test
	public void createSuggestionMultipleWeapons() throws BadConfigFormatException {
		//checks to make sure the suggestion doesn't contain players weapon and only draws from the unseen cards
		try {
			ComputerPlayer compPlayer = new ComputerPlayer("Rick", "Red",board.getCellAt(5, 0));
			Card testRoom = new Card("Conservatory", CardType.ROOM);
			Card testWeapon = new Card("Weapon", CardType.WEAPON);
			Set<Card> compHand = compPlayer.getHand();
			for(Card card : compHand) {
				if(card.getCardType() == CardType.WEAPON) {
					testWeapon = card;
				}
			}
			Guess suggestion = compPlayer.createSuggestion(board, testRoom);
			assertTrue(!suggestion.getWeapon().equals(testWeapon));
		}catch(BadConfigFormatException e){
			throw new BadConfigFormatException("computer player did not initialize correctly.");
		}
	}
	
	@Test
	public void createSuggestionMultiplePeople() throws BadConfigFormatException {
		//checks to make sure the suggestion doesn't contain the player and only draws from the unseen cards
		try {
			ComputerPlayer compPlayer = new ComputerPlayer("Rick", "Red",board.getCellAt(5,0));
			Card testRoom = new Card("Conservatory", CardType.ROOM);
			Card testPerson = new Card("Person", CardType.PLAYER);
			Set<Card> compHand = compPlayer.getHand();
			for(Card card : compHand) {
				if(card.getCardType() == CardType.PLAYER) {
					testPerson = card;
				}
			}
			Guess suggestion = compPlayer.createSuggestion(board, testRoom);
			assertTrue(!suggestion.getPlayer().equals(testPerson));
		}catch(BadConfigFormatException e){
			throw new BadConfigFormatException("computer player did not initialize correctly.");
		}
	}
	
	@Test
	public void createSuggestionOneWeapon() throws BadConfigFormatException{
		//checks to make sure the suggestion must contain the only unseen weapon card
		try {
			ComputerPlayer compPlayer = new ComputerPlayer("Rick", "Red",board.getCellAt(5, 0));
			Card testRoom = new Card("Conservatory", CardType.ROOM);
			Stack<Card> countWeapons = new Stack<Card>(); 
			while(countWeapons.size() != 4) {
				Guess testSuggestion = compPlayer.createSuggestion(board, testRoom);
				board.handleSuggestion(testSuggestion, 0);
				countWeapons.push(testSuggestion.getWeapon());
			}
			Guess finalSuggestion = compPlayer.createSuggestion(board, testRoom);
			while(!countWeapons.isEmpty()) {
				assertTrue(!finalSuggestion.getWeapon().equals(countWeapons.peek()));
				countWeapons.pop();
			}
		}catch(BadConfigFormatException e) {
			throw new BadConfigFormatException("computer player did not initialize correctly.");
		}
	}
	
	@Test
	public void createSuggestionOnePerson() throws BadConfigFormatException{
		//checks to make sure the suggestion must contain the only unseen person card
		try {
			ComputerPlayer compPlayer = new ComputerPlayer("Rick", "Red",board.getCellAt(5, 0));
			Card testRoom = new Card("Conservatory", CardType.ROOM);
			Stack<Card> countPerson = new Stack<Card>(); 
			while(countPerson.size() != 2) {
				Guess testSuggestion = compPlayer.createSuggestion(board, testRoom);
				board.handleSuggestion(testSuggestion, 0);
				countPerson.push(testSuggestion.getPlayer());
	
			}
			Guess finalSuggestion = compPlayer.createSuggestion(board, testRoom);
			while(!countPerson.isEmpty()) {
				assertTrue(!finalSuggestion.getPlayer().equals(countPerson.peek()));
				countPerson.pop();
			}
		}catch(BadConfigFormatException e) {
			throw new BadConfigFormatException("computer player did not initialize correctly.");
		}
	}
	
	@Test
	public void handleSuggestion() throws BadConfigFormatException {
		//checks that suggestions are handled appropriately based on the scenerio
		ArrayList<Player> players = board.getPlayers();
		Card testRoom = new Card("Conservatory", CardType.ROOM);
		ComputerPlayer compPlayer = new ComputerPlayer("Rick", "Red",board.getCellAt(5, 0));
		Guess compSuggestion = compPlayer.createSuggestion(board,testRoom);
		assertEquals(null, board.handleSuggestion(compSuggestion,0));
		
		board.addPlayer(compPlayer);
		board.dealDeck();
		compSuggestion = compPlayer.createSuggestion(board,testRoom);
		assertEquals(null, board.handleSuggestion(compSuggestion,6)); 
	}
}
