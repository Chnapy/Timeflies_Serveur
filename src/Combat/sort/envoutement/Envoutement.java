/*
 * 
 * 
 * 
 */
package Combat.sort.envoutement;

import Serializable.InCombat.InCombat;
import Combat.sort.declencheur.Declencheur;
import Combat.sort.effet.Effet;

/**
 * Envoutement.java
 * 
 */
public class Envoutement implements InCombat {

	private static final long serialVersionUID = 5564981549981070656L;
	
	private final Declencheur declencheur;
	private final Effet[] effets;
	private final int nbrToursDuree;

	public Envoutement(Declencheur declencheur, int nbrTours, Effet... effets) {
		this.declencheur = declencheur;
		this.effets = effets;
		this.nbrToursDuree = nbrTours;
	}

}
