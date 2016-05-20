/*
 * 
 * 
 * 
 */
package CombatHandler;

import MoteurJeu.gameplay.core.Joueur;
import MoteurJeu.gameplay.entite.variable.PersonnageVariable;
import MoteurJeu.gameplay.map.Map;
import gameplay.map.MapSerializable;
import java.io.File;

/**
 * CombatStartPack.java
 *
 */
public class CombatStartPack {

	public final Map map;
	public final Joueur[] joueurs;

	public CombatStartPack(Map map, Joueur[] joueurs) {
		this.map = map;
		this.joueurs = joueurs;
	}

	public static CombatStartPack getTestPack() {

		Map map = getTestMap();

		PersonnageVariable[] persosJ1 = {
//			new Guerrier()
		};
		PersonnageVariable[] persosJ2 = {
//			new Guerrier2()
		};
		Joueur[] joueurs = {
			new Joueur(5, "J1", persosJ1),
			new Joueur(6, "J2", persosJ2)
		};

		return new CombatStartPack(map, joueurs);
	}
	
	public static Map getTestMap() {
		MapSerializable smap = Map.getMapSerializable(new File("test.tfmap"));
		Map map = new Map(smap);
		return map;
	}

}
