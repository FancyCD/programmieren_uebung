/**
* Diese Klasse repraesentiert ein Wand.
* Sie entscheidet, ob eine Wand einer
* Tuer oder einer festen Wand entspricht.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Wall {
	private boolean isDoor = false;
	/**
	* Diese Methode entscheidet, ob diese Wand einer
	* Tuer oder einer festen Wand entspricht.
	*/
	Wall() {
		double randn = Math.random();
		if (randn > 0.95) {
			isDoor = true;
		}
	}

	/**
	* Diese Methode gibt das Ergebnis zurueck.
	* @return "O" wenn es eine Tuer entspricht.
	* "#" wenn es eine feste Wand entspricht.
	*/
	public String toString() {
		if (isDoor) {
			return "O";
		} else {
			return "#";
		}
	}
}