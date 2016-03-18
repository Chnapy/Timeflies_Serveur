/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.map;

import MoteurJeu.general.GridPoint2;
import MoteurJeu.controleur.ControleurPrincipal;
import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.Entite;
import MoteurJeu.gameplay.invocation.Invocation;
import static MoteurJeu.gameplay.map.EtatTuile.NORMAL;
import MoteurJeu.general.Orientation;
import gameplay.map.Type;

/**
 * Tuile.java
 * Représente une tuile (une case) de la map.
 *
 */
public class Tuile {

	private ControleurPrincipal controleur;

	//Type de la tuile (simple, obstacle, ...)
	private Type type;

	//Etat de la tuile (normal, zonesort, ...)
	private EtatTuile etat;

	//Position x/y
	private final GridPoint2 position;

	//Pathfinding
	private final int index;

	//Occupation par une entité
	private boolean occupe;

	/**
	 *
	 * @param t
	 * @param pos
	 * @param index
	 */
	public Tuile(Type t, GridPoint2 pos, int index) {
		type = t;
		etat = NORMAL;
		position = pos;
		this.index = index;
	}

	public void setControleur(ControleurPrincipal _controleur) {
		controleur = _controleur;
	}

	public Type getType() {
		return type;
	}

	public EtatTuile getEtat() {
		return etat;
	}

	public void setEtat(EtatTuile etat) {
		this.etat = etat;
	}

	public GridPoint2 getPosition() {
		return position;
	}

	public int getIndex() {
		return index;
	}

	public boolean isOccupe() {
		return occupe;
	}

	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}

	public boolean isVisible() {
		return !isOccupe()
				&& !type.equals(Type.OBSTACLE)
				&& !type.equals(Type.ECRAN);
	}

	public boolean isCiblable() {
		return !type.equals(Type.OBSTACLE)
				&& !type.equals(Type.TROU);
	}

	public boolean isParcourable() {
		return !isOccupe()
				&& isCiblable();
	}

	public boolean isLastVisible() {
		return isOccupe()
				|| type.equals(Type.ECRAN);
	}

	/**
	 * La tuile recoit le sort et invoque l'invocation ect si besoins
	 *
	 * @param effets
	 * @param lanceur
	 * @param oriAttaque
	 * @param critique
	 */
	public void recoitSort(Effet[] effets, Entite lanceur, Orientation oriAttaque, boolean critique) {
		for (Effet effet : effets) {
			for (Declencheur declencheur : effet.getDeclencheur()) {
				if (declencheur instanceof Invocation) {
					((Invocation) declencheur).invoquer(this.getPosition());
					controleur.addEntite((Entite) declencheur);
				} else {
					effet.lancerEffetTuile(this, lanceur, oriAttaque, critique);
				}
			}
		}
	}

}
