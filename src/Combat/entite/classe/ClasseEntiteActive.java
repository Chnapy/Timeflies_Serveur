/*
 * 
 * 
 * 
 */
package Combat.entite.classe;

import Serializable.HorsCombat.CaracteristiquePhysiqueMax;
import Combat.sort.classe.SortActif;

/**
 * ClasseEntiteActive.java
 * Représente une entité active (controlable par un joueur).
 * Joue son tour.
 *
 */
public class ClasseEntiteActive extends ClasseEntite {

	//Tableau des sorts actifs
	public SortActif[] tabSortActif;
	public final int tempsDeplacement;

	public ClasseEntiteActive(int idClasse,
			CaracteristiquePhysiqueMax cPhysique, int tempsDeplacement) {

		super(idClasse, cPhysique);
		this.tempsDeplacement = tempsDeplacement;
	}
	
	public void setTabSortActif(SortActif[] tab) {
		this.tabSortActif = tab;
	}

}
