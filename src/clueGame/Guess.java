package clueGame;

public class Guess {
	private Card player;
	private Card room;
	private Card weapon;
	
	public Guess() {
		player = null;
		room = null;
		weapon = null;
	}
	
	public void setPlayer(Card pCard) {
		player = pCard;
	}
	
	public void setRoom(Card rCard) {
		room = rCard;
	}
	
	public void setWeapon(Card wCard) {
		weapon = wCard;
	}
	
	public Card getPlayer() {
		return this.player;
	}
	
	public Card getRoom() {
		return this.room;
	}
	
	public Card getWeapon() {
		return this.weapon;
	}
	
	public String toString() {
		return "Player: " + this.player.getName() + "Room: " + this.room.getName() + "Weapon: " + this.weapon.getName();
	}
	
	public boolean equals(Guess other) {
		if(other == null) {
			return false;
		}
		if(this.player.equals(other.getPlayer()) && this.room.equals(other.getRoom()) && this.weapon.equals(other.getWeapon())) {
			return true;
		}
		return false;
	}
}
