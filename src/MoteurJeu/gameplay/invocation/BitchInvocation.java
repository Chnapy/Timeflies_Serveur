package MoteurJeu.gameplay.invocation;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.Entite;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;
import static MoteurJeu.general.Orientation.NORD;

public class BitchInvocation extends InvocationMobile {

	public BitchInvocation() {
		super("Bitchator", 0, 0, NORD, new SortPassif[]{}, new SortActif[]{},
				new CaracteristiquePhysique(110, 12000, 1500, 0, 100),
				0, 0);
	}

	@Override
	public void invoquer(GridPoint2 point) {

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
