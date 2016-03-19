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
public class Guerrier extends Personnage {

	private static final int INDEX_TEXTURE = 0;
	private static final int INDEX_TEXTURE_TIMELINE = 0;

	public Guerrier(CaracteristiquePhysique cp, SortPassif[] sp, SortActif[] sa) {
		this(
				"perso rouge",
				1,
				2,
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
	public Guerrier(String nom,
			int posX, int posY, Orientation orientation,
			CaracteristiquePhysique cPhysique,
			SortPassif[] sortsPassifs,
			SortActif[] sortsActifs) {

		super("Guerrier", nom, posX, posY, orientation, cPhysique, sortsPassifs, sortsActifs, INDEX_TEXTURE, INDEX_TEXTURE_TIMELINE);
	}

}
