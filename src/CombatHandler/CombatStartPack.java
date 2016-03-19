/*
 * 
 * 
 * 
 */
package CombatHandler;

import MoteurJeu.gameplay.core.Joueur;
import MoteurJeu.gameplay.entite.Personnage;
import MoteurJeu.gameplay.map.Map;
import MoteurJeu.test.Guerrier;
import MoteurJeu.test.Guerrier2;
import gameplay.map.MapSerializable;
import java.io.File;
import java.util.UUID;

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

//	public static CombatStartPack getTestPack() {
//
//		MapSerializable smap = Map.getMapSerializable(new File("test.tfmap"));
//		Map map = new Map(smap);
//
//		Personnage[] persosJ1 = {
//			new Guerrier()
//		};
//		Personnage[] persosJ2 = {
//			new Guerrier2()
//		};
//		Joueur[] joueurs = {
//			new Joueur(5, new UUID(0, 0), "J1", persosJ1),
//			new Joueur(6, new UUID(0, 0), "J2", persosJ2)
//		};
//
//		return new CombatStartPack(map, joueurs);
//	}

}
