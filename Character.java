class Character {
	private int[] position = new int[2];
	public int[] getPos() {
		return position;
	}
	public void setPos(int a, int b) {
		position[0] = a;
		position[1] = b;
	}
	/**
	* Diese Methode rechbet den Abstand zwischen 
	* das Alien und eine Zielposition.
	* @param pos zeigt die Zielposition.
	* @return den berechneten Abstand.
	*/
	public int distance(int[] pos) {
		int distance = Math.abs(pos[0] - position[0]) + Math.abs(pos[1] - position[1]);
		return distance;
	}
	/**
	* Diese Methode führt den Schießprozess 
	* vom Alien zu einem Spieler aus.
	* @param player zeigt den Spieler.
	*/
	public void shoot(Player player) {
		int x = position[0];
		int y = position[1];
		System.out.printf("Das Alien bei (%d,%d) greift den Spieler an.\n", x, y);
		double t = Math.random() * 6 / distance(player.getPos());
		if (t >= 0.5) {
			System.out.println("Das Alien hat den Spieler getroffen!");
			player.losHp();
		} else {
			System.out.println("Das Alien hat den Spieler verfelt.");
		}
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
				if (!al.isAlive()) {
					System.out.println("Dieser Alien ist bereits besiegt.");
					return false;
				}
				double t = Math.random() * 9 / al.distance(position);
				if (t >= 0.5) {
					System.out.println("Der Spieler hat das Alien getroffen!");
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