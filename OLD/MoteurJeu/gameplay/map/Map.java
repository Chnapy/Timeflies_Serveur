/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.map;

import MoteurJeu.general.Array;
import MoteurJeu.controleur.ControleurPrincipal;
import MoteurJeu.general.GridPoint2;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.HorsCombat.Map.TypeTuile;
import static Serializable.HorsCombat.Map.TypeTuile.TROU;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Map.java
 * Représente la map du combat.
 * Gère également le pathfinding.
 *
 */
public class Map {

	//Tableau des différentes tuiles de la map
	private final Tuile[][] tabTuiles;

	//Dimension x/y de la map
	private final Dimension dimension;

	/**
	 *
	 * @param mapPlan
	 */
	public Map(MapSerializable mapPlan) {
		TypeTuile[][] plan = mapPlan.tuiles;
		tabTuiles = new Tuile[plan.length][plan[0].length];
		dimension = new Dimension(plan.length, plan[0].length);
		init(plan);
	}

	/**
	 * Rempli la map depuis le plan.
	 * Génère les connections entre les tuiles pour le pathfinding.
	 *
	 * @param plan
	 */
	private void init(TypeTuile[][] plan) {
		int x, y;
		for (y = 0; y < plan.length; y++) {
			for (x = 0; x < plan[0].length; x++) {
				tabTuiles[y][x] = new Tuile(plan[y][x], new GridPoint2(x, y), y * plan[0].length + x);
			}
		}
	}

	public void setControleur(ControleurPrincipal controleur) {
		for (Tuile[] colonne : tabTuiles) {
			for (Tuile tuile : colonne) {
				tuile.setControleur(controleur);
			}
		}
	}

	/**
	 * Rends la tuile ciblée occupé ou non
	 *
	 * @param occupe
	 * @param y
	 * @param x
	 */
	public void setTuileOccupe(boolean occupe, int y, int x) {
		tabTuiles[y][x].setOccupe(occupe);
	}

	/**
	 * Récupère les tuiles ciblées dans une zone précise
	 *
	 * @param zone
	 * @param cible
	 * @return
	 */
	public Tuile[] getTuilesAction(boolean[][] zone, GridPoint2 cible) {
		Array<Tuile> ret = new Array<Tuile>();
		ret.add(tabTuiles[cible.y][cible.x]);

		for (int y = cible.y + zone.length / 2 - Math.abs(zone.length % 2 - 1), j = 0;
				y > cible.y - zone.length / 2 - zone.length % 2 && j < zone.length;
				y--, j++) {
			for (int x = cible.x - zone[0].length / 2 + Math.abs(zone[0].length % 2 - 1), i = 0;
					x < cible.x + zone[0].length / 2 + zone[0].length % 2 && i < zone[0].length;
					x++, i++) {
				if (zone[j][i] && y >= 0 && x >= 0 && y < tabTuiles.length && x < tabTuiles[0].length) {
					ret.add(tabTuiles[y][x]);
				}
			}
		}

		return (Tuile[]) ret.toArray();
	}

	public Tuile[][] getTabTuiles() {
		return tabTuiles;
	}

	public Dimension getMapDimension() {
		return dimension;
	}

	private boolean isCiblable(Array<GridPoint2> listePoints, GridPoint2 depart, GridPoint2 arrive, int dx, int dy) {
		if (depart.equals(arrive)) {
			return true;
		}
		if (!tabTuiles[arrive.y][arrive.x].isParcourable() && !tabTuiles[arrive.y][arrive.x].isOccupe()) {
			return false;
		}
		boolean ciblable = true;
		GridPoint2 p;
		for (int i = 0; i < listePoints.size(); i++) {
			p = listePoints.get(i);
			//Si le point est le point de départ, on passe au suivant
			if (p.x == depart.x && p.y == depart.y) {
				continue;
			}

			if (tabTuiles[p.y][p.x].isLastVisible() && ciblable) {
				ciblable = p.x == arrive.x && p.y == arrive.y;
			}

			if (tabTuiles[p.y][p.x].getType().equals(TROU)) {
				continue;
			}

			//Si le point est un obstacle, on arrete
			if (!tabTuiles[p.y][p.x].isParcourable() && (p.x != arrive.x || p.y != arrive.y)) {
				return false;
			}

			//On défini les positions pour zoneIntermediaire
			int py = p.y - dy;
			int px = p.x - dx;

			//Si le point est pas dans la map
			if (py < 0 || px < 0) {
				return false;
			}
		}
		return ciblable;
	}

	public static MapSerializable getMapSerializable(File file) {
		try {
			ObjectInputStream inputFile = new ObjectInputStream(new FileInputStream(file));
			MapSerializable smap = (MapSerializable) inputFile.readObject();
			return smap;
		} catch (IOException | ClassNotFoundException ex) {
			System.err.println(file.getAbsolutePath());
			Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
