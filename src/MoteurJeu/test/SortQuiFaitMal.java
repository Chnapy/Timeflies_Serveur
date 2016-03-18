/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.Carac;
import MoteurJeu.gameplay.effet.Balus;
import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.sort.Niveau;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.zone.Carre;
import MoteurJeu.gameplay.sort.zone.ZoneAction;


/**
 * SortQuiFaitMal.java
 *
 */
public class SortQuiFaitMal extends SortActif {

	private final static String NOM = "SortQuiFaitMal";
	private final static String DESCRIPTION = "Ca fait mal";
	private final static int INDEX = 1;

	public SortQuiFaitMal() {
		super(
				NOM,
				DESCRIPTION,
				new Niveau(0),
				new Effet[]{
					new Effet(new Declencheur[]{
						new Balus(Carac.VITALITE, -30)
					})
				},
				new ZoneAction(new Carre(2, true), new Carre(1, false)),
				new ZoneAction(new Carre(1, true)),
				INDEX,
				2000,
				0,
				2);
	}

}