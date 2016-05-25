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
import Combat.sort.zone.Carre;
import Combat.sort.zone.Zone;

/**
 * SortTestEnvoutement.java
 * Donne à une entité un envoutement renvoyant des dommages
 *
 */
public class SortTestEnvoutement extends SortActif {

	private static final long serialVersionUID = -8193941312929700532L;

	public SortTestEnvoutement(int idClasseSort, int idClasseEntite, String nom, String description, int tempsAction, int _cooldown, int _fatigue) {
		super(idClasseSort, idClasseEntite, nom, description,
				new Effet[]{
					new AddEnvoutement(TypeCible.ENTITE, new EnvoutementTestEffet())
				},
				new Zone(new Carre(4, true)),
				new Zone(new Carre(1, true)),
				tempsAction, _cooldown, _fatigue);
	}

}
