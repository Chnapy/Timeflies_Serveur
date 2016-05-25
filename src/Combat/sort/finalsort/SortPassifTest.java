/*
 * 
 * 
 * 
 */
package Combat.sort.finalsort;

import Combat.sort.classe.SortPassif;
import Combat.sort.declencheur.Declencheur;
import Combat.sort.effet.AlterationCarac;
import Combat.sort.TypeCible;
import Combat.TypeCarac;

/**
 * SortPassifTest.java
 * Boost la vitesse permanent
 *
 */
public class SortPassifTest extends SortPassif {

	private static final long serialVersionUID = 6678067439106254014L;

	public SortPassifTest(int id, int idClasseEntite, String nom, String description) {
		super(id, idClasseEntite, nom, description, Declencheur.DEBUT_COMBAT,
				new AlterationCarac(TypeCible.ENTITE, TypeCarac.VITESSE, 10));
	}

}
