/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.entite.variable;

import MoteurJeu.gameplay.entite.classe.ClasseEntiteActive;

/**
 * PersonnageVariable.java
 * 
 */
public class PersonnageVariable extends EntiteActiveVariable {
	
	public final String nomDonne;

	public PersonnageVariable(ClasseEntiteActive entite, String nomDonne, int initiative) {
		super(entite, initiative);
		this.nomDonne = nomDonne;
	}

}
