/*
 * 
 * 
 * 
 */
package CombatHandler.Combat;

import CombatHandler.CombatStartPack;
import Console.utils.ConsoleDisplay;
import Database.utils.DBMapper;
import MoteurJeu.controleur.ControleurPrincipal;
import MoteurJeu.gameplay.core.Joueur;
import MoteurJeu.gameplay.map.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		for (Joueur jr : joueurs) {
			addPersos(jr);
		}
		controleur = new ControleurPrincipal(map, joueurs);
	}

	@Override
	public void run() {
		controleur.lancer();
	}

	public void arret() {
		controleur.stop();
	}

	private void addPersos(Joueur jr) {
		int id = jr.getId();
		ResultSet res;
		try {
			res = DBMapper.executeQuery("select * from entite natural join classeentite natural join sort natural join classesort where idjoueur=?", DBMapper.QueryType.SELECT, id);
			while (res.next()) {

			}
		} catch (SQLException ex) {
			ConsoleDisplay.error("addPersos " + id);
		}
	}

}
