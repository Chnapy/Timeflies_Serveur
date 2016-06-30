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
import Serializable.InCombat.TypeCarac;
import Combat.sort.effet.Effet;

/**
 * SortPassifTest.java
 * Boost la vitesse permanent
 *
 */
public class SortPassifTest extends SortPassif {

	public SortPassifTest(int id, int idClasseEntite) {
		super(id, idClasseEntite, new Declencheur[]{
			Declencheur.DEBUT_COMBAT
		}, new Effet[] {
			new AlterationCarac(TypeCible.ENTITE, TypeCarac.VITESSE, 10)
		});
	}

}
