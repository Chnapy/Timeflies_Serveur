/*
 * 
 * 
 * 
 */
package MoteurJeu.controleur;

import MoteurJeu.gameplay.entite.Entite;
import MoteurJeu.gameplay.entite.EntiteActive;
import MoteurJeu.gameplay.map.Map;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.pileaction.Action;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;
import MoteurJeu.general.Tourable;

/**
 * ControleurSort.java
 *
 */
public class ControleurSort implements Tourable {

	private final ControleurPrincipal controleurPrincipal;

	private EntiteActive entiteEnCours;

	private SortActif sortEnCours;

	public ControleurSort(ControleurPrincipal controleurPrincipal) {
		this.controleurPrincipal = controleurPrincipal;
	}

	public void clicSurTuile(int x, int y) {
		GridPoint2 pt = new GridPoint2(x, y);
		//Lancement de sort sur toute la zone action
		int tempsSort = entiteEnCours.getSortFinalTempsAction(sortEnCours);
		if (entiteEnCours.tempsDispo() >= tempsSort) {
			controleurPrincipal.setPrecOriAttaque(controleurPrincipal.getOriAttaque());
			controleurPrincipal.setOriAttaque(
					controleurPrincipal.getOrientation(entiteEnCours.getLastPosition(), pt)
			);
//					System.out.println(oriAttaque);
			Boolean critique = controleurPrincipal.isCoupCritique(
					entiteEnCours.getCaracSpatiale().getOrientation(),
					controleurPrincipal.getOriAttaque()
			);
			if (controleurPrincipal.getOriAttaque() != controleurPrincipal.getPrecOriAttaque()) {
				controleurPrincipal.addAction(new Action(pt, entiteEnCours.getOrienter(),
						controleurPrincipal.getOriAttaque(), controleurPrincipal.getPrecOriAttaque(), critique)
				);
				critique = controleurPrincipal.isCoupCritique(
						entiteEnCours.getCaracSpatiale().getOrientation(),
						controleurPrincipal.getOriAttaque()
				);
			}
			controleurPrincipal.addAction(new Action(pt, sortEnCours, controleurPrincipal.getOriAttaque(),
					controleurPrincipal.getPrecOriAttaque(), critique)
			);
			entiteEnCours.subTime(tempsSort);
		}
	}

	public void update(EntiteActive entite, Action action, Map map) {

		Tuile[] tuilesTouchees = map.getTuilesAction(action.getSort().getZoneAction().getZoneIntermediaire(), new GridPoint2(action.getGridPoint2().x, action.getGridPoint2().y));
		for (Tuile t : tuilesTouchees) {
			lancerSort(entite, action.getSort(), t, action.getOriAttaque(), action.isCritique());
		}
		action.getSort().resetCooldown();
	}

	public void lancerSort(EntiteActive lanceur, SortActif sort, Tuile tuileCible, Orientation oriAttaque, boolean critique) {
		Entite persoCible = controleurPrincipal.getPerso(tuileCible);
		sort.lancerSort(persoCible, tuileCible, lanceur, oriAttaque, critique);
	}

	@Override
	public void nouveauTour(ControleurPrincipal controleur, EntiteActive _entiteEnCours, Object... objs) {
		entiteEnCours = _entiteEnCours;
	}

	@Override
	public void finTour(ControleurPrincipal controleur, EntiteActive entiteEnCours, Object... objs) {
		for (SortActif sort : entiteEnCours.getTabSortActif()) {
			sort.subCooldown();
		}
	}

	@Override
	public void enTour(ControleurPrincipal controleur, EntiteActive entiteEnCours, Object... objs) {
	}

	public SortActif getSortEnCours() {
		return sortEnCours;
	}

	public void setSortEnCours(SortActif sortEnCours) {
		this.sortEnCours = sortEnCours;
	}

}
