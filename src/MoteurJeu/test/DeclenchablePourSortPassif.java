package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.Carac;
import MoteurJeu.gameplay.effet.Balus;
import MoteurJeu.gameplay.effet.Declenchable;

/*
 * 
 * 
 * 
 */

/**
 * DeclenchablePourSortPassif.java
 * 
 */
public class DeclenchablePourSortPassif extends Declenchable {

	public DeclenchablePourSortPassif() {
		super(new Balus(Carac.VITALITE, -1), -999, -30);
	}

}
