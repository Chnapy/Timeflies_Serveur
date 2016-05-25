/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.sort.TypeCible;
import Serializable.InCombat.InCombat;

/**
 * SEffet.java
 * 
 */
public abstract class Effet implements InCombat {

	private static final long serialVersionUID = 1483283495961811201L;
	
	public final TypeCible cible;

	public Effet(TypeCible cible) {
		this.cible = cible;
	}

}
