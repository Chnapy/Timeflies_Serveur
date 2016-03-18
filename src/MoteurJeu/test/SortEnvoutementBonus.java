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
 * SortEnvoutementBonus.java
 * 
 */
public class SortEnvoutementBonus extends SortActif {

	public SortEnvoutementBonus() {
		super("SortEnvoutementEffet", 
				"Un sort qui donne un envoutement bonus", 
				new Niveau(0), 
				new Effet[] {
					new Effet(new Declencheur[]{
						new EnvoutementQuiDonneUnBonus()
					})
				}, 
				new ZoneAction(new Carre(2, true)), 
				new ZoneAction(new Carre(1, true)), 
				2, 
				1500,
				1,
				1);
	}

}
