/*
 * 
 * 
 * 
 */
package Combat.entite.classe;

import Serializable.HorsCombat.CaracteristiquePhysiqueMax;
import Combat.sort.classe.SortPassif;

/**
 * ClasseEntite.java
 * Représente une entité, visible en combat.
 *
 */
public abstract class ClasseEntite {

	//Id de la classe
	public final int idClasse;

	//Caractéristiques physiques initiales de l'entité
	public final CaracteristiquePhysiqueMax caracPhysiqueMax;

	//Nom de la classe
	public final String nomClasse;

	//Sorts passifs de l'entité
	public SortPassif[] tabSortPassif;

	public ClasseEntite(int idClasse, String nomClasse, CaracteristiquePhysiqueMax caracPhysiqueMax) {
		this.idClasse = idClasse;
		this.nomClasse = nomClasse;
		this.caracPhysiqueMax = caracPhysiqueMax;
	}

	public void setTabSortPassif(SortPassif[] tab) {
		this.tabSortPassif = tab;
	}

	public boolean isEntiteActif() {
		return this instanceof ClasseEntiteActive;
	}

}
