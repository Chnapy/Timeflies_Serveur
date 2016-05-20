/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.invocation;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.entite.classe.ClasseEntitePassive;
import MoteurJeu.gameplay.sort.SortPassif;

/**
 * ClasseInvocationPassive.java
 * Représente une invocation autonome.
 *
 */
public abstract class ClasseInvocationPassive extends ClasseEntitePassive implements Invocation {

	/**
	 *
	 * @param id
	 * @param n
	 * @param sortsPassifs
	 * @param cphysique
	 */
	public ClasseInvocationPassive(int id, String n,
			SortPassif[] sortsPassifs, CaracteristiquePhysiqueMax cphysique) {

		super(id, n, sortsPassifs, cphysique);
	}

	public ClasseInvocationPassive(int id, String n, CaracteristiquePhysiqueMax cphysique) {

		super(id, n, cphysique);
	}

}
