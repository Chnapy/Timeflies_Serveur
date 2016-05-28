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

	private static final long serialVersionUID = 4143560593368171106L;

	public SortTestTP(int idClasseSort, int idClasseEntite, String nom, String description, int tempsAction, int _cooldown, int _fatigue) {
		super(idClasseSort, idClasseEntite,
				nom, description,
				new Effet[]{
					new Teleportation(TypeCible.LANCEUR)
				},
				new Zone(new Carre(5, true)),
				new Zone(new Carre(1, true)),
				tempsAction, _cooldown, _fatigue);
	}

}
