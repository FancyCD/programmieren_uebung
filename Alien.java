/**
* Diese Klasse repraesentiert ein Alien.
* Sie speichert den Status und die Position
* des Aliens.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Alien extends Character {
	private int status = 1; // 1 als lebendig, 0 als tot
	public int getStatus() {
		return status;
	}
	public void tot() {
		status -= 1;
	}
}