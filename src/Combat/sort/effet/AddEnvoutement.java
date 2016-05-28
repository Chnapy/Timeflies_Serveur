/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.entite.Entite;
import Combat.map.Tuile;
import Combat.sort.envout.Envoutement;
import Combat.sort.TypeCible;

/**
 * AddEnvoutement.java
 *
 */
public class AddEnvoutement extends Effet {

	private final Envoutement envoutement;

	public AddEnvoutement(TypeCible cible, Envoutement envoutement) {
		super(cible);
		this.envoutement = envoutement;
	}

	@Override
	public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		switch (cible) {
			case ENTITE:
				if (entite != null) {
					entite.envoutements.add(envoutement);
				}
				break;
			case LANCEUR:
				lanceur.envoutements.add(envoutement);
				break;
		}
	}

}
