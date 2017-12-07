/**
* Diese Klasse repraesentiert ein Alien.
* Sie speichert den Status und die Position
* des Aliens.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Alien {
	private int status = 1; // 1 als lebendig, 0 als tot
	private int[] position = new int[2];
	public int getStatus () {
		return status;
	}
	public int[] getPos () {
		return position;
	}
	public void tot() {
		status -= 1;
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
}