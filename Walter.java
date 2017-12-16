/**
* Walter White, eine Figur der TV-Show Breaking Bad.
* In AlienGame kann er ein Alien vergiften und 
* es in der nächsten Runde sterben lassen.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Walter extends Player {
	/**
	* Diese Methode vergiftet den Alien auf der Zielposition.
	* @param alienpos zeigt die Position des Zielaliens.
	* @param map zeigt die Spielfeld des Spielers und der Aliens.
	* @return true, falls die Zielposition effekiv ist.
	*/
	public boolean shoot(int[] alienpos, Map map) {
		if (alienpos[0] >= map.getMap().length || alienpos[1] >= map.getMap()[0].length) {
			System.out.println("Die Zielposition ist ausserhalb der Grenze.");
			return false;
		}
		for (Alien al : map.getAliens()) {
			if (al.getPos()[0] == alienpos[0] && al.getPos()[1] == alienpos[1]) {
				if (!al.isAlive()) {
					System.out.println("Dieser Alien ist bereits besiegt.");
					return false;
				}
				al.getPoisoned();
				System.out.println("Das Alien ist jetzt vergiftet.");
				return true;
			}
		}
		System.out.println("Kein Alien auf der Zielposition.");
		return false;
	}
}