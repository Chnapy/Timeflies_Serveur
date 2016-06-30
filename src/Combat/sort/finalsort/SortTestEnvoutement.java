/*
 * 
 * 
 * 
 */
package Combat.sort.finalsort;

import Combat.sort.classe.SortActif;
import Combat.sort.effet.AddEnvoutement;
import Combat.sort.effet.Effet;
import Combat.sort.TypeCible;
import Serializable.InCombat.zone.Carre;
import Serializable.InCombat.zone.Zone;

/**
 * SortTestEnvoutement.java
 * Donne à une entité un envoutement renvoyant des dommages
 *
 */
public class SortTestEnvoutement extends SortActif {

	public SortTestEnvoutement(int idClasseSort, int idClasseEntite, int tempsAction, int _cooldown, int _fatigue) {
		super(idClasseSort, idClasseEntite,
				new Effet[]{
					new AddEnvoutement(TypeCible.ENTITE, new EnvoutementTestEffet())
				},
				new Zone(new Carre(4, true)),
				new Zone(new Carre(1, true)),
				tempsAction, _cooldown, _fatigue);
	}

}
