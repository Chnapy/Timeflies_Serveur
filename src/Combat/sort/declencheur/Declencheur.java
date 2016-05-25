/*
 * 
 * 
 * 
 */
package Combat.sort.declencheur;

import Serializable.InCombat.InCombat;
import Combat.sort.effet.DebutCombat;
import Combat.sort.effet.Effet;

/**
 * Declencheur.java
 * 
 */
public interface Declencheur extends InCombat {
	
	public static final Declencheur DEBUT_COMBAT = (Effet effet) -> {
		return effet instanceof DebutCombat;
	};
	
	public boolean estDeclenche(Effet effet);

}
