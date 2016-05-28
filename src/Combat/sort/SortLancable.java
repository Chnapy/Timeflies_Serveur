/*
 * 
 * 
 * 
 */
package Combat.sort;

import Combat.sort.classe.SortActif;
import Combat.sort.effet.Effet;
import Serializable.Position;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SortLancable.java
 *
 */
public abstract class SortLancable implements Runnable {

	private final SortActif sort;
	private final ArrayList<Position> posCibles;
	private boolean enCours;
	private final long timeStart;

	public SortLancable(SortActif sort, ArrayList<Position> posCibles, long timeStart) {
		this.sort = sort;
		this.posCibles = posCibles;
		enCours = false;
		this.timeStart = timeStart;
	}

	@Override
	public void run() {
		enCours = true;
		try {
			Thread.sleep(sort.tempsAction - (System.currentTimeMillis() - timeStart));
			for (Effet effet : sort.tabEffets) {
				posCibles.forEach((p) -> {
					applyEffet(effet, p);
				});
			}
		} catch (InterruptedException ex) {
			System.out.println("Sort interrompu " + sort.nom);
		}
		enCours = false;
	}

	public abstract void applyEffet(Effet effet, Position p);

	public boolean isEnCours() {
		return enCours;
	}

}
