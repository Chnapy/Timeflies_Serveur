/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.sort;

import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.Entite;
import MoteurJeu.gameplay.entite.EntiteActive;
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
	 * @param nom
	 * @param description
	 * @param niveau
	 * @param effets
	 * @param index
	 */
	public SortPassifBonus(String nom, String description, Niveau niveau,
			Effet[] effets,
			int index) {

		super(nom, description, niveau, effets, index);
	}

	@Override
	public void lancerSort(Entite cibleEntite, Tuile cibleTuile, EntiteActive lanceur, Orientation oriAttaque, boolean critique) {
		cibleEntite.recoitSort(getTabEffets(), cibleEntite, oriAttaque, critique);
	}

}
