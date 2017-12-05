import java.util.Scanner;
/**
* Diese Klasse repraesentiert den Spieler.
* Sie speichert das Hitpoint und die Position
* des Spielers.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Player {
	private int hitpoint = 5;
	private int[] position = new int[2];
	public int getHp() {
		return hitpoint;
	}
	public void losHp() {
		hitpoint -= 1;
	}
	public int[] getPos() {
		return position;
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
		if (x >= map.map.length || y >= map.map[0].length) {
			System.out.println("Die Zielposition ist ausserhalb der Grenze.");
			return false;
		}
		for (Alien al : map.aliens) {
			if (al.position[0] == x && al.position[1] == y) {
				if (al.status == 0) {
					System.out.println("Dieser Alien ist bereits besiegt.");
					return false;
				}
				double t = Math.random() * 9 / al.distance(position);
				if (t >= 0.5) {
					System.out.println("Der Spieler hat das Alien grtroffen!");
					al.status -= 1;
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
		double t = Math.random() * 6 / distance(player.position);
		if (t >= 0.5) {
			System.out.println("Das Alien hat den Spieler getroffen!");
			player.hitpoint -= 1;
		} else {
			System.out.println("Das Alien hat den Spieler verfelt.");
		}
	}
}
/**
* Diese Klasse repraesentiert das Spielfeld.
* Sie speichert das Map, den Spieler und
* die Aliens auf dem Spielfeld. Der Boolean-Parameter
* smooth zeigt, ob die Erzeugung der Klasse
* erforgtlich ist.
* @author Chijiang Duan 4727082 Gruppe 4a
*/
class Map {
	public char[][] map;
	public Player player = new Player();
	public Alien[] aliens;
	public boolean smooth;
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
		player.position[0] = zufallx;
		player.position[1] = zufally;
		// Zufallige Positionen von Aliens
		int alien = 0;
		while (alien < alienzahl) {
			zufallx = (int) (Math.random() * x);
			zufally = (int) (Math.random() * y);
			if (map[zufallx][zufally] == ' ') {
				map[zufallx][zufally] = 'A';
				aliens[alien] = new Alien();
				aliens[alien].position[0] = zufallx;
				aliens[alien].position[1] = zufally;
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
			int x = al.position[0],
				y = al.position[1];
			map[x][y] = al.status == 1 ? 'A' : 'X';
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
		return "Der Spieler hat noch " + player.hitpoint + " Hitpoints";
	}
}
/**
* Diese Klasse hat ein mai-Methode fuer das
* Gameplay des Spiels. 
* @author Chijiang Duan 4727082 Gruppe 4a
*/
public class AlienGame {
	public static void main(String[] args) throws Exception {
		Map spielfeld = new Map(args);
		int x, y;
		while (spielfeld.smooth) {
			System.out.println(spielfeld);
			Scanner usrinput = new Scanner(System.in);
			System.out.println("Wohin soll der Spieler angreifen? (X-Koordinate)");
			try {
				x = usrinput.nextInt();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Bitte geben Sie ein Ganzenzahl ein.");
				continue;
			}
			System.out.println("Wohin soll der Spieler angreifen? (Y-Koordinate)");
			try {
				y = usrinput.nextInt();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Bitte geben Sie ein Ganzenzahl ein.");
				continue;
			}
			int[] pos = {x, y};
			if (!spielfeld.player.shoot(pos, spielfeld)) {
				System.out.println("Bitte geben Sie eine andere Position ein!");
				continue;
			}
			int deadalien = 0;
			for (Alien al : spielfeld.aliens) {
				if (al.status < 1) {
					deadalien++;
				}
				if (deadalien == spielfeld.aliens.length) {
					System.out.println("Der Spieler hat alle Aliens besiegt!");
					return;
				}
			}
			for (Alien al : spielfeld.aliens) {
				if (al.status == 1) {
					al.shoot(spielfeld.player);
					if (spielfeld.player.hitpoint == 0) {
						System.out.println("Die Aliens haben der Spieler besiegt.");
						return;
					}
				}
			}
		}
	}
}
