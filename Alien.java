/**
* Diese Klasse repraesentiert ein Alien.
* Sie speichert den Status und die Position
* des Aliens.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Alien extends Character {
	private boolean status = true; // 1 als lebendig, 0 als tot
	private boolean poisoned = false;
	public boolean isAlive() {
		return status;
	}
	public void tot() {
		status = false;
	}
	public void getPoisoned() {
		poisoned = true;
	}
	public boolean isPoisoned() {
		return poisoned;
	}
}