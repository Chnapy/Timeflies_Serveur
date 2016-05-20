/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.core;

import MoteurJeu.gameplay.entite.variable.PersonnageVariable;

/**
 * Joueur.java
 * Représente un joueur.
 *
 */
public class Joueur {

	//Id du joueur
	private final int id;

	//Pseudo
	private String pseudo;

	//Tableau des personnages
	private PersonnageVariable[] personnages;

	/**
	 *
	 * @param _id
	 * @param pseud
	 * @param personnages
	 */
	public Joueur(int _id, String pseud, PersonnageVariable[] personnages) {
		id = _id;
		pseudo = pseud;
		this.personnages = personnages;
	}

	public int getId() {
		return id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public PersonnageVariable[] getPersonnages() {
		return personnages;
	}

}
