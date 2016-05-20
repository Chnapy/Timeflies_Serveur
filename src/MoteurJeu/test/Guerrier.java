/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.entite.classe.ClasseEntiteActive;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;

/**
 * Guerrier.java
 * CLASSE DE TEST
 *
 */
public class Guerrier extends ClasseEntiteActive {

	private static final int INDEX_TEXTURE = 0;
	private static final int INDEX_TEXTURE_TIMELINE = 0;

	public Guerrier(int id, String nomClasse, String nom, CaracteristiquePhysiqueMax caracPhysique, SortPassif[] sortsPassifs, SortActif[] sortsActifs) {
		super(id,
				nomClasse,
				caracPhysique
				//				new SortPassif[]{
				//					new SortPassifBonusVitesseAction(),
				//					new SortPassifEffetSoin()
				//				},
				//				new SortActif[]{
				//					new SortQuiFaitMal(),
				//					new SortEnvoutementBonus(),
				//					new SortEnvoutementEffet(),
				//					new SortInvocationPassive(),
				//					new SortInvocationActive()
				//				}
		);
	}

}
