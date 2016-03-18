/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.invocation;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.entite.EntiteActive;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.general.Orientation;

/**
 * InvocationMobile.java
 * Représente une invocation pouvant être controlée par un joueur.
 *
 */
public abstract class InvocationMobile extends EntiteActive implements Invocation {

	/**
	 *
	 * @param nom
	 * @param posX
	 * @param posY
	 * @param orientation
	 * @param sortsPassifs
	 * @param sortsActifs
	 * @param cPhysique
	 * @param indexTexture
	 * @param iTextureTimeline
	 */
	public InvocationMobile(String nom,
			int posX, int posY, Orientation orientation,
			SortPassif[] sortsPassifs,
			SortActif[] sortsActifs,
			CaracteristiquePhysique cPhysique,
			int indexTexture,
			int iTextureTimeline) {

		super(nom, posX, posY, orientation, cPhysique, sortsPassifs, sortsActifs, indexTexture, iTextureTimeline);

	}

}
