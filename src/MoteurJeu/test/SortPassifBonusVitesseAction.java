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
import MoteurJeu.gameplay.sort.SortPassifBonus;


/**
 * SortQuiFaitMal.java
 *
 */
public class SortPassifBonusVitesseAction extends SortPassifBonus {

	private final static String NOM = "SortPassifTest1";
	private final static String DESCRIPTION = "Donne un bonus de 10% en vitesse d'action.";
	private final static int INDEX = 1;

	public SortPassifBonusVitesseAction() {
		super(NOM,
				DESCRIPTION,
				new Niveau(0),
				new Effet[]{
					new Effet(new Declencheur[]{
						new Balus(Carac.VITESSEACTION, 10)
					})
				},
				INDEX);
	}

}