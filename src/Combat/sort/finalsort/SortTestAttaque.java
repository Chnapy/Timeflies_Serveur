/*
 * 
 * 
 * 
 */
package Combat.sort.finalsort;

import Serializable.InCombat.TypeCarac;
import Combat.sort.classe.SortActif;
import Combat.sort.TypeCible;
import Combat.sort.effet.AlterationCarac;
import Combat.sort.effet.Effet;
import Serializable.InCombat.zone.Carre;
import Serializable.InCombat.zone.Zone;

/**
 * SortTestAttaque.java
 *
 */
public class SortTestAttaque extends SortActif {

	public SortTestAttaque(int idClasseSort, int idClasseEntite, int tempsAction, int _cooldown, int _fatigue) {
		super(idClasseSort, idClasseEntite,
				new Effet[]{
					new AlterationCarac(TypeCible.ENTITE, TypeCarac.VITALITE, 30)
				},
				new Zone(new Carre(6, true)),
				new Zone(new Carre(2, true)),
				tempsAction, _cooldown, _fatigue);
	}

}
