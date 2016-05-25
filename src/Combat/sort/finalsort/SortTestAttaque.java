/*
 * 
 * 
 * 
 */
package Combat.sort.finalsort;

import Combat.TypeCarac;
import Combat.sort.classe.SortActif;
import Combat.sort.TypeCible;
import Combat.sort.effet.AlterationCarac;
import Combat.sort.effet.Effet;
import Combat.sort.zone.Carre;
import Combat.sort.zone.Zone;

/**
 * SortTestAttaque.java
 *
 */
public class SortTestAttaque extends SortActif {

	private static final long serialVersionUID = -4713545234490381938L;

	public SortTestAttaque(int idClasseSort, int idClasseEntite, String nom, String description, int tempsAction, int _cooldown, int _fatigue) {
		super(idClasseSort, idClasseEntite,
				nom, description,
				new Effet[]{
					new AlterationCarac(TypeCible.ENTITE, TypeCarac.VITALITE, 30)
				},
				new Zone(new Carre(6, true)),
				new Zone(new Carre(2, true)),
				tempsAction, _cooldown, _fatigue);
	}

}
