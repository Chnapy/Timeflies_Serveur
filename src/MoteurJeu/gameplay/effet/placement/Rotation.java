/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.effet.placement;

import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.Entite;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.Orientation;

/**
 * Rotation.java
 *
 */
public class Rotation extends Placement {

	public Rotation() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		return getClass() == obj.getClass();
	}

	@Override
	public boolean canDeclencher(Effet effet, int min, int max) {
//		System.out.println("declench");
		for (Declencheur declencheur : effet.getDeclencheur()) {
			if (declencheur instanceof Rotation) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void lancerEntite(Entite victime, Orientation oriLanceur, boolean ccritique) {
//		System.out.println(victime.getNom());
		victime.getCaracSpatiale().setOrientation(oriLanceur);
	}

	@Override
	public void lancerTuile(Tuile cible, Entite lanceur, Orientation oriLanceur, boolean ccritique) {
//		System.out.println("tuile");
	}

}
