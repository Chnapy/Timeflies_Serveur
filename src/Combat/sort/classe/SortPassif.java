/*
 * 
 * 
 * 
 */
package Combat.sort.classe;

import Combat.sort.TypeCible;
import Combat.sort.declencheur.Declencheur;
import Combat.sort.effet.AddEnvoutement;
import Combat.sort.effet.Effet;
import Combat.sort.envout.Envoutement;

/**
 * SortPassif.java
 * Représente un sort donnant un bonus permanent, ou pouvant être déclenché.
 *
 */
public abstract class SortPassif extends Sort {

	public SortPassif(int id, int idClasseEntite, Declencheur[] declencheurs, Effet[] effets) {

		super(id, idClasseEntite, new Effet[]{
			new AddEnvoutement(TypeCible.LANCEUR,
			new Envoutement(declencheurs, -1, effets))
		});

	}

}
