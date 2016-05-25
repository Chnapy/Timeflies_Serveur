/*
 * 
 * 
 * 
 */
package MoteurJeu.controleur;

import Combat.entite.variable.EntiteActiveVariable;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Map;
import MoteurJeu.gameplay.map.Tuile;
import Combat.sort.SortActif;
import Combat.sort.SortVariable;
import Combat.sort.pileaction.Action;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;
import MoteurJeu.general.Tourable;

/**
 * ControleurSort.java
 *
 */
public class ControleurSort implements Tourable {

	private final ControleurPrincipal controleurPrincipal;

	private EntiteActiveVariable entiteEnCours;

	private SortVariable<SortActif> sortEnCours;

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

	public void update(EntiteActiveVariable entite, Action action, Map map) {

		Tuile[] tuilesTouchees = map.getTuilesAction(action.getSort().sort.getZoneAction().getZoneIntermediaire(), new GridPoint2(action.getGridPoint2().x, action.getGridPoint2().y));
		for (Tuile t : tuilesTouchees) {
			lancerSort(entite, action.getSort(), t, action.getOriAttaque(), action.isCritique());
		}
		action.getSort().sort.resetCooldown();
	}

	public void lancerSort(EntiteActiveVariable lanceur, SortVariable<SortActif> sort, Tuile tuileCible, Orientation oriAttaque, boolean critique) {
		EntiteVariable persoCible = controleurPrincipal.getPerso(tuileCible);
		sort.lancerSort(persoCible, tuileCible, lanceur, oriAttaque, critique);
	}

	@Override
	public void nouveauTour(ControleurPrincipal controleur, EntiteActiveVariable _entiteEnCours, Object... objs) {
		entiteEnCours = _entiteEnCours;
	}

	@Override
	public void finTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs) {
		for (SortVariable<SortActif> sort : entiteEnCours.getTabSortActifVariable()) {
			sort.sort.subCooldown();
		}
	}

	@Override
	public void enTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs) {
	}

	public SortVariable<SortActif> getSortEnCours() {
		return sortEnCours;
	}

	public void setSortEnCours(SortVariable<SortActif> sortEnCours) {
		this.sortEnCours = sortEnCours;
	}

}
