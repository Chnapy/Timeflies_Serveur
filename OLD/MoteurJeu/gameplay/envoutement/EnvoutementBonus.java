/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.envoutement;

import MoteurJeu.caracteristique.Carac;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.Orientation;

/**
 * EnvoutementBonus.java
 * Représente un bonus, temporaire, appliqué à une entité active.
 *
 */
public abstract class EnvoutementBonus extends Envoutement {

	private Carac carac;
	private int valeur;

	/**
	 *
	 *
	 * @param nom
	 * @param duree
	 * @param c
	 * @param _valeur
	 */
	public EnvoutementBonus(String nom, int duree, Carac c, int _valeur) {
		super(nom, duree);
		carac = c;
		valeur = _valeur;
	}

	@Override
	public void actionDebutTour() {

	}

	@Override
	public void actionFinTour() {

	}

	@Override
	public void actionDebutTourGlobal() {

	}

	@Override
	public void actionFinTourGlobal() {

	}

	@Override
	public void actionDebutEnvoutement() {
		if (valeur > 0) {
			getCible().getCaracPhysique().add(carac, valeur);
		} else {
			getCible().getCaracPhysique().supp(carac, valeur);
		}
	}

	@Override
	public void actionFinEnvoutement() {
		if (valeur > 0) {
			getCible().getCaracPhysique().supp(carac, valeur);
		} else {
			getCible().getCaracPhysique().add(carac, valeur);
		}
	}

	@Override
	public void actionLancerEntite(Orientation oriLanceur, boolean ccritique) {
		
	}

	@Override
	public void lancerTuile(Tuile cible, EntiteVariable lanceur, Orientation oriLanceur, boolean ccritique) {

	}

}
