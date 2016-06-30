/*
 * 
 * 
 * 
 */
package Combat.sort.base;

import Combat.sort.effet.Effet;
import Combat.sort.classe.SortActif;
import static Combat.sort.TypeCible.LANCEUR;
import Combat.sort.effet.Teleportation;
import Serializable.InCombat.zone.Carre;
import Serializable.InCombat.zone.Zone;

/**
 * Deplacer.java
 *
 */
public class Deplacer extends SortActif {

	private static final int TEMPS_ACTION = 500;

	public Deplacer() {
		super(-1, -1,
				new Effet[]{
					new Teleportation(LANCEUR)
				},
				new Zone(new Carre(0, true)),
				new Zone(new Carre(0, true)),
				TEMPS_ACTION,
				0,
				0);
	}

}
