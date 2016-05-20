/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.sort;

import MoteurJeu.gameplay.entite.variable.EntiteActiveVariable;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.Orientation;

/**
 * SortVariable.java
 * 
 * @param <S>
 */
public class SortVariable<S extends Sort> {
	
	public final S sort;
	private final Niveau niveau;
	
	public SortVariable(S sort) {
		this.sort = sort;
		niveau = new Niveau(0);
	}

	/**
	 * lance le sort sur la cible et la tuile
	 *
	 * @param cibleEntite
	 * @param cibleTuile
	 * @param lanceur
	 * @param oriAttaque
	 * @param critique
	 */
	public void lancerSort(EntiteVariable cibleEntite, Tuile cibleTuile, EntiteActiveVariable lanceur, Orientation oriAttaque, boolean critique) {
		sort.lancerSort(cibleEntite, cibleTuile, lanceur, oriAttaque, critique);
	}

}
