/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.entite.classe.ClasseEntiteActive;

/**
 * Guerrier.java
 * CLASSE DE TEST
 *
 */
public class Guerrier2 extends ClasseEntiteActive {

	private static final int INDEX_TEXTURE = 1;
	private static final int INDEX_TEXTURE_TIMELINE = 1;

	public Guerrier2(int id, String nomClasse, CaracteristiquePhysiqueMax caracPhysique) {
		super(id,
				nomClasse,
				caracPhysique
		);
	}

}
