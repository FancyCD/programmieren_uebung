import java.util.Scanner;
/**
* Diese Klasse hat ein mai-Methode fuer das
* Gameplay des Spiels. 
* @author Chijiang Duan 4727082 Gruppe 4a
*/
public class AlienGame {
	/**
	* Methode, um zu überprüfen, ob alle Aliens besiegt sind.
	* @param aliens ist das Alien-Array, das überprüft werden soll.
	* @return true, wenn alle Aliens besigt sind.
	*/
	static boolean alienClear(Alien[] aliens) {
		int deadalien = 0;
		for (Alien al : aliens) {
			if (!al.isAlive()) {
				deadalien++;
			}
		}
		if (deadalien == aliens.length) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) throws Exception {
		char choice;
		System.out.println("|---------------------------------------------------------------------------|");
		System.out.println("|Spielcharakter:                                                            |");
		System.out.println("|Normaler Spieler, mit 5 HP und normale Treffwahrscheinlichkeit.            |");
		System.out.println("|Sniper, mit 100% Treffwahrscheinlichkeit und 3 HP.                         |");
		System.out.println("|Bomber, greift den Zielort und angrenzende Einheiten an.                   |");
		System.out.println("|Walter White, vergiftet das Alien und laesst es in der naechsten Runde tot.|");
		System.out.println("|---------------------------------------------------------------------------|\n");
		double chara = Math.random();
		if (chara < 0.25) {
			choice = 'P';
			System.out.println("Sie spielen jetzt als ein normale Spieler.\n");
		} else if (chara < 0.5) {
			choice = 'S';
			System.out.println("Sie spielen jetzt als ein Sniper.\n");
		} else if (chara < 0.75) {
			choice = 'B';
			System.out.println("Sie spielen jetzt als ein Bomber.\n");
		} else {
			choice = 'W';
			System.out.println("Sie spielen jetzt als Walter White.\n");
		}
		Map spielfeld = new Map(args, choice);
		int x, y;
		while (spielfeld.isSmooth()) {
			Scanner usrinput = new Scanner(System.in);
			for (Alien al : spielfeld.getAliens()) {
				if (al.isAlive() && al.isPoisoned()) {
					System.out.printf("\nAlien bei (%d,%d) starben an Vergiftungen.\n", al.getPos()[0], al.getPos()[1]);
					al.tot();
				}
			}
			if (alienClear(spielfeld.getAliens())) {
				System.out.println("Der Spieler hat alle Aliens besiegt!");
				return;
			}
			System.out.println(spielfeld);
			// ====================================================
			// Spieler move
			playerMove: while (true) {
				System.out.println("Wohin soll der Spieler gehen?");
				String path = "";
				path = usrinput.nextLine();
				if (path.isEmpty()) {
					break;
				}
				for (int i = 0; i < path.length(); i++) {
					char letter = path.charAt(i);
					if (letter != 'w' && letter != 's' && letter != 'a' && letter != 'd') {
						System.out.println("Eingabe nicht erkennbar.");
						continue playerMove;
					}
				}
				if (spielfeld.getPlayer().canMove(path, spielfeld)) {
					spielfeld.getPlayer().move(path, spielfeld);
					break;
				}
			}
			System.out.println(spielfeld);
			// ====================================================
			// Spieler attack
			while (true) {
				boolean hasAlien = false;
				for (Alien al : spielfeld.getAliens()) {
					if (al.isAlive() && al.canSee(spielfeld, spielfeld.getPlayer().getPos())) {
						System.out.printf("Das Spiel kann nun das Alien bei (%d, %d) angreifen.\n", al.getPos()[0], al.getPos()[1]);
						hasAlien = true;
					}
				}
				if (!hasAlien) {
					System.out.println("Es gibt kein Alien, das jetzt vom Spieler angegriffen werden kann.");
					break;
				}
				usrinput = new Scanner(System.in);
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
				if (!spielfeld.getPlayer().shoot(pos, spielfeld, 1)) {
					System.out.println("Bitte geben Sie eine andere Position ein!");
					continue;
				}
				if (alienClear(spielfeld.getAliens())) {
					System.out.println("Der Spieler hat alle Aliens besiegt!");
					return;
				}
				break;
			}
			System.out.println(spielfeld);
			// =======================================================
			// Aliens move
			System.out.println("Die Aliens bewegen sich.");
			for (Alien al : spielfeld.getAliens()) {
				if (al.isAlive()) {
					System.out.printf("Das Alien an (%d, %d) bewegt sich zu ", al.getPos()[0], al.getPos()[1]);
					String[] possibleMove = {"w", "a", "s", "d", "wa", "aw", "as", "sa", "dw", "wd", "sd", "ds"};
					int randMove = (int) (Math.random() * 12);
					al.move(possibleMove[randMove], spielfeld);
					System.out.printf("(%d, %d).\n", al.getPos()[0], al.getPos()[1]);
				}
			}
			System.out.println(spielfeld);
			// ==================================================================
			// Aliens attack
			for (Alien al : spielfeld.getAliens()) {
				if (al.isAlive()) {
					if (al.canSee(spielfeld, spielfeld.getPlayer().getPos())) {
						al.shoot(spielfeld.getPlayer().getPos(), spielfeld, 2);
						if (spielfeld.getPlayer().getHp() == 0) {
							System.out.println("Die Aliens haben der Spieler besiegt.");
							return;
						}
					}
				}
			}
		}
	}
}
