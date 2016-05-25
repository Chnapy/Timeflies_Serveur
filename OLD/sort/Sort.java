/*
 * 
 * 
 * 
 */
package Combat.sort;

import Console.utils.ConsoleDisplay;
import MoteurJeu.gameplay.effet.Effet;
import Combat.entite.variable.EntiteActiveVariable;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.general.Orientation;
import java.util.Observable;

/**
 * Sort.java
 * Représente un sort, actif ou passif.
 *
 */
public abstract class Sort extends Observable {
	
	//Id du sort
	public final int id;
	
	//Id classe entité
	public final int idClasseEntite;

	//Nom du sort
	public final String nom;

	//Description
	public final String description;

	//Niveau et expérience
	public Niveau niveau;

	//Tableau des effets
	public final Effet[] tabEffets;

	//Index pour la vue
	public final int index;

	/**
	 *
	 * @param id
	 * @param idClasseEntite
	 * @param nom
	 * @param description
	 * @param effets
	 * @param i	          pour la vue
	 */
	public Sort(int id, int idClasseEntite, String nom, String description, Effet[] effets, int i) {
		this.id = id;
		this.idClasseEntite = idClasseEntite;
		this.nom = nom;
		this.description = description;
		this.niveau = new Niveau(0);
		index = i;
		tabEffets = effets;
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
		if(tabEffets == null) {
			ConsoleDisplay.error("Sort " + nom + " lancé avant d'avoir appliqué les effets !");
			return;
		}
		if (cibleEntite != null) {
			cibleEntite.recoitSort(getTabEffets(), lanceur, oriAttaque, critique);
		}
		cibleTuile.recoitSort(getTabEffets(), lanceur, oriAttaque, critique);
	}

	public String getNom() {
		return nom;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public Effet[] getTabEffets() {
		return tabEffets;
	}

	public String getDescription() {
		return description;
	}

	public int getIndex() {
		return index;
	}
	
	public boolean isSortActif() {
		return this instanceof SortActif;
	}

}
