/**
* Diese Klasse repraesentiert den Spieler.
* Sie speichert das Hitpoint und die Position
* des Spielers.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Player {
	private int hitpoint = 5;
	private int[] position = new int[2];
	public int getHp() {
		return hitpoint;
	}
	public void losHp() {
		hitpoint -= 1;
	}
	public int[] getPos() {
		return position;
	}
	public void setPos(int a, int b) {
		position[0] = a;
		position[1] = b;
	}
	/**
	* Diese Methode führt den Schießprozess 
	* vom Spieler zu einem Alien aus.
	* @param alienpos zeigt die Position des Zielaliens.
	* @param map zeigt die Spielfeld des Spielers und der Aliens.
	* @return true, falls die Zielposition effekiv ist.
	*/
	public boolean shoot(int[] alienpos, Map map) {
		int x = alienpos[0];
		int y = alienpos[1];
		if (x >= map.getMap().length || y >= map.getMap()[0].length) {
			System.out.println("Die Zielposition ist ausserhalb der Grenze.");
			return false;
		}
		for (Alien al : map.getAliens()) {
			if (al.getPos()[0] == x && al.getPos()[1] == y) {
				if (al.getStatus() == 0) {
					System.out.println("Dieser Alien ist bereits besiegt.");
					return false;
				}
				double t = Math.random() * 9 / al.distance(position);
				if (t >= 0.5) {
					System.out.println("Der Spieler hat das Alien grtroffen!");
					al.tot();
					return true;
				} else {
					System.out.println("Der Spieler hat das Alien verfehlt.");
					return true;
				}
			}
		}
		System.out.println("Kein Alien auf der Zielposition.");
		return false;
	}
}