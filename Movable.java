/**
* Interface der beweglichen Characters
* @author Chijiang Duan 4727082 Gruppe 4a
*/
interface Movable {
	/**
	* Methode, um festzustellen, 
	* ob ein Pfad für den Character moeglich ist.
	* @param pos beschreibt den Pfad
	* @param map zeigt das Spielmap
	* @return true, wenn beweglich
	*/
	boolean canMove(String pos, Map map);
	/**
	* Methode, um der Character entlang des 
	* gegebenen Pfades zu bewegen.
	* @param pos beschreibt den Pfad
	* @param spielfeld zeigt das Spielmap
	*/
	void move(String pos, Map spielfeld);
}