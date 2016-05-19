/*
 * 
 * 
 * 
 */
package Serializable.Personnages.Sort;

import java.io.Serializable;

/**
 * Sort.java
 *
 */
public abstract class Sort implements Serializable {

	private static final long serialVersionUID = 3238941071137920942L;

	public final String nom;
	public final String description;
	public final Niveau niveau;

	public Sort(String nom, Niveau niveau, String description) {
		this.nom = nom;
		this.description = description;
		this.niveau = niveau;
	}

	public static class Niveau implements Serializable {

		private static final long serialVersionUID = -5702122673495080557L;

		public final int niveau;
		public final int xp;

		public Niveau(int niveau, int xp) {
			this.niveau = niveau;
			this.xp = xp;
		}
	}

}
