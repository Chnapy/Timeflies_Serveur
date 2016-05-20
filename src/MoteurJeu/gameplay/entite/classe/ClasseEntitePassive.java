/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.entite.classe;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.sort.SortPassif;

/**
 * ClasseEntitePassive.java
 * Représente une entité passive (autonome).
 *
 */
public class ClasseEntitePassive extends ClasseEntite {

	/**
	 *
	 * @param id
	 * @param n
	 * @param sortsPassifs
	 * @param cPhysique
	 */
	public ClasseEntitePassive(int id, String n,
			SortPassif[] sortsPassifs, CaracteristiquePhysiqueMax cPhysique) {

		super(id, n, sortsPassifs, cPhysique);
	}

	public ClasseEntitePassive(int id, String n, CaracteristiquePhysiqueMax cPhysique) {

		super(id, n, cPhysique);
	}

}
