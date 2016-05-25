/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur;

import HorsCombat.Controleur.Matchmaking.Matchmaking;
import HorsCombat.Modele.HCModele;

/**
 * HCControleur.java
 * 
 */
public class HCControleur {

	public static void init() {
		HCModele.init();
		Matchmaking.init();
	}
	
}
