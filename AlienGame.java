import java.util.Scanner;

public class AlienGame {
	private static boolean smooth = true;
	private static class Player {
		private int hitpoint = 5;
		private int[] position = new int[2];
		private void shoot(int[] alienpos, Map map) {
			int x = alienpos[0];
			int y = alienpos[1];
			if (x >= map.map.length || y >= map.map[0].length) {
				System.out.println("Die Zielposition ist ausserhalb der Grenze.");
				return;
			}
			for (Alien al : map.aliens) {
				if (al.position[0] == x && al.position[1] == y) {
					if (al.status == 0) {
						System.out.println("Dieser Alien ist bereits besiegt.");
						return;
					}
					double t = Math.random() * 9 / al.distance(position);
					if (t >= 0.5) {
						System.out.println("Der Spieler hat das Alien grtroffen!");
						al.status -= 1;
						return;
					} else {
						System.out.println("Der Spieler hat das Alien verfehlt.");
						return;
					}
				}
			}
			System.out.println("Kein Alien auf der Zielposition.");
		}
	}

	private static class Alien {
		private int status = 1; // 1 als lebendig, 0 als tot
		private int[] position = new int[2];
		private int distance(int[] pos) {
			int distance = Math.abs(pos[0] - position[0]) + Math.abs(pos[1] - position[1]);
			return distance;
		}
		private void shoot(Player player) {
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

	private static class Map {
		private char[][] map;
		private Player player = new Player();
		private Alien[] aliens;
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
			if (x <= 0 || y <= 0 || alienzahl < 0) {
				System.out.println("\n!! Die Breite und Hoehe des Maps sollen zumindest 1 betragen.\n"
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
			while (true) {
				int zufallx = (int) (Math.random() * x);
				int zufally = (int) (Math.random() * y);
				if (map[zufallx][zufally] == ' ') {
					map[zufallx][zufally] = 'P';
					player.position[0] = zufallx;
					player.position[1] = zufally;
					break;
				}
			}
			// Zufallige Positionen von Aliens
			int alien = 0;
			while (alien < alienzahl) {
				int zufallx = (int) (Math.random() * x);
				int zufally = (int) (Math.random() * y);
				if (map[zufallx][zufally] == ' ') {
					aliens[alien] = new Alien();
					aliens[alien].position[0] = zufallx;
					aliens[alien].position[1] = zufally;
					alien++;
				}
			}
		}
		public String toString() {
			return "Der Spieler hat noch " + player.hitpoint + " Hitpoints";
		}
		public void plotMap() {
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
		}

	}

	public static void main(String[] args) {
		Map spielfeld = new Map(args);
		int alienzahl = Integer.parseInt(args[2]);
		outerloop: while (smooth) {
			spielfeld.plotMap();
			System.out.println(spielfeld);
			Scanner usrinput = new Scanner(System.in);
			System.out.println("Wohin soll der Spieler angreifen? (X-Koordinate)");
			int x = usrinput.nextInt();
			System.out.println("Wohin soll der Spieler angreifen? (Y-Koordinate)");
			int y = usrinput.nextInt();
			int[] pos = {x, y};
			spielfeld.player.shoot(pos, spielfeld);
			int deadalien = 0;
			for (Alien al : spielfeld.aliens) {
				if (al.status < 1) {
					deadalien++;
				}
				if (deadalien == alienzahl) {
					System.out.println("Der Spieler hat alle Aliens besiegt!");
					break outerloop;
				}
			}
			for (Alien al : spielfeld.aliens) {
				if (al.status == 1) {
					al.shoot(spielfeld.player);
					if (spielfeld.player.hitpoint == 0) {
						System.out.println("Die Aliens haben der Spieler besiegt.");
						break outerloop;
					}
				}
			}
		}
	}
}
