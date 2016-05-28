/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Serializable.InCombat.Orientation;
import Combat.entite.Entite;
import Combat.map.Tuile;
import Combat.sort.TypeCible;

/**
 * Rotation.java
 *
 */
public class Rotation extends Effet {

	public Rotation(TypeCible cible) {
		super(cible);
	}

	@Override
	public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		Orientation o;
		switch (cible) {
			case ENTITE:
				if (entite != null) {
					o = Orientation.getDirection(entite.position, lanceur.position);
					entite.orientation = o;
				}
				break;
			case LANCEUR:
				o = Orientation.getDirection(lanceur.position, tuile.position);
				lanceur.orientation = o;
				break;
		}
	}

}
