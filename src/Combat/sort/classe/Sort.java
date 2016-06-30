/*
 * 
 * 
 * 
 */
package Combat.sort.classe;

import Combat.sort.effet.Effet;

/**
 * Sort.java
 * Représente un sort, actif ou passif.
 *
 */
public abstract class Sort {

	//Id de la classe du sort
	public final int idClasseSort;
	
	//Id classe entité
	public final int idClasseEntite;

	//Tableau des effets
	public final Effet[] tabEffets;

	public Sort(int idClasseSort, int idClasseEntite, Effet[] effets) {
		this.idClasseSort = idClasseSort;
		this.idClasseEntite = idClasseEntite;
		tabEffets = effets;
	}

	public Effet[] getTabEffets() {
		return tabEffets;
	}
	
	public boolean isSortActif() {
		return this instanceof SortActif;
	}

}
