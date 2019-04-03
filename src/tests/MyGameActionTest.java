package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

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
	public void selectTargetLocationComp() {
		ArrayList<Player> players = board.getPlayers();
		Player computerPlayer;
		//Make the computer player Colonel Mustard
		for(Player x: players) {
			if(x.getName() == "Colonel Mustard") {
				computerPlayer = x;
				break;
			}
		}
		
	}
	
	/*
	 * need to make an accusation class and a getter for accusation in board class
	 */
	@Test
	public void checkAccusation() {
		Card testWeapon = new Card("Rope", CardType.WEAPON);
		Card testPlayer = new Card("Colonel Mustard", CardType.PLAYER);
		Card testRoom = new Card("Pool", CardType.ROOM);
		Card falseRoom = new Card("Kitchen", CardType.ROOM);
		Card falsePlayer = new Card("ABC", CardType.PLAYER);
		Card falseWeapon = new Card("Axe", CardType.WEAPON);
		
		Accusation testAcc = new Accusation(testPlayer, testRoom, testWeapon);
		assertEquals(board.getAccusation(), testAcc);
		testAcc = new Accusation(testPlayer, falseRoom, testWeapon);
		assertEquals(board.getAccusation(), testAcc);
		testAcc = new Accusation(falsePlayer, testRoom, testWeapon);
		assertEquals(board.getAccusation(), testAcc);
		testAcc = new Accusation(testPlayer, testRoom, falseWeapon);
		assertEquals(board.getAccusation(), testAcc);
		
	}
}
