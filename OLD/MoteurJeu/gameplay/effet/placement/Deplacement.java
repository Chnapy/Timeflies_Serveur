package MoteurJeu.gameplay.effet.placement;

import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import Combat.entite.classe.ClasseEntite;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.Orientation;

/**
 * Permet d'atirer/pousser un joueur
 *
 */
public class Deplacement extends Placement {

	//Nombre de tuiles déplacées
	private int nombre;

	public Deplacement(int nombre) {
		this.nombre = nombre;
	}

	public int getNombre() {
		return nombre;
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
		Deplacement other = (Deplacement) obj;
		return nombre == other.nombre;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + this.nombre;
		return hash;
	}

	@Override
	public boolean canDeclencher(Effet effet, int min, int max) {
		for (Declencheur declencheur : effet.getDeclencheur()) {
			if (declencheur instanceof Deplacement && ((Deplacement) declencheur).nombre <= min && ((Deplacement) declencheur).nombre >= max) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void lancerEntite(EntiteVariable victime, Orientation oriLanceur, boolean ccritique) {
	}

	@Override
	public void lancerTuile(Tuile cible, EntiteVariable lanceur, Orientation oriLanceur, boolean ccritique) {
//		System.out.println(lanceur.getCaracSpatiale().getPosition());
		switch (oriLanceur) {
			case EST:
				lanceur.move(nombre, 0);
				break;
			case NORD:
				lanceur.move(0, -nombre);
				break;
			case OUEST:
				lanceur.move(-nombre, 0);
				break;
			case SUD:
				lanceur.move(0, nombre);
				break;
			default:
				throw new Error("direction non gérée.");
		}
	}

}
