/**
* Diese Klasse repraesentiert den Spieler.
* Sie speichert das Hitpoint und die Position
* des Spielers.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Player extends Character {
	private int hitpoint = 5;
	public int getHp() {
		return hitpoint;
	}
	public void losHp() {
		hitpoint -= 1;
	}
}