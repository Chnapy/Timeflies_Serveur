/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.entite;

import MoteurJeu.general.Mode;
import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.general.Orientation;

/**
 * EntitePassive.java
 * Représente une entité passive (autonome).
 *
 */
public abstract class EntitePassive extends Entite {

	/**
	 *
	 * @param n
	 * @param posX
	 * @param posY
	 * @param orient
	 * @param sortsPassifs
	 * @param cPhysique
	 * @param indexTexture
	 */
	public EntitePassive(String n,
			int posX, int posY, Orientation orient,
			SortPassif[] sortsPassifs, CaracteristiquePhysique cPhysique,
			int indexTexture) {

		super(n, posX, posY, orient, sortsPassifs, cPhysique, indexTexture);
		this.niveauSymbol = new NiveauSymbolique(sortsPassifs);
	}

	@Override
	public Mode getEtatNow() {
		return getEtat();
	}

}