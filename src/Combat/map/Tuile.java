/*
 * 
 * 
 * 
 */
package Combat.map;

import Serializable.HorsCombat.Map.TypeTuile;
import Serializable.Position;

/**
 * Tuile.java
 * 
 */
public class Tuile {
	
	private TypeTuile type;
	public final Position position;
	
	public Tuile(TypeTuile type, Position position) {
		this.type = type;
		this.position = position;
	}

}
