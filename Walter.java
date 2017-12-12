class Walter extends Player {
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