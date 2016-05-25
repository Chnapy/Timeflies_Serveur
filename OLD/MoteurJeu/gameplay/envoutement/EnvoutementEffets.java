/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.envoutement;

import MoteurJeu.gameplay.effet.Declenchable;
import MoteurJeu.gameplay.effet.Effet;
import Combat.entite.classe.ClasseEntite;
import Combat.entite.variable.EntiteVariable;

/**
 * EnvoutementEffets.java
 * Représente un effet déclenchable, temporaire, appliqué à une entité active.
 * Les effets de l'envoutement s'appliquent lorsque l'ensemble des déclenchables
 * sont activés.
 *
 */
public abstract class EnvoutementEffets extends Envoutement {

	//Liste des declenchables qui doivent être activés
	private final Declenchable[] listDeclenchables;

	//Liste des effets lancés lorsque les déclenchables sont activés
	private final Effet[] listEffets;

	/**
	 *
	 * @param nom
	 * @param duree
	 * @param declenchables
	 * @param effets
	 */
	public EnvoutementEffets(String nom, int duree,
			Declenchable[] declenchables,
			Effet[] effets) {
		super(nom, duree);
		listDeclenchables = declenchables;
		listEffets = effets;
	}
	
	public final void applyEffect(Effet[] effets, EntiteVariable lanceur, boolean isAvant, boolean ccritique) {
		for(Declenchable dec : getListDeclenchables()) {
			if(dec.canDeclencher(effets)) {
				actionApplyEffect(lanceur, isAvant, ccritique);
			}
		}
	}

	protected abstract void actionApplyEffect(EntiteVariable lanceur, boolean isAvant, boolean ccritique);

	public Declenchable[] getListDeclenchables() {
		return listDeclenchables;
	}

	public Effet[] getListEffets() {
		return listEffets;
	}

}
