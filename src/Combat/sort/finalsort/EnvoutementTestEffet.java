/*
 * 
 * 
 * 
 */
package Combat.sort.finalsort;

import Combat.sort.effet.AlterationCarac;
import Combat.sort.effet.Effet;
import Combat.sort.envout.Envoutement;
import Combat.sort.TypeCible;
import Serializable.InCombat.TypeCarac;
import Combat.sort.declencheur.Declencheur;

/**
 * EnvoutementTestEffet.java
 * A chaque dommage recu, renvoi des dommages
 *
 */
public class EnvoutementTestEffet extends Envoutement {

	private static final long serialVersionUID = -8721142333478972324L;

	public EnvoutementTestEffet() {
		super(new Declencheur[]{
			(Effet effet) -> {
				return effet instanceof AlterationCarac
						&& ((AlterationCarac) effet).carac.equals(TypeCarac.VITALITE)
						&& !((AlterationCarac) effet).isBonus();
			}
		}, 3, new Effet[]{
			new AlterationCarac(TypeCible.ENTITE, TypeCarac.VITALITE, 5)
		});
	}

}
