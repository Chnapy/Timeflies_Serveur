package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.Entite;
import MoteurJeu.gameplay.invocation.InvocationPassive;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;

public class InvocationPassiveTest extends InvocationPassive {

	public InvocationPassiveTest() {
		super("Invoc. passive",
				0,
				0,
				Orientation.NORD,
				new SortPassif[]{
					new SortPassifEffetSoin()
				},
				new CaracteristiquePhysique(110, 0, 1500, 0, 100),
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

	@Override
	public void jouerTour(long time) {
	}

}
