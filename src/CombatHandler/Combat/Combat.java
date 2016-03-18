/*
 * 
 * 
 * 
 */
package CombatHandler.Combat;

import CombatHandler.CombatStartPack;
import MoteurJeu.controleur.ControleurPrincipal;
import MoteurJeu.gameplay.core.Joueur;
import MoteurJeu.gameplay.map.Map;

/**
 * Combat.java
 * 
 */
public class Combat implements Runnable {
	
	private Map map;
	private Joueur[] joueurs;
	private ControleurPrincipal controleur;
	
	public Combat(CombatStartPack pack) {
		this.map = pack.map;
		this.joueurs = pack.joueurs;
		controleur = new ControleurPrincipal(map, joueurs);
	}

	@Override
	public void run() {
		controleur.lancer();
	}
	
	public void arret() {
		controleur.stop();
	}

}
