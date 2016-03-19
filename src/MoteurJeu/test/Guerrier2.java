/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.entite.Personnage;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.general.Orientation;
import static MoteurJeu.general.Orientation.EST;

/**
 * Guerrier.java
 * CLASSE DE TEST
 *
 */
public class Guerrier2 extends Personnage {

	private static final int INDEX_TEXTURE = 1;
	private static final int INDEX_TEXTURE_TIMELINE = 1;

	public Guerrier2(CaracteristiquePhysique cp, SortPassif[] sp, SortActif[] sa) {
		this(
				"perso bleu", 
				0, 
				0, 
				EST,
				cp,
				sp,
				sa
		);
	}

	/**
	 *
	 * @param nom
	 * @param posX
	 * @param posY
	 * @param orientation
	 * @param cPhysique
	 * @param sortsPassifs
	 * @param sortsActifs
	 */
	public Guerrier2(String nom,
			int posX, int posY, Orientation orientation,
			CaracteristiquePhysique cPhysique,
			SortPassif[] sortsPassifs,
			SortActif[] sortsActifs) {

		super("Guerrier2", nom, posX, posY, orientation, cPhysique, sortsPassifs, sortsActifs, INDEX_TEXTURE, INDEX_TEXTURE_TIMELINE);
	}

}
