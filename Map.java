import java.util.Collections;
import java.util.Arrays;

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
	private Player player;
	private Alien[] aliens;
	private boolean smooth;
	public char[][] getMap() {
		return map;
	}
	public Alien[] getAliens() {
		return aliens;
	}
	public boolean isSmooth() {
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
	* @param choice bestimmt die Spielerklasse
	*/
	Map(String[] insert, char choice) {
		switch (choice) {
			case 'P' : 
				player = new Player();
				break;
			case 'S' :
				player = new Sniper();
				break;
			case 'B' :
				player = new Bomber();
				break;
			case 'W' :
				player = new Walter();
				break;
		}
		if (insert.length != 3) {
			System.out.println("\n!! Bitte geben Sie 3 Integer als Parameter ein,\n"
				+ "\tum die Breite, die Hoehe der Karte und die Anzahl der Aliens zu beschreiben. !!\n");
			smooth = false;
			return; // Die Methode beenden
		}
		int x, y, alienzahl;
		try {
			x = Integer.parseInt(insert[0]);
			y = Integer.parseInt(insert[1]);
			alienzahl = Integer.parseInt(insert[2]);
		} catch (java.lang.NumberFormatException err) {
			System.out.println("Breite, Hoehe und Alienzahl sollen Ganzzahlen sein.");
			smooth = false;
			return;
		}
		// Ueberpruefung, ob das Feld mindestens 1 Feld besitzet. 
		if (x < 5 || y < 5 || alienzahl < 0) {
			System.out.println("\n!! Die Breite und Hoehe des Maps sollen groesser als 4 betragen.\n"
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
		// Irrgarten generiert
		map = new char[x][y];
		map = generateMatchfield(x, y);
		// Alienarray generiert
		aliens = new Alien[alienzahl];
		// Zufallige Position von Spieler
		while (true) {
			int zufallx = (int) (Math.random() * x),
				zufally = (int) (Math.random() * y);
			if (map[zufallx][zufally] == ' ') {
				map[zufallx][zufally] = 'P';
				player.setPos(zufallx, zufally);
				break;
			}
		}
		// Zufallige Positionen von Aliens
		int alien = 0;
		while (alien < alienzahl) {
			int zufallx = (int) (Math.random() * x),
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
	* Diese Methode entstellt ein Labyrinth fuer das Spielfeld.
	* @param width ist die Breite des Maps
	* @param height ist die hoehe des Maps
	* @return ein Irrgarten als das Map.
	*/
	private char[][] generateMatchfield(int width, int height) {
		// Map generiert und Leerzeichen eingeben
		char[][] field = new char[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Wall wall = new Wall();
				field[i][j] = wall.toString().charAt(0);
			}
		}
		int x = (int) (1 + Math.random() * (width - 3) / 2);
		int y = (int) (1 + Math.random() * (height - 3) / 2);
		irrgarten(field, 2 * x, 2 * y);
		return field;
	}
	/**
	* Diese Methode macht den zugaenglichen Bereich im
	* Map. Es verwendet Rekursion, um ein Labyrinth 
	* zu erstellen.
	* @param field zeigt das Map des Spielfelds.
	* @param x ist x-Koordinate des Zentralpunkts
	* @param y ist y-Koordinate des Zentralpunkts
	*/
	private void irrgarten(char[][] field, int x, int y) {
		field[x][y] = ' ';
		int[][] nachbarn = new int[4][2];
		
		nachbarn[0][0] = x + 2;
		nachbarn[0][1] = y;
		
		nachbarn[1][0] = x - 2;
		nachbarn[1][1] = y;
		
		nachbarn[2][0] = x;
		nachbarn[2][1] = y + 2;
		
		nachbarn[3][0] = x;
		nachbarn[3][1] = y - 2;

		Collections.shuffle(Arrays.asList(nachbarn));

		for (int[] pos : nachbarn) {
			try {
				if (field[pos[0]][pos[1]] != ' ') {
					field[(x + pos[0]) / 2][(y + pos[1]) / 2] = ' ';
					irrgarten(field, pos[0], pos[1]);
				}
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}
		return;
	}

	/**
	* Diese Methode ist die toString 
	* Methode der Map-Klasse.
	* @return das Hitpoint des Spielers in String
	*/
	public String toString() {
		int breite = map.length, hoehe = map[0].length;
		for (Alien al : aliens) {
			int a = al.getPos()[0],
				b = al.getPos()[1];
			map[a][b] = al.isAlive() ? 'A' : 'X';
		}
		map[player.getPos()[0]][player.getPos()[1]] = 'P';
		// Spielfeld zeichnen.
		System.out.println("Spielfeld:");
		// Koodinate Top und Waende Top
		System.out.print("   ");
		for (int i = 0; i < breite; i++) {
			System.out.print(i % 10);
		}
		System.out.print("\n  ");
		for (int i = 0; i < breite + 2; i++) {
			System.out.print('#');
		}
		System.out.println();
		// Spielfeld
		for (int i = 0; i < hoehe; i++) {
			System.out.printf( "%2d#", i);	// Die Waende links hinzufuegen
			for (int j = 0; j < breite; j++) {
				System.out.print(map[j][i]);
			}
			System.out.println("#");	// Die Waende rechts hinzufuegen
		}
		// Der Wand Bottom
		System.out.print("  ");
		for (int i = 0; i < breite + 2; i++) {
			System.out.print('#');
		}
		System.out.println();
		return "Der Spieler hat noch " + player.getHp() + " Hitpoints";
	}
}