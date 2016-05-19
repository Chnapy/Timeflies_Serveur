/*
 * 
 * 
 * 
 */
package Serializable.Combat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * InfosCombat.java
 *
 */
public abstract class InfosCombat implements Serializable {

	private static final long serialVersionUID = 625782106078776094L;

	public static class PartieTrouvee extends InfosCombat {

		private static final long serialVersionUID = 3135149435124340248L;

		public final ArrayList<String> pseudos;
		public final ArrayList<Integer> nbrsPersos;

		public PartieTrouvee(ArrayList<String> pseudos, ArrayList<Integer> nbrsPersos) {
			this.pseudos = pseudos;
			this.nbrsPersos = nbrsPersos;
		}
	}

	public static class NewJoueur extends InfosCombat {

		private static final long serialVersionUID = 3131655523732648959L;

		public final String pseudo;
		public final int nbrPersos;

		public NewJoueur(String pseudo, int nbrPersos) {
			this.pseudo = pseudo;
			this.nbrPersos = nbrPersos;
		}
	}

	public static class LancementCombat extends InfosCombat {

		private static final long serialVersionUID = 1147765223531782044L;

		public LancementCombat() {
		}
	}

}
