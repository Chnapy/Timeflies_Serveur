/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.sort;

import MoteurJeu.gameplay.effet.Effet;

/**
 * SortPassif.java
 * Représente un sort donnant un bonus permanent, ou pouvant être déclenché.
 *
 */
public abstract class SortPassif extends Sort {

	/**
	 *
	 * @param id
	 * @param idClasseEntite
	 * @param nom
	 * @param description
	 * @param effets
	 * @param index
	 */
	public SortPassif(int id, int idClasseEntite, String nom, String description, Effet[] effets, int index) {

		super(id, idClasseEntite, nom, description, effets, index);

	}

}
