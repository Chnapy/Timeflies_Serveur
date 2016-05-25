/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.sort.TypeCible;
import Combat.TypeCarac;

/**
 * AlterationCarac.java
 * 
 */
public class AlterationCarac extends Effet {

	private static final long serialVersionUID = -4179764516857318782L;
	
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

}
