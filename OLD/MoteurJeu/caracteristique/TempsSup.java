/*
 * 
 * 
 * 
 */
package MoteurJeu.caracteristique;

/**
 * TempsSup.java
 * Gère le temps supplémentaire de l'entité.
 * Utilisé lorsqu'une action utilise plus que le temps d'action disponible.
 *
 */
public class TempsSup extends Caracteristique {

	/**
	 *
	 * @param total
	 */
	public TempsSup(int total) {
		super(total, total);
	}

	/**
	 * equals en fontion du type de la classe (TempsSup)
	 */
	@Override
	public boolean equals(Object o) {
		return o instanceof TempsSup;
	}
}
