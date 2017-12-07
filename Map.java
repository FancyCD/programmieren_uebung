/**
* Diese Klasse repraesentiert das Spielfeld.
* Sie speichert das Map, den Spieler und
* die Aliens auf dem Spielfeld. Der Boolean-Parameter
* smooth zeigt, ob die Erzeugung der Klasse
* erforgtlich ist.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Map {
	private char[][] map;
	private Player player = new Player();
	private Alien[] aliens;
	private boolean smooth;
	public char[][] getMap() {
		return map;
	}
	public Alien[] getAliens() {
		return aliens;
	}
	public boolean getSmooth() {
		return smooth;
	}
	public Player getPlayer() {
		return player;
	}
	/**
	* Diese Methode ist der Konstruktor
	* der Map Klasse. Sie generiert das Map
	* und zufallige Positionen des Spielers 
	* und der Aliens.
	* @param insert ist eingegebene Parameter von Nutzer
	*/
	Map(String[] insert) {
		if (insert.length != 3) {
			System.out.println("\n!! Bitte geben Sie 3 Integer als Parameter ein,\n"
				+ "\tum die Breite, die Hoehe der Karte und die Anzahl der Aliens zu beschreiben. !!\n");
			smooth = false;
			return; // Die Methode beenden
		}
		int x = Integer.parseInt(insert[0]),
			y = Integer.parseInt(insert[1]),
			alienzahl = Integer.parseInt(insert[2]);
		// Ueberpruefung, ob das Feld mindestens 1 Feld besitzet. 
		if (x < 1 || x > 10 || y < 1 || y > 10 || alienzahl < 0) {
			System.out.println("\n!! Die Breite und Hoehe des Maps sollen zwischen 1 und 10 betragen.\n"
				+ "\tDie Anzahl der Aliens sollt zumindest als 0 betragen. !!\n"); 
			smooth = false;
			return;	// Die Methode beenden
		}
		// Ueberpruefung, ob es genug Platz fuer Spieler und Aliens gibt
		if (alienzahl > x * y - 1) { 
			System.out.println("\n!! Zu viele Aliens fuer dieses Map !!\n"); 
			smooth = false;
			return;	// Die Methode beenden
		}
		map = new char[x][y];
		aliens = new Alien[alienzahl];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				map[i][j] = ' ';
			}
		}
		// Zufallige Position von Spieler
		int zufallx = (int) (Math.random() * x),
			zufally = (int) (Math.random() * y);
		map[zufallx][zufally] = 'P';
		player.setPos(zufallx, zufally);
		// Zufallige Positionen von Aliens
		int alien = 0;
		while (alien < alienzahl) {
			zufallx = (int) (Math.random() * x);
			zufally = (int) (Math.random() * y);
			if (map[zufallx][zufally] == ' ') {
				map[zufallx][zufally] = 'A';
				aliens[alien] = new Alien();
				aliens[alien].setPos(zufallx, zufally);
				alien++;
			}
		}
		smooth = true;
	}
	/**
	* Diese Methode ist die toString 
	* Methode der Map-Klasse.
	* @return das Hitpoint des Spielers in String
	*/
	public String toString() {
		int breite = map.length, hoehe = map[0].length;
		for (Alien al : aliens) {
			int x = al.getPos()[0],
				y = al.getPos()[1];
			map[x][y] = al.getStatus() == 1 ? 'A' : 'X';
		}
		// Spielfeld zeichnen.
		System.out.println("\nSpielfeld:");
		// Koodinate Top und Waende Top
		System.out.print("   ");
		for (int i = 0; i < breite; i++) {
			System.out.print(i);
		}
		System.out.print("\n  ");
		for (int i = 0; i < breite + 2; i++) {
			System.out.print('*');
		}
		System.out.println();
		// Spielfeld
		for (int i = 0; i < hoehe; i++) {
			System.out.printf( "%2d*", i);	// Die Waende links hinzufuegen
			for (int j = 0; j < breite; j++) {
				System.out.print(map[j][i]);
			}
			System.out.println("*");	// Die Waende rechts hinzufuegen
		}
		// Der Wand Bottom
		System.out.print("  ");
		for (int i = 0; i < breite + 2; i++) {
			System.out.print('*');
		}
		System.out.println();
		return "Der Spieler hat noch " + player.getHp() + " Hitpoints";
	}
}