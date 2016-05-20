/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.sort;

import MoteurJeu.gameplay.effet.Declenchable;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;

/**
 * SortPassifEffets.java
 * Représente un sort passif pouvant être déclenché.
 *
 */
public abstract class SortPassifEffets extends SortPassif {

	//Liste des déclenchables demandés pour lancer les effets
	protected Declenchable[] listDeclenchables;

	/**
	 *
	 * @param id
	 * @param idClasseEntite
	 * @param nom
	 * @param description
	 * @param effets
	 * @param declenchables
	 * @param index
	 *
	 */
	public SortPassifEffets(int id, int idClasseEntite, String nom, String description, Effet[] effets,
			Declenchable[] declenchables,
			int index) {
		super(id, idClasseEntite, nom, description, effets, index);
		listDeclenchables = declenchables;
	}

	/**
	 * Applique les effet du sort passif sur la cible
	 *
	 * @param effets
	 * @param lanceur
	 * @param cible
	 * @param isAvant	est lancé avant ou apres l'effet
	 * @param ccritique
	 */
	public void applyEffect(Effet[] effets, EntiteVariable lanceur, EntiteVariable cible, boolean isAvant, boolean ccritique) {
		for(Declenchable dec : getListDeclenchables()) {
			if(dec.canDeclencher(effets)) {
				actionApplyEffect(lanceur, cible, isAvant, ccritique);
			}
		}
	}
	
	protected abstract void actionApplyEffect(EntiteVariable lanceur, EntiteVariable cible, boolean isAvant, boolean ccritique);

	public Declenchable[] getListDeclenchables() {
		return listDeclenchables;
	}

}
