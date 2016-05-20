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
 * SortEnvoutementEffet.java
 *
 */
public class SortEnvoutementEffet extends SortActif {

	public SortEnvoutementEffet(int id, int idClasseEntite, String nom, String description, int tempsAction, int cooldown, int fatigue) {
		super(id, idClasseEntite,
				nom,
				description,
				new Effet[]{
					new Effet(new Declencheur[]{
						new EnvoutementQuiSeDeclenche()
					})
				},
				new ZoneAction(new Carre(2, true)),
				new ZoneAction(new Carre(0, true)),
				3,
				tempsAction,
				cooldown,
				fatigue);
	}

}
