/*
 * 
 * 
 * 
 */
package Main;

import HorsCombat.Modele.Reseau.Client;
import java.io.Serializable;

/**
 * ClientControleur.java
 * 
 * @param <P>
 */
public abstract class ClientControleur<P extends Serializable> {
	
	protected final Client client;
	
	public ClientControleur(Client client) {
		this.client = client;
	}
	
	public abstract void receiveFromClient(P pack);

}
