/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.entite.Entite;
import Combat.map.Tuile;
import Combat.sort.TypeCible;

/**
 * EffetTour.java
 *
 */
public class EffetTour {

	public static class EfDebutCombat extends Effet {

		public EfDebutCombat() {
			super(TypeCible.ENTITE);
		}

		@Override
		public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		}

	}

	public static class EfDebutTourGlobal extends Effet {

		public EfDebutTourGlobal() {
			super(TypeCible.ENTITE);
		}

		@Override
		public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		}

	}

	public static class EfFinTourGlobal extends Effet {

		public EfFinTourGlobal() {
			super(TypeCible.ENTITE);
		}

		@Override
		public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		}

	}

	public static class EfDebutTour extends Effet {

		public EfDebutTour() {
			super(TypeCible.ENTITE);
		}

		@Override
		public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		}

	}

	public static class EfFinTour extends Effet {

		public EfFinTour() {
			super(TypeCible.ENTITE);
		}

		@Override
		public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		}

	}

}
