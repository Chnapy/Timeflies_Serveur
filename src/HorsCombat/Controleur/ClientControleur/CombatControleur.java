/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.ClientControleur;

import HorsCombat.Modele.Reseau.Client;
import Main.ClientControleur;
import Serializable.InCombat.InCombat;
import Serializable.InCombat.LancerSort;

/**
 * CombatControleur.java
 * 
 */
public class CombatControleur extends ClientControleur<InCombat> {

	public CombatControleur(Client client) {
		super(client);
	}

	@Override
	public void receiveFromClient(InCombat pack) {
		if(pack instanceof LancerSort) {
			client.combat.recepLancerSort((LancerSort) pack, client);
		}
	}

}
