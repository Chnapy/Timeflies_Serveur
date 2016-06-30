/*
 * 
 * 
 * 
 */
package Combat.sort.finalsort;

import Combat.sort.classe.SortActif;
import Combat.sort.TypeCible;
import Combat.sort.effet.Effet;
import Combat.sort.effet.Teleportation;
import Serializable.InCombat.zone.Carre;
import Serializable.InCombat.zone.Zone;

/**
 * SortTestTP.java
 *
 */
public class SortTestTP extends SortActif {

	public SortTestTP(int idClasseSort, int idClasseEntite, int tempsAction, int _cooldown, int _fatigue) {
		super(idClasseSort, idClasseEntite,
				new Effet[]{
					new Teleportation(TypeCible.LANCEUR)
				},
				new Zone(new Carre(5, true)),
				new Zone(new Carre(1, true)),
				tempsAction, _cooldown, _fatigue);
	}

}
