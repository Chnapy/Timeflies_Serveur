/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.entite.classe;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.gameplay.sort.base.Deplacer;
import MoteurJeu.gameplay.sort.base.Orienter;

/**
 * ClasseEntiteActive.java
 * Représente une entité active (controlable par un joueur).
 * Joue son tour.
 *
 */
public class ClasseEntiteActive extends ClasseEntite {

	//Tableau des sorts actifs
	public SortActif[] tabSortActif;

	public final Orienter orienter;

	public final Deplacer deplacer;

	/**
	 *
	 * @param id
	 * @param nom
	 * @param cPhysique
	 */
	public ClasseEntiteActive(int id, String nom,
			CaracteristiquePhysiqueMax cPhysique) {

		super(id, nom, cPhysique);

		orienter = new Orienter();
		deplacer = new Deplacer();
	}

	public ClasseEntiteActive(int id, String nom,
			CaracteristiquePhysiqueMax cPhysique,
			SortPassif[] sortsPassifs,
			SortActif[] sortsActifs) {

		super(id, nom, sortsPassifs, cPhysique);

		tabSortActif = sortsActifs;
		orienter = new Orienter();
		deplacer = new Deplacer();
	}
	
	public void setTabSortActif(SortActif[] tab) {
		this.tabSortActif = tab;
	}

}
