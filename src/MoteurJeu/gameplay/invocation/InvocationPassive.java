/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.invocation;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.entite.EntitePassive;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.general.Orientation;

/**
 * InvocationPassive.java
 * Représente une invocation autonome.
 *
 */
public abstract class InvocationPassive extends EntitePassive implements Invocation {

	/**
	 *
	 * @param n
	 * @param posX
	 * @param posY
	 * @param orient
	 * @param sortsPassifs
	 * @param cphysique
	 * @param indexTexture
	 */
	public InvocationPassive(String n,
			int posX, int posY, Orientation orient,
			SortPassif[] sortsPassifs, CaracteristiquePhysique cphysique,
			int indexTexture) {

		super(n, posX, posY, orient, sortsPassifs, cphysique, indexTexture);
	}

}
