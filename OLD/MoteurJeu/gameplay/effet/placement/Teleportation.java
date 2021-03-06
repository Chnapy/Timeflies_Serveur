package MoteurJeu.gameplay.effet.placement;

import MoteurJeu.caracteristique.CaracteristiqueSpatiale;
import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import Combat.entite.classe.ClasseEntite;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;

/**
 * Effet de téléportation (effet de placement)
 * Téléporte le joueur au coordonée mises dans le constructeur
 *
 */
public class Teleportation extends Placement {

	//Position cible
	private GridPoint2 position;

	public Teleportation(GridPoint2 pos) {
		this.position = pos;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Teleportation other = (Teleportation) obj;
		if (position == null) {
			if (other.getPosition() != null) {
				return false;
			}
		} else if (!position.equals(other.getPosition())) {
			return false;
		}
		return true;
	}

	public GridPoint2 getPosition() {
		return position;
	}

	@Override
	public boolean canDeclencher(Effet effet, int min, int max) {
		for (Declencheur declencheur : effet.getDeclencheur()) {
			if (declencheur instanceof Teleportation) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void lancerEntite(EntiteVariable victime, Orientation oriLanceur, boolean ccritique) {
		victime.setCaracSpatiale(new CaracteristiqueSpatiale((int) position.x, (int) position.y, victime.getCaracSpatiale().getOrientation()));
	}

	@Override
	public void lancerTuile(Tuile cible, EntiteVariable lanceur, Orientation oriLanceur, boolean ccritique) {
		lanceur.setPosition(cible.getPosition());
	}

}
