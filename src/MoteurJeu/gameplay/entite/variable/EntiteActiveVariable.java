/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.entite.variable;

import MoteurJeu.gameplay.entite.classe.ClasseEntiteActive;
import MoteurJeu.gameplay.caracteristique.Carac;
import MoteurJeu.gameplay.caracteristique.Caracteristique;
import MoteurJeu.gameplay.entite.NiveauSymbolique;
import MoteurJeu.gameplay.envoutement.Envoutement;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortVariable;
import MoteurJeu.gameplay.sort.base.Deplacer;
import MoteurJeu.gameplay.sort.base.Orienter;
import MoteurJeu.gameplay.sort.pileaction.Action;
import MoteurJeu.gameplay.sort.pileaction.PileAction;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Mode;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * EntiteActiveVariable.java
 *
 */
public class EntiteActiveVariable extends EntiteVariable<ClasseEntiteActive> {

	//Est en train de se déplacer
	private boolean enDeplacement;
	
	private SortVariable<SortActif> orienter;
	private SortVariable<SortActif> deplacer;

	//Etat de l'entité en prenant en compte les actions prévues de la pile 
	//d'actions
	private Mode etatNow;

	//Sort actif en phase d'être lancé
	private SortVariable<SortActif> sortEnCours = null;

	//Tableau des sorts actifs
	private final SortVariable<SortActif>[] tabSortActifVariable;

	//Temps avant la fin du sort (en ms)
	private long tempsFinSort = -1;
	private long timeLeft = -1;
	private long oldTime = -1;

	//Pile des actions
	private final PileAction pileAction = new PileAction();

	public EntiteActiveVariable(ClasseEntiteActive entite, int initiative) {
		super(entite, initiative);
		tabSortActifVariable = new SortVariable[entite.tabSortActif.length];
		for (int i = 0; i < tabSortActifVariable.length; i++) {
			tabSortActifVariable[i] = new SortVariable<SortActif>(entite.tabSortActif[i]);
		}
		niveauSymbol = new NiveauSymbolique(Stream.concat(Arrays.stream(entite.tabSortPassif), Arrays.stream(entite.tabSortActif)).toArray(SortVariable[]::new));
		orienter = new SortVariable(entite.orienter);
		deplacer = new SortVariable(entite.deplacer);
	}

	/**
	 *
	 * @return les sort passif de l'entitée
	 */
	public SortVariable<SortActif>[] getTabSortActifVariable() {
		return tabSortActifVariable;
	}

	public SortVariable<SortActif> getOrienter() {
		return orienter;
	}

	public SortVariable<SortActif> getDeplacer() {
		return deplacer;
	}

	/**
	 * Joue le tour sur l'ensemble du temps d'action de l'entité.
	 * Utilise les actions de la pile d'actions.
	 *
	 * @param time
	 */
	@Override
	public void jouerTour(long time) {
		if (!actionIsRunning() && pileAction.size() > 0) {
			try {
				if (pileAction.get(0).getEtat() == Action.EtatAction.DEPLACEMENT) {
					etatNow = Mode.DEPLACEMENT;
				} else {
					etatNow = Mode.SORT;
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				System.out.println("pileAction = " + pileAction);
				System.out.println("pileAction.get(0) = " + pileAction.get(0));
				System.out.println("pileAction.get(0).getEtat() = " + pileAction.get(0).getEtat());
			}
			tempsFinSort = pileAction.get(0).getSort().sort.getTempsAction() + time;
			setChanged();
			notifyObservers(pileAction.removeFirst());
		}
		if (tempsFinSort != -1 && tempsFinSort <= time) {
			tempsFinSort = -1;
		}
		if (oldTime != -1) {
			timeLeft -= time - oldTime;
			oldTime = time;
		} else {
			timeLeft = caracPhysique.get(Carac.TEMPSACTION).getActu();
		}

	}

	public SortVariable<SortActif> getSortEnCours() {
		return sortEnCours;
	}

	public int getSortFinalTempsAction(SortVariable<SortActif> sort) {
		float sortTemps = sort.sort.getTempsAction();
		float vitesse = caracPhysique.get(Carac.VITESSEACTION).getActu();
		return (int) ((float) sortTemps / ((float) vitesse / 100));
	}

	public Caracteristique getTempsAction() {
		return getCaracPhysique().get(Carac.TEMPSACTION);
	}

	/**
	 * Défini le sort en cours d'après son index, et le renvoie
	 *
	 * @param index
	 * @return
	 */
	public SortVariable<SortActif> setSortEnCours(int index) {
		sortEnCours = getSort(index);
		return sortEnCours;
	}

	/**
	 * Récupère un sort depuis son index
	 *
	 * @param index
	 * @return
	 */
	public SortVariable<SortActif> getSort(int index) {
		for (SortVariable<SortActif> sort : tabSortActifVariable) {
			if (sort.sort.getIndex() == index) {
				return sort;
			}
		}
		throw new IllegalArgumentException();
	}

	public void addEnvoutement(Envoutement envoutement) {
		listEnvoutements.add(envoutement);
	}

	/**
	 * Renvoie si une action est en court d'exécution
	 *
	 * @return true si une action se déroule (déplacement ou sort) false sinon
	 */
	public boolean actionIsRunning() {
		return enDeplacement || tempsFinSort != -1;
	}

	/**
	 * permet de changer le temps restant
	 *
	 * @param time
	 */
	public void subTime(int time) {
		this.timeLeft -= time;
	}

	/**
	 *
	 * @return le temps dispot total avec le temps supplémentaire
	 */
	public long tempsDispo() {
		return timeLeft + caracPhysique.get(Carac.TEMPSSUPP).getActu();
	}

	/**
	 * Ajoute une action dans la pile d'action
	 *
	 * @param a
	 */
	public void addAction(Action a) {
		pileAction.add(a);
	}

	/**
	 * Défini les actions que cette entité va effectuer lorsque chaque tour
	 * global commencera.
	 */
	public void debutTourGlobal() {
		for (Envoutement envout : listEnvoutements) {
			envout.debutTourGlobal();
		}
	}

	/**
	 * Défini les actions que cette entité va effectuer lorsque chaque tour
	 * global se terminera.
	 */
	public void finTourGlobal() {
		for (Envoutement envout : listEnvoutements) {
			envout.finTourGlobal();
		}
	}

	/**
	 * Défini les actions que cette entité va effectuer lorsque chacun de ses
	 * tours commencera.
	 */
	public void debutTour() {
		for (Envoutement envout : listEnvoutements) {
			envout.debutTour();
		}
	}

	/**
	 * Défini les actions que cette entité va effectuer lorsque chacun de ses
	 * tours finira.
	 */
	public void finTour() {
		pileAction.clear();
		for (Envoutement envout : listEnvoutements) {
			envout.finTour();
			if (envout.subDuree() <= 0) {
				envout.finEnvoutement();
				listEnvoutements.removeValue(envout, false);
			}
		}
		caracPhysique.setActu(Carac.TEMPSACTION, caracPhysique.get(Carac.TEMPSACTION).getTotal());
	}

	/**
	 * Renvoie l'état de l'entité en prenant en compte la pile d'actions
	 *
	 * @return
	 */
	@Override
	public Mode getEtatNow() {
		if (actionIsRunning()) {
			return etatNow;
		} else {
			return getEtat();
		}
	}

	/**
	 *
	 * @return true si l'entitée est en déplacement
	 */
	public boolean isEnDeplacement() {
		return enDeplacement;
	}

	/**
	 *
	 * @param enDeplacement
	 */
	public void setEnDeplacement(boolean enDeplacement) {
		this.enDeplacement = enDeplacement;
	}

	/**
	 * Renvoie la dernière position d'arrivée de la pile d'actions
	 *
	 * @return la position de l'entité une fois que la liste d'action sera fini
	 */
	public GridPoint2 getLastPosition() {
		GridPoint2 ret = pileAction.getLastPosition();
		if (ret == null) {
			return getCaracSpatiale().getPosition();
		}
		return ret;
	}

}
