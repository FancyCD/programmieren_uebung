class Sniper extends Player {
	Sniper() {
		super.losHp();
		super.losHp();
	}
	public boolean shoot(int[] pos, Map map) {
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