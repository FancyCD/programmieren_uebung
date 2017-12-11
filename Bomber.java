class Bomber extends Player {
	public boolean shoot(int[] pos, Map map) {
		if (pos[0] >= map.getMap().length || pos[1] >= map.getMap()[0].length) {
			System.out.println("Die Zielposition ist ausserhalb der Grenze.");
			return false;
		}
		for (Alien al : map.getAliens()) {
			if ((pos[0] -2 < al.getPos()[0] && al.getPos()[0] < pos[0] + 2) && 
				(pos[1] -2 < al.getPos()[1] && al.getPos()[1]< pos[1] + 2)) {
				double t =  Math.random() * 4 / al.distance(getPos());
				if (t > 0.5 && al.isAlive()) {
					System.out.printf("Der Bomber hat das Alien (%d, %d) getroffen!\n", al.getPos()[0], al.getPos()[1]);
					al.tot();
					return true;
				}
			}
		}
		System.out.println("Kein Alien auf der Zielbereich.");
		return false;
	}
}