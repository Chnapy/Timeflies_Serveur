/*
 * 
 * 
 * 
 */
package Combat.sort.declencheur;

import Combat.sort.effet.Effet;
import Combat.sort.effet.EffetTour;

/**
 * Declencheur.java
 *
 */
public interface Declencheur {

	public static final Declencheur DEBUT_COMBAT = (Effet effet) -> {
		return effet instanceof EffetTour.EfDebutCombat;
	};

	public static final Declencheur DEBUT_TOUR_GLOBAL = (Effet effet) -> {
		return effet instanceof EffetTour.EfDebutTourGlobal;
	};

	public static final Declencheur FIN_TOUR_GLOBAL = (Effet effet) -> {
		return effet instanceof EffetTour.EfFinTourGlobal;
	};

	public static final Declencheur DEBUT_TOUR = (Effet effet) -> {
		return effet instanceof EffetTour.EfDebutTour;
	};

	public static final Declencheur FIN_TOUR = (Effet effet) -> {
		return effet instanceof EffetTour.EfFinTour;
	};

	public boolean estDeclenche(Effet effet);

}
