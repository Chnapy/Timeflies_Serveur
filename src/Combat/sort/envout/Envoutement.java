/*
 * 
 * 
 * 
 */
package Combat.sort.envout;

import Combat.sort.declencheur.Declencheur;
import Combat.sort.effet.Effet;

/**
 * Envoutement.java
 *
 */
public class Envoutement {

	private final Declencheur[] declencheurs;
	public final Effet[] effets;
	public final int nbrToursDuree;

	public Envoutement(Declencheur[] declencheurs, int nbrTours, Effet... effets) {
		this.declencheurs = declencheurs;
		this.effets = effets;
		this.nbrToursDuree = nbrTours;
	}

	public boolean estDeclenche(Effet effet) {
		for (Declencheur d : declencheurs) {
			if (d.estDeclenche(effet)) {
				return true;
			}
		}
		return false;
	}

}
