/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.entite.Entite;
import Combat.map.Tuile;
import Combat.sort.TypeCible;
import Serializable.Position;

/**
 * Teleportation.java
 *
 */
public class Teleportation extends Effet {

	public Teleportation(TypeCible cible) {
		super(cible);
	}

	@Override
	public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		switch (cible) {
			case TUILE:
				lanceur.position = tuile.position;
				break;
			case ENTITE:
				if (entite != null) {
					Position p = lanceur.position;
					lanceur.position = entite.position;
					entite.position = p;
				}
		}
	}

}
