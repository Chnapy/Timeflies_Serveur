/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.Carac;
import MoteurJeu.gameplay.effet.Balus;
import MoteurJeu.gameplay.effet.Declenchable;
import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.envoutement.EnvoutementEffets;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.Orientation;

/**
 * EnvoutementQuiSeDeclenche.java
 *
 */
public class EnvoutementQuiSeDeclenche extends EnvoutementEffets {

	public EnvoutementQuiSeDeclenche() {
		super(
				"EnvoutementQuiSeDeclenche",
				3,
				new Declenchable[]{
					new DeclenchablePourEnvoutement()
				},
				new Effet[]{
					new Effet(new Declencheur[]{
						new Balus(Carac.VITALITE, 60)
					})
				}
		);
	}

	@Override
	public void actionDebutTour() {
	}

	@Override
	public void actionFinTour() {
	}

	@Override
	public void actionDebutTourGlobal() {
	}

	@Override
	public void actionFinTourGlobal() {
	}

	@Override
	public void actionDebutEnvoutement() {
	}

	@Override
	public void actionFinEnvoutement() {
	}

	@Override
	public void actionLancerEntite(Orientation oriLanceur, boolean ccritique) {
	}

	@Override
	public void lancerTuile(Tuile cible, EntiteVariable lanceur, Orientation oriLanceur, boolean ccritique) {
	}

	@Override
	protected void actionApplyEffect(EntiteVariable lanceur, boolean isAvant, boolean ccritique) {
		if (isAvant) {
			return;
		}
		getCible().recoitSort(getListEffets(), lanceur, Orientation.NORD, ccritique);
	}

}
