/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.sort.TypeCible;
import Serializable.InCombat.TypeCarac;
import Combat.entite.Entite;
import Combat.map.Tuile;

/**
 * AlterationCarac.java
 *
 */
public class AlterationCarac extends Effet {

	public final TypeCarac carac;
	public final int valeur;

	public AlterationCarac(TypeCible cible, TypeCarac carac, int valeur) {
		super(cible);
		this.carac = carac;
		this.valeur = valeur;
	}

	public boolean isBonus() {
		return valeur >= 0;
	}

	@Override
	public void affecter(Tuile tuile, Entite entite, Entite lanceur) {
		switch (cible) {
			case ENTITE:
				if (entite != null) {
					entite.caracs.get(carac).actu += valeur;
				}
				break;
			case LANCEUR:
				entite.caracs.get(carac).actu += valeur;
				break;
		}
	}

}
