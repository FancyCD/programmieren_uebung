import java.util.Scanner;
/**
* Diese Klasse hat ein mai-Methode fuer das
* Gameplay des Spiels. 
* @author Chijiang Duan 4727082 Gruppe 4a
*/
public class AlienGame {
	public static void main(String[] args) throws Exception {
		Scanner usrinput = new Scanner(System.in);
		String input;
		char choice;
		while (true) {
			System.out.println("Bitte waehlen Sie Ihr Spielerklasse:");
			System.out.println("P - Normale Spieler, mit 5 HP und normale Treffgenauigkeit.");
			System.out.println("S - Sniper, mit 100% Treffgenauigkeit und 3 HP.");
			System.out.println("B - Bomber, greift den Zielort und angrenzende Einheiten an.");
			System.out.println("W - Walter White, vergiftet das Alien und laesst es in der naechsten Runde tot.");
			System.out.print("Ihr Auswahl: ");
			input = usrinput.next();
			if (input.length() > 1) {
				System.out.println("Bitte geben Sie eine Buchstabung als eine Klasse ein.");
				continue;
			}
			choice = input.charAt(0);
			if (choice != 'P' && choice != 'S' && choice != 'B' && choice != 'W') {
				System.out.println("Bitte geben Sie eine Buchstabung als eine Klasse ein.");
				continue;
			}
			break;
		}
		Map spielfeld = new Map(args, choice);
		int x, y;
		while (spielfeld.isSmooth()) {
			System.out.println(spielfeld);
			// Spieler attack
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
			if (!spielfeld.getPlayer().shoot(pos, spielfeld)) {
				System.out.println("Bitte geben Sie eine andere Position ein!");
				continue;
			}
			int deadalien = 0;
			for (Alien al : spielfeld.getAliens()) {
				if (!al.isAlive()) {
					deadalien++;
				}
				if (deadalien == spielfeld.getAliens().length) {
					System.out.println("Der Spieler hat alle Aliens besiegt!");
					return;
				}
			}
			
			// Aliens attack
			for (Alien al : spielfeld.getAliens()) {
				if (al.isAlive()) {
					al.shoot(spielfeld.getPlayer());
					if (spielfeld.getPlayer().getHp() == 0) {
						System.out.println("Die Aliens haben der Spieler besiegt.");
						return;
					}
					if (al.isPoisoned()) {
						System.out.printf("Alien bei (%d,%d) starben an Vergiftungen.\n", al.getPos()[0], al.getPos()[1]);
						al.tot();
						deadalien++;
						if (deadalien == spielfeld.getAliens().length) {
							System.out.println("Der Spieler hat alle Aliens besiegt!");
							return;
						}
					}
				}
			}
		}
	}
}
