/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.effet.placement;

import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import Combat.entite.classe.ClasseEntite;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.general.Orientation;

/**
 * Placement.java
 * Gère les mouvements et autres modifications de placement.
 *
 */
public abstract class Placement implements Declencheur {

	/**
	 *
	 */
	public Placement() {
	}

	/**
	 * Equals en fonction de la caracSpatiale
	 *
	 * @return
	 */
	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract boolean canDeclencher(Effet effet, int min, int max);

	@Override
	public abstract void lancerEntite(EntiteVariable victime, Orientation oriLanceur, boolean ccritique);

}
