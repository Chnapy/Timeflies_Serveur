/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.entite.variable;

import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.entite.classe.ClasseEntite;
import MoteurJeu.gameplay.caracteristique.CaracteristiqueSpatiale;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.NiveauSymbolique;
import MoteurJeu.gameplay.envoutement.Envoutement;
import MoteurJeu.gameplay.envoutement.EnvoutementEffets;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.gameplay.sort.SortPassifBonus;
import MoteurJeu.gameplay.sort.SortPassifEffets;
import MoteurJeu.gameplay.sort.SortVariable;
import MoteurJeu.general.Array;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Mode;
import MoteurJeu.general.Orientation;
import java.util.Observable;

/**
 * EntiteVariable.java
 *
 * @param <E>
 */
public abstract class EntiteVariable<E extends ClasseEntite> extends Observable {

	protected final E entite;

	//Etat de l'entité (déplacement, sort, ...) sans prendre en compte les 
	//actions prévues de la pile d'actions
	protected Mode etat;

	//Caractéristiques physiques de l'entité
	public final CaracteristiquePhysique caracPhysique;

	//Caractéristiques spatiales de l'entité
	protected CaracteristiqueSpatiale caracSpatiale;

	//Niveau symbolique
	protected NiveauSymbolique niveauSymbol;

	//Liste des envoutements
	protected final Array<Envoutement> listEnvoutements;

	//Sorts passifs de l'entité
	private final SortVariable<SortPassif>[] tabSortPassifVariable;

	public EntiteVariable(E entite, int initiative) {
		this.entite = entite;
		etat = Mode.DEPLACEMENT;
		listEnvoutements = new Array<Envoutement>();
		caracPhysique = new CaracteristiquePhysique(entite.caracPhysiqueMax, initiative);
		tabSortPassifVariable = new SortVariable[entite.tabSortPassif.length];
		for (int i = 0; i < tabSortPassifVariable.length; i++) {
			tabSortPassifVariable[i] = new SortVariable<SortPassif>(entite.tabSortPassif[i]);
		}
	}

	/**
	 *
	 * @return les sort passif de l'entitée
	 */
	public SortVariable<SortPassif>[] getTabSortPassif() {
		return tabSortPassifVariable;
	}

	public CaracteristiquePhysique getCaracPhysique() {
		return caracPhysique;
	}

	public void premiereAction() {
		for (SortVariable<SortPassif> sort : tabSortPassifVariable) {
			if (sort.sort instanceof SortPassifBonus) {
				sort.lancerSort(this, null, null, Orientation.NORD, false);
			}
		}
	}

	public void notifierObserveurs(Object[] envois) {
		setChanged();
		notifyObservers(envois);
	}

	/**
	 * Recoit un sort d'une entité autre.
	 * Lance les sorts passifs si possibilité.
	 *
	 * @param effets
	 * @param lanceur
	 * @param oriAttaque
	 * @param critique
	 */
	public void recoitSort(Effet[] effets, EntiteVariable lanceur, Orientation oriAttaque, boolean critique) {
		if (lanceur != null) {
			for (SortVariable<SortPassif> sortPassif : tabSortPassifVariable) {
				if (sortPassif.sort instanceof SortPassifEffets) {
					((SortPassifEffets) sortPassif.sort).applyEffect(effets, lanceur, this, true, critique);
				}
			}
			for (Envoutement envoutement : listEnvoutements) {
				if (envoutement instanceof EnvoutementEffets) {
					((EnvoutementEffets) envoutement).applyEffect(effets, lanceur, true, critique);
				}
			}
		}
		for (Effet effet : effets) {
			effet.lancerEffetEntite(this, oriAttaque, critique);
		}
		if (lanceur != null) {
			for (SortVariable<SortPassif> tabSortPassif1 : tabSortPassifVariable) {
				if (tabSortPassif1.sort instanceof SortPassifEffets) {
					((SortPassifEffets) tabSortPassif1.sort).applyEffect(effets, lanceur, this, false, critique);
				}
			}
			for (Envoutement envoutement : listEnvoutements) {
				if (envoutement instanceof EnvoutementEffets) {
					((EnvoutementEffets) envoutement).applyEffect(effets, lanceur, false, critique);
				}
			}
		}
	}

	//Jeu du tour de l'entité
	public abstract void jouerTour(long time);

	/**
	 * Change la position
	 *
	 * @param pt
	 */
	public void setPosition(GridPoint2 pt) {
		caracSpatiale.getPosition().x = pt.x;
		caracSpatiale.getPosition().y = pt.y;
	}

	public void move(int x, int y) {
		caracSpatiale.move(x, y);
	}

	public void setCaracSpatiale(CaracteristiqueSpatiale caracSpatiale) {
		this.caracSpatiale = caracSpatiale;
	}

	/**
	 *
	 * @return les caractéristique spatial de l'entité
	 */
	public CaracteristiqueSpatiale getCaracSpatiale() {
		return caracSpatiale;
	}

	/**
	 *
	 * @return niveau symbolique
	 */
	public NiveauSymbolique getNiveauSymbol() {
		return niveauSymbol;
	}

	/**
	 *
	 * @return l'état de l'entité vue du joueur
	 */
	public Mode getEtat() {
		return etat;
	}

	/**
	 *
	 * @return l'état de l'entité en se moment
	 */
	public abstract Mode getEtatNow();

	public void setEtat(Mode etat) {
		this.etat = etat;
	}

}
