/**
* Archetyp für alle Charaktere in AlienGame,
* beinhaltet zwei Shoot-Methoden für 
* Spieler bzw. Aliens.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Character implements Movable {
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
	* Diese Methode führt den Schießprozess 
	* vom Spieler zu einem Alien aus.
	* @param pos zeigt die Position des Zielaliens.
	* @param map zeigt die Spielfeld des Spielers und der Aliens.
	* @return true, falls die Zielposition effekiv ist.
	*/
	public boolean shoot(int[] pos, Map map, int performer) {
		int x = pos[0];
		int y = pos[1];
		if (performer == 1) {
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
					double t = Math.random() * 9 / al.distance(position);
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
			double t = Math.random() * 9 / distance(position);
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
	public boolean canMove(int[] pos, Map map) {
		if (map.getPlayer().getPos()[0] == pos[0] && map.getPlayer().getPos()[1] == pos[1]) {
			System.out.println("Player ist da");
			return false;
		}
		for(Alien al : map.getAliens()) {
			if (al.getPos()[0] == pos[0] && al.getPos()[1] == pos[1]) {
				System.out.println("Ein Alien ist auf dieser Position");
				return false;
			}
		}
		return true;
	}
	public void move(int[] pos) {
		int x = pos[0], y = pos[1];
		setPos(x, y);
	}
}