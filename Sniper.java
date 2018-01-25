/**
* Diese Klasse repraesentiert eine Rolle Sniper,
* die eine einzigartige Shoot-Methode und nur 3 HP hat.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Sniper extends Player {
	/**
	* Diese Method initialisiert die Klasse mit 2 
	* Hitpoint weniger als die normale Spielerklasse
	*/
	Sniper() {
		super.losHp();
		super.losHp();
	}
	/**
	* die Methode sicherlich das Ziel zu treffen.
	* @param pos zeigt die Position des Zielaliens.
	* @param map zeigt die Spielfeld des Spielers und der Aliens.
	* @param performer immer 1 als der Spieler
	* @return true, falls die Zielposition effekiv ist.
	*/
	public boolean shoot(int[] pos, Map map, int performer) {
		if (!canSee(map, pos)) {
			System.out.println("Der Spieler konnte die Zielposition nicht erreichen.");
			return false;
		}
		for (Alien al : map.getAliens()) {
			if (pos[0] >= map.getMap().length || pos[1] >= map.getMap()[0].length) {
				System.out.println("Die Zielposition ist ausserhalb der Grenze.");
				return false;
			}
			if (al.getPos()[0] == pos[0] && al.getPos()[1] == pos[1]) {
				if (!al.isAlive()) {
					System.out.println("Dieser Alien ist bereits besiegt.");
					return false;
				}
				System.out.println("Der Sniper hat das Alien getroffen!");
				al.tot();
				return true;
			}
		}
		System.out.println("Kein Alien auf der Zielposition.");
		return false;
	}
}