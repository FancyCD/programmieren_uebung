/**
* Diese Klasse repraesentiert eine Rolle Bomber,
* die eine einzigartige Shoot-Methode hat.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Bomber extends Player {
	/**
	* greift den Zielort und angrenzende Einheiten an.
	* @param pos zeigt den Mittelpunkt des Zielbereichs.
	* @param map zeigt die Spielfeld des Spielers und der Aliens.
	* @return true, falls die Zielposition effekiv ist.
	*/
	public boolean shoot(int[] pos, Map map) {
		boolean hasAlien = false;
		if (pos[0] >= map.getMap().length || pos[1] >= map.getMap()[0].length) {
			System.out.println("Die Zielposition ist ausserhalb der Grenze.");
			return hasAlien;
		}
		for (Alien al : map.getAliens()) {
			if ((pos[0] - 2 < al.getPos()[0] && al.getPos()[0] < pos[0] + 2) 
				&& (pos[1] - 2 < al.getPos()[1] && al.getPos()[1] < pos[1] + 2)) {
				hasAlien = true;
				double t =  Math.random() * 4 / al.distance(getPos());
				if (t > 0.5 && al.isAlive()) {
					System.out.printf("Der Bomber hat das Alien (%d, %d) getroffen!\n", al.getPos()[0], al.getPos()[1]);
					al.tot();
				} else {
					System.out.printf("Der Bomber hat das Alien (%d, %d) verfelt!\n", al.getPos()[0], al.getPos()[1]);
				}

			}
		}
		if (!hasAlien) {
			System.out.println("Kein Alien auf der Zielbereich.");
		}
		return hasAlien;
	}
}