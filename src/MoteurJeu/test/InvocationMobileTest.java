/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.invocation.ClasseInvocationMobile;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;

/**
 * InvocationMobileTest.java
 * 
 */
public class InvocationMobileTest extends ClasseInvocationMobile {

	public InvocationMobileTest(int id, String nomClasse, CaracteristiquePhysiqueMax caracPhysique) {
		super(id,
				nomClasse, 
				caracPhysique);
	}

	@Override
	public void invoquer(GridPoint2 point) {
	}

	@Override
	public boolean canDeclencher(Effet effet, int min, int max) {
		return false;
	}

	@Override
	public void lancerEntite(EntiteVariable cible, Orientation oriLanceur, boolean ccritique) {
	}

	@Override
	public void lancerTuile(Tuile cible, EntiteVariable lanceur, Orientation oriLanceur, boolean ccritique) {
	}

}