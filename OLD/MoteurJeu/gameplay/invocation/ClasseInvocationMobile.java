/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.invocation;

import MoteurJeu.caracteristique.CaracteristiquePhysiqueMax;
import Combat.entite.classe.ClasseEntiteActive;
import Combat.sort.SortActif;
import Combat.sort.SortPassif;

/**
 * ClasseInvocationMobile.java
 Représente une invocation pouvant être controlée par un joueur.
 *
 */
public abstract class ClasseInvocationMobile extends ClasseEntiteActive implements Invocation {

	/**
	 *
	 * @param id
	 * @param nom
	 * @param sortsPassifs
	 * @param sortsActifs
	 * @param cPhysique
	 */
	public ClasseInvocationMobile(int id, String nom,
			SortPassif[] sortsPassifs,
			SortActif[] sortsActifs,
			CaracteristiquePhysiqueMax cPhysique) {

		super(id, nom, cPhysique, sortsPassifs, sortsActifs);

	}
	
	public ClasseInvocationMobile(int id, String nom,
			CaracteristiquePhysiqueMax cPhysique) {

		super(id, nom, cPhysique);

	}

}
