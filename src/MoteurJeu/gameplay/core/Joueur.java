/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.core;

import MoteurJeu.gameplay.entite.Personnage;
import java.util.UUID;

/**
 * Joueur.java
 * Représente un joueur.
 *
 */
public class Joueur {

	//Id du joueur
	private final int id;
	
	private UUID token;

	//Pseudo
	private String pseudo;

	//Tableau des personnages
	private Personnage[] personnages;

	/**
	 *
	 * @param _id
	 * @param _token
	 * @param pseud
	 * @param personnages
	 */
	public Joueur(int _id, UUID _token, String pseud, Personnage[] personnages) {
		this(_id, _token, pseud);
		this.personnages = personnages;
	}
	
	public Joueur(int _id, UUID _token, String pseud) {
		id = _id;
		token = _token;
		pseudo = pseud;
	}

	public int getId() {
		return id;
	}

	public UUID getToken() {
		return token;
	}

	public String getPseudo() {
		return pseudo;
	}

	public Personnage[] getPersonnages() {
		return personnages;
	}

}
