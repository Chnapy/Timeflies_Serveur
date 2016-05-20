/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.entite.classe;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.sort.SortPassif;

/**
 * ClasseEntite.java
 Représente une entité, visible en combat.
 *
 */
public abstract class ClasseEntite {
	
	//Id de la classe
	public final int id;

	//Caractéristiques physiques initiales de l'entité
	public final CaracteristiquePhysiqueMax caracPhysiqueMax;

	//Nom de l'entité
	public final String nomClasse;

	//Sorts passifs de l'entité
	public SortPassif[] tabSortPassif;

	/**
	 *
	 * @param id
	 * @param no
	 * @param caracPhysiqueMax
	 */
	public ClasseEntite(int id, String no, CaracteristiquePhysiqueMax caracPhysiqueMax) {
		this.id = id;
		nomClasse = no;
		this.caracPhysiqueMax = caracPhysiqueMax;
	}
	
	public ClasseEntite(int id, String no,
			SortPassif[] sortsPassifs, CaracteristiquePhysiqueMax caracPhysiqueMax) {
		this(id, no, caracPhysiqueMax);
		tabSortPassif = sortsPassifs;
	}
	
	public void setTabSortPassif(SortPassif[] tab) {
		this.tabSortPassif = tab;
	}
	
	public boolean isEntiteActif() {
		return this instanceof ClasseEntiteActive;
	}

}
