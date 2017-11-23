import java.util.Scanner;

public class AlienGame {

    private static class Player {
        public static int hitpoint = 5;
        public static int[] position = new int[2];
        public void shoot(int[] alienpos, Map map) {
        	int x = alienpos[0];
        	int y = alienpos[1];
        	for (Alien al : map.aliens) {
        		if (al.position[0] == x && al.position[1] == y) {
        			double t = Math.random()*9/al.distance(this.position);
        			if (t >= 0.5) {
	        			System.out.println("Der Spieler hat das Alien grtroffen!");
	        			al.status -= 1;
	        			break;
        			} else {
        				System.out.println("Der Spieler hat das Alien verfehlt.");
        			}
        		}
        	}
        }
    }

    private static class Alien {
        public int status = 1; // 1 als lebendig, <= 1 als tot
        public int[] position = new int[2];
        public int distance(int[] pos) {
            int distance = Math.abs(pos[0] - this.position[0]) + Math.abs(pos[1] - this.position[1]);
            return distance;
		}
        public void shoot(Player player) {
        	int x = this.position[0];
        	int y = this.position[1];
        	System.out.printf("Das Alien bei (%d,%d) greift den Spieler an.\n", x, y);
        	double t = Math.random()*6/this.distance(player.position);
        	if (t >= 0.5) {
	        	System.out.println("Das Alien hat den Spieler grtroffen!");
	        	player.hitpoint -= 1;
	        } else {
	        	System.out.println("Das Alien hat den Spieler verfelt.");
	        }
        }
    }

    private static class Map {
        public char[][] map;
        public Player player = new Player();
        public Alien[] aliens;
        public void getInfo (String[] insert) {
            int x = Integer.parseInt(insert[0]),
            y = Integer.parseInt(insert[1]),
            alienzahl = Integer.parseInt(insert[2]);
            
            this.map = new char[x][y];
            this.aliens = new Alien[alienzahl];
        }
        public String toString () {
            return "Der Spieler hat noch " + this.player.hitpoint + " Hitpoints";
        }
        public void printInfo () {
            int breite = this.map.length, hoehe = this.map[0].length;
            for (int i = 0; i < breite; i++) {
                for (int j = 0; j < hoehe; j++) {
                    this.map[i][j] = ' ';
                }
            }
            this.map[this.player.position[0]][this.player.position[1]] = 'P';
            for (Alien al : this.aliens) {
                int x = al.position[0],
                y = al.position[1];
                this.map[x][y] = al.status == 1? 'A' : 'X';
            }
            // Spielfeld zeichnen.
            System.out.println("\nSpielfeld:");
            // Koodinate Top
            System.out.print("  ");
            for (int i = 0; i < breite; i++) {
                System.out.print(i);
            }
            System.out.print("\n ");
            for (int i = 0; i < breite + 2; i++) {
                System.out.print('*');
            }
            System.out.println();
            for (int i = 0; i < hoehe; i++) {
                System.out.print(i + "*");    // Die Waende hinzufuegen
                for (int j = 0; j < breite; j++) {
                    System.out.print(this.map[j][i]);
                }
                System.out.println("*");    // Die Waende hinzufuegen
            }
            System.out.print(" ");
            for (int i = 0; i < breite + 2; i++) {
                System.out.print('*');
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        Map spielfeld = new Map();
        spielfeld.getInfo(args);
        
        // Ueberpruefung, ob genug Parameter eingegeben.
        if (args.length != 3) {
            System.out.println("\n!! Bitte geben Sie 3 Integer als Parameter ein,\n"
                + "\tum die Breite, die Hoehe der Karte und die Anzahl der Aliens zu beschreiben. !!\n");
            return; // Die Methode beenden
        }

        int breite, hoehe, alienanzahl;
        breite = Integer.parseInt(args[0]);
        hoehe =  Integer.parseInt(args[1]);
        alienanzahl = Integer.parseInt(args[2]);

        // Ueberpruefung, ob das Feld mindestens 1 Feld besitzet. 
        if (breite <= 0 || hoehe <= 0 || alienanzahl < 0) {
            System.out.println("\n!! Die Breite und Hoehe des Maps sollen zumindest 1 betragen.\n"
                + "\tDie Anzahl der Aliens sollt zumindest als 0 betragen. !!\n"); 
            return;    // Die Methode beenden
        }

        // Ueberpruefung, ob es genug Platz fuer Spieler und Aliens gibt
        if (alienanzahl > breite * hoehe - 1) { 
            System.out.println("\n!! Zu viele Aliens fuer dieses Map !!\n"); 
            return;    // Die Methode beenden
        }

        // Leerzeichen im Map einfuegen
        char[][] map = new char[breite][hoehe];
        for (int i = 0; i < hoehe; i++) {
            for (int j = 0; j < breite; j++) {
                map[j][i] = ' ';
            }
        }

        // Zufallige Position von Spieler
        while (true) {
            int x = (int) (Math.random() * breite);
            int y = (int) (Math.random() * hoehe);
            if (map[x][y] == ' ') {
                map[x][y] = 'P';
                spielfeld.player.position[0] = x;
                spielfeld.player.position[1] = y;
                break;
            }
        }
        
        // Zufallige Positionen von Aliens
        int alien = 0;
        while (alien < alienanzahl) {
            int x = (int) (Math.random() * breite);
            int y = (int) (Math.random() * hoehe);
            if (map[x][y] == ' ') {
                map[x][y] = 'A';
                spielfeld.aliens[alien] = new Alien();
                spielfeld.aliens[alien].position[0] = x;
                spielfeld.aliens[alien].position[1] = y;
                alien ++;
            }
        }

        outerloop: while (true) {
	        spielfeld.printInfo();
	        System.out.println(spielfeld.toString());

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
	        		deadalien ++;
	        	}
	        	if (deadalien == alienanzahl) {
	        		System.out.println("Der Spieler hat alle Aliens besiegt!");
	        		break outerloop;
	        	}
	        }
	        for (Alien al : spielfeld.aliens) {
	        	if (al.status == 1) {
	        		al.shoot(spielfeld.player);
	        		if (spielfeld.player.hitpoint == 0) {
	        			System.out.println("You lose.");
	        			break outerloop;
	        		}
	        	}
	        }
	    }
    }
}
