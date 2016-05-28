/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.entite.Entite;
import Combat.map.Tuile;
import Combat.sort.TypeCible;

/**
 * SEffet.java
 *
 */
public abstract class Effet {

	public final TypeCible cible;

	public Effet(TypeCible cible) {
		this.cible = cible;
	}

	public abstract void affecter(Tuile tuile, Entite entite, Entite lanceur);

}
