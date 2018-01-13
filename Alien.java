/**
* Diese Klasse repraesentiert ein Alien.
* Sie speichert den Status und die Position
* des Aliens.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Alien extends Character implements Movable {
	private boolean status = true; // 1 als lebendig, 0 als tot
	private boolean poisoned = false;
	static final int maxStep = 2; 
	public int getMaxStep() {
		return maxStep;
	}
	public boolean isAlive() {
		return status;
	}
	/**
	* Diese Methode setzt den Status des Aliens in den Tod.
	*/
	public void tot() {
		status = false;
	}
	/**
	* Diese Methode setzt den Poisoned-Status auf wahr.
	*/
	public void getPoisoned() {
		poisoned = true;
	}
	public boolean isPoisoned() {
		return poisoned;
	}

	public boolean canMove(String pos, Map map) {
		if (pos.length() > maxStep) {
			System.out.println("Die maximale Verschiebungsgrenze wurde ueberschritten.");
			return false;
		}
		int[] newPos = new int[2];
		newPos[0] = getPos()[0];
		newPos[1] = getPos()[1];
		for (int i = 0; i < pos.length(); i++) {
			char letter = pos.charAt(i);
			if (letter == 'w') {
				newPos[1] += -1;
			} else if (letter == 's') {
				newPos[1] += 1;
			} else if (letter == 'a') {
				newPos[0] += -1;
			} else if (letter == 'd') {
				newPos[0] += 1;
			}
			if (newPos[0] < 0 || newPos[0] > map.getMap().length - 1 || newPos[1] < 0 || newPos[1] > map.getMap()[0].length - 1) {
				return false;
			}
			if (map.getPlayer().getPos()[0] == newPos[0] && map.getPlayer().getPos()[1] == newPos[1]) {
				return false;
			}
			for (Alien al : map.getAliens()) {
				if (al.getPos()[0] == newPos[0] && al.getPos()[1] == newPos[1]) {
					System.out.printf("von Alien bei (%d, %d) blockiert.\n", al.getPos()[0], al.getPos()[1]);
					return false;
				}
			}
		}
		return true;
	}

	public void move(String pos, Map spielfeld) {
		int[] newPos = new int[2];
		newPos[0] = getPos()[0];
		newPos[1] = getPos()[1];
		for (int i = 0; i < pos.length(); i++) {
			char letter = pos.charAt(i);
			if (canMove(letter + "", spielfeld)){
				if (letter == 'w') {
					newPos[1] += -1;
				} else if (letter == 's') {
					newPos[1] += 1;
				} else if (letter == 'a') {
					newPos[0] += -1;
				} else if (letter == 'd') {
					newPos[0] += 1;
				}
			}
		}
		setPos(newPos[0], newPos[1]);
	}
}