/*
 * 
 * 
 * 
 */
package MoteurJeu.general;

/**
 * GridPoint2.java
 *
 */
public class GridPoint2 {

	public int x;
	public int y;

	public GridPoint2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public GridPoint2(GridPoint2 point) {
		x = point.x;
		y = point.y;
	}

	public GridPoint2 set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public GridPoint2 set(GridPoint2 point) {
		x = point.x;
		y = point.y;
		return this;
	}

}
