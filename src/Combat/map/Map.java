/*
 * 
 * 
 * 
 */
package Combat.map;

import Serializable.InCombat.zone.Zone;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.Position;
import java.util.ArrayList;

/**
 * Map.java
 *
 */
public class Map {

	private Tuile[][] tuiles;

	public Map(MapSerializable mapS) {
		tuiles = new Tuile[mapS.tuiles[0].length][mapS.tuiles.length];
		for (int y = 0; y < mapS.tuiles.length; y++) {
			for (int x = 0; x < mapS.tuiles[0].length; x++) {
				tuiles[x][y] = new Tuile(mapS.tuiles[y][x], new Position(x, y));
			}
		}
	}

	public boolean inBounds(Position pos) {
		if (pos.x < 0 || pos.y < 0
				|| pos.x >= tuiles.length || pos.y >= tuiles[0].length) {
			return false;
		}
		return true;
	}

	public Tuile get(Position pos) {
		return get(pos.x, pos.y);
	}

	public Tuile get(int x, int y) {
		try {
			return tuiles[x][y];
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	public ArrayList<Tuile> getAll(ArrayList<Position> positions) {
		ArrayList<Tuile> tuiles = new ArrayList();
		positions.forEach((p) -> {
			tuiles.add(get(p));
		});
		return tuiles;
	}

	public ArrayList<Position> getAllPositions(Position pos, Zone zone) {
		boolean[][] z = zone.getZoneIntermediaire();
		int decalageX = z.length / 2;
		int decalageY = z[0].length / 2;

		ArrayList<Position> ret = new ArrayList();

		for (int x = pos.x - decalageX, i = 0; i < z.length; x++, i++) {
			for (int y = pos.y - decalageY, j = 0; j < z[0].length; y++, j++) {
				Position p = new Position(x, y);
				if (inBounds(p)) {
					ret.add(p);
				}
			}
		}

		return ret;
	}

}
