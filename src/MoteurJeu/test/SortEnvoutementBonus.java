/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.zone.Carre;
import MoteurJeu.gameplay.sort.zone.ZoneAction;

/**
 * SortEnvoutementBonus.java
 *
 */
public class SortEnvoutementBonus extends SortActif {

	public SortEnvoutementBonus(int id, int idClasseEntite, String nom, String description, int tempsAction, int cooldown, int fatigue) {
		super(id, idClasseEntite,
				nom,
				description, 
				new Effet[]{
					new Effet(new Declencheur[]{
						new EnvoutementQuiDonneUnBonus()
					})
				},
				new ZoneAction(new Carre(2, true)),
				new ZoneAction(new Carre(1, true)),
				2,
				tempsAction,
				cooldown,
				fatigue);
	}

}
