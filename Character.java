/**
* Archetyp für alle Charaktere in AlienGame,
* beinhaltet zwei Shoot-Methoden für 
* Spieler bzw. Aliens.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Character {
	private int[] position = new int[2];
	public int[] getPos() {
		return position;
	}
	/**
	* Diese Methode legt die Position des Charakters fest.
	* @param a ist die x-Koordinate der Karte.
	* @param b ist die y-Koordinate der Karte.
	*/
	public void setPos(int a, int b) {
		position[0] = a;
		position[1] = b;
	}
	/**
	* Diese Methode rechbet den Abstand zwischen 
	* den Character und die Zielposition.
	* @param pos zeigt die Zielposition.
	* @return den berechneten Abstand.
	*/
	public int distance(int[] pos) {
		int distance = Math.abs(pos[0] - position[0]) + Math.abs(pos[1] - position[1]);
		return distance;
	}
	/**
	* Diese Methode entscheidet, ob 
	* eine Position für das Zeichen sichtbar ist.
	* @param map ist das Map vom Spielfeld
	* @param pos ist die Position
	* @return true wenn die Position sichtbar ist.
	*/
	public boolean canSee(Map map, int[] pos) {
		if (position[0] == pos[0]) {
			if (Math.abs(pos[1] - position[1]) == 1) {
				return true;
			} else if (pos[1] > position[1]) {
				for (int i = 0; i < pos[1] - position[1]; i++) {
					if (map.getMap()[pos[0]][position[1] + i] == '#') {
						return false;
					}
				}
				return true;
			} else {
				for (int i = 0; i < position[1] - pos[1]; i++) {
					if (map.getMap()[pos[0]][pos[1] + i] == '#') {
						return false;
					}
				}
				return true;
			}
		} else if (position[1] == pos[1]) {
			if (Math.abs(pos[0] - position[0]) == 1) {
				return true;
			} else if (pos[0] > position[0]) {
				for (int i = 0; i < pos[0] - position[0]; i++) {
					if (map.getMap()[position[0] + i][position[1]] == '#') {
						return false;
					}
				}
				return true;
			} else {
				for (int i = 0; i < position[0] - pos[0]; i++) {
					if (map.getMap()[pos[0] + i][pos[1]] == '#') {
						return false;
					}
				}
				return true;
			}
		} else if (Math.abs(position[1] - pos[1]) == Math.abs(position[0] - pos[0])) {
			if (Math.abs(position[1] - pos[1]) == 1) {
				return true;
			} else if (position[1] > pos[1]) {
				if (position[0] > pos[0]) {
					for (int i = 0; i < position[1] - pos[1]; i++) {
						if (map.getMap()[pos[0] + i][pos[1] + i] == '#') {
							return false;
						}
					}
				} else {
					for (int i = 0; i < position[1] - pos[1]; i++) {
						if (map.getMap()[position[0] + i][pos[1] + i] == '#') {
							return false;
						}
					}
				}
				return true;
			} else {
				if (position[0] > pos[0]) {
					for (int i = 0; i < pos[1] - position[1]; i++) {
						if (map.getMap()[pos[0] + i][position[1] + i] == '#') {
							return false;
						}
					}
				} else {
					for (int i = 0; i < pos[1] - position[1]; i++) {
						if (map.getMap()[position[0] + i][position[1] + i] == '#') {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	/**
	* Diese Methode führt den Schießprozess 
	* vom Spieler zu einem Alien aus.
	* @param pos zeigt die Position des Zielaliens.
	* @param map zeigt die Spielfeld des Spielers und der Aliens.
	* @param performer unterscheidet, ob der Charakter ein Spieler oder ein Alien ist.
	* @return true, falls die Zielposition effekiv ist.
	*/
	public boolean shoot(int[] pos, Map map, int performer) {
		int x = pos[0];
		int y = pos[1];
		if (performer == 1) {
			if (canSee(map, pos)) {
				if (x < 0 || y < 0 || x >= map.getMap().length || y >= map.getMap()[0].length) {
					System.out.println("Die Zielposition ist ausserhalb der Grenze.");
					return false;
				}
				for (Alien al : map.getAliens()) {
					if (al.getPos()[0] == x && al.getPos()[1] == y) {
						if (!al.isAlive()) {
							System.out.println("Dieser Alien ist bereits besiegt.");
							return false;
						}
						double t = Math.random() * 6 / al.distance(pos);
						if (t >= 0.5) {
							System.out.println("Der Spieler hat das Alien Getroffen!");
							al.tot();
							return true;
						} else {
							System.out.println("Der Spieler hat das Alien Verfehlt!");
							return true;
						}
					}
				}
				System.out.println("Kein Alien auf der Zielposition.");
				return false;
			} else {
				System.out.println("Die Zielposition wird von eine Wand blockiert.");
				return false;
			}
		} else {
			double t = Math.random() * 4 / distance(pos);
			if (t >= 0.5) {
				System.out.printf("Das Alien bei (%d, %d) has den Spieler Getroffen!\n", getPos()[0], getPos()[1]);
				map.getPlayer().losHp();
				return true;
			} else {
				System.out.printf("Das Alien bei (%d, %d) has den Spieler Verfehlt!\n", getPos()[0], getPos()[1]);
				return true;
			}
		}
	}
}