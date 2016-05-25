/*
 * 
 * 
 * 
 */
package Combat.sort.effet;

import Combat.sort.envoutement.Envoutement;
import Combat.sort.TypeCible;

/**
 * AddEnvoutement.java
 * 
 */
public class AddEnvoutement extends Effet {

	private static final long serialVersionUID = -7103875622458501362L;
	
	private final Envoutement envoutement;

	public AddEnvoutement(TypeCible cible, Envoutement envoutement) {
		super(cible);
		this.envoutement = envoutement;
	}

}
