/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.Carac;
import MoteurJeu.gameplay.effet.Balus;
import MoteurJeu.gameplay.effet.Declenchable;


/**
 * DeclenchablePourEnvoutement.java
 * 
 */
public class DeclenchablePourEnvoutement extends Declenchable {

	public DeclenchablePourEnvoutement() {
		super(new Balus(Carac.VITALITE, -1), -40, -20);
	}

}
