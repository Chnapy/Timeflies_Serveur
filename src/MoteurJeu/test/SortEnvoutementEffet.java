/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.sort.Niveau;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.zone.Carre;
import MoteurJeu.gameplay.sort.zone.ZoneAction;

/**
 * SortEnvoutementEffet.java
 *
 */
public class SortEnvoutementEffet extends SortActif {

	public SortEnvoutementEffet() {
		super("SortEnvoutementEffet",
				"Un sort qui donne un envoutement effet",
				new Niveau(0),
				new Effet[]{
					new Effet(new Declencheur[]{
						new EnvoutementQuiSeDeclenche()
					})
				},
				new ZoneAction(new Carre(2, true)),
				new ZoneAction(new Carre(0, true)),
				3,
				1500,
				1,
				4);
	}

}
