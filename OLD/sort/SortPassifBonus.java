/*
 * 
 * 
 * 
 */
package Combat.sort;

import MoteurJeu.gameplay.effet.Effet;
import Combat.entite.variable.EntiteActiveVariable;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.Orientation;

/**
 * SortPassifBonus.java
 * Représente un sort passif donnant un bonus à son utilisateur, de manière
 * permannente.
 *
 */
public abstract class SortPassifBonus extends SortPassif {

	/**
	 *
	 * @param id
	 * @param idClasseEntite
	 * @param nom
	 * @param description
	 * @param effets
	 * @param index
	 */
	public SortPassifBonus(int id, int idClasseEntite, String nom, String description, Effet[] effets,
			int index) {

		super(id, idClasseEntite, nom, description, effets, index);
	}

	@Override
	public void lancerSort(EntiteVariable cibleEntite, Tuile cibleTuile, EntiteActiveVariable lanceur, Orientation oriAttaque, boolean critique) {
		cibleEntite.recoitSort(getTabEffets(), cibleEntite, oriAttaque, critique);
	}

}
