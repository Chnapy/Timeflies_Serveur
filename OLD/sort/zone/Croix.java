/*
 * 
 * 
 * 
 */
package Combat.sort.zone;

/**
 * Croix.java
 * Représente une zone en forme de croix.
 *
 */
public class Croix extends Zone {

	/**
	 *
	 * @param size	    taille d'une partie de la croix
	 * @param positive	positif ?
	 */
	public Croix(int size, boolean positive) {
		super(positive, size);
		//TODO taille
	}

//	@Override
//	public Tuile[] getTilesOfInterrest(GridPoint2 center) {
//		ArrayList<Tuile> tiles = new ArrayList<Tuile>();
//		Tuile[][] mapTiles = currentMap.getTabTuiles();
//
//		int maxX = currentMap.getMapDimension().width;
//		int maxY = currentMap.getMapDimension().height;
//
//		GridPoint2 start = new GridPoint2(super.valueInBound(center.x - size, maxX), super.valueInBound(center.y - size, maxY));
//		GridPoint2 stop = new GridPoint2(super.valueInBound(center.x + size, maxX), super.valueInBound(center.y + size, maxY));
//
//		for (int x = start.x; x < stop.x; x++) {
//			tiles.add(mapTiles[x][center.y]);
//		}
//
//		for (int y = start.y; y < stop.y; y++) {
//			tiles.add(mapTiles[center.x][y]);
//		}
//
//		Tuile[] result = new Tuile[tiles.size()];
//		return tiles.toArray(result);
//	}
	@Override
	public boolean[][] getZoneOfInterest() {
		throw new Error("TODO");
	}

}
