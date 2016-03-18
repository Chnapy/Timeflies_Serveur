/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.Entite;
import MoteurJeu.gameplay.invocation.InvocationMobile;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;

/**
 * InvocationMobileTest.java
 * 
 */
public class InvocationMobileTest extends InvocationMobile {

	public InvocationMobileTest() {
		super("Invoc. mobile", 
				0, 
				0, 
				Orientation.NORD, 
				new SortPassif[]{}, 
				new SortActif[] {
					new SortQuiFaitMal()
				}, 
				new CaracteristiquePhysique(110, 13000, 1500, 0, 100),
				1, 
				1);
	}

	@Override
	public void invoquer(GridPoint2 point) {
		getCaracSpatiale().getPosition().set(point);
	}

	@Override
	public boolean canDeclencher(Effet effet, int min, int max) {
		return false;
	}

	@Override
	public void lancerEntite(Entite cible, Orientation oriLanceur, boolean ccritique) {
	}

	@Override
	public void lancerTuile(Tuile cible, Entite lanceur, Orientation oriLanceur, boolean ccritique) {
	}

}