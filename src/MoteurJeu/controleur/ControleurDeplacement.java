/*
 * 
 * 
 * 
 */
package MoteurJeu.controleur;

import MoteurJeu.gameplay.entite.variable.EntiteActiveVariable;
import MoteurJeu.gameplay.map.Map;
import MoteurJeu.gameplay.sort.pileaction.Action;
import MoteurJeu.general.Array;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Mode;
import MoteurJeu.general.Tourable;

/**
 * ControleurDeplacement.java
 *
 */
public class ControleurDeplacement implements Tourable {

	private final ControleurPrincipal controleurPrincipal;

	private final Map map;

	private EntiteActiveVariable entiteEnCours;

	//Chemin (liste de points) de l'entité active à la tuile ciblée
	private Array<GridPoint2> path;

	private GridPoint2 lastPosFixe;

	public ControleurDeplacement(ControleurPrincipal controleurPrincipal, Map _map) {
		this.controleurPrincipal = controleurPrincipal;
		map = _map;
	}

	public void clicSurTuile(int x, int y) {
//			System.out.println(path);
		if (path != null) {
			deplacer();
			if (path.first().equals(entiteEnCours.getCaracSpatiale().getPosition())) {
				path.remove(0);
			}
			if (path.size() > 0) {
				lastPosFixe = path.last();
			}
			path = null;
		}
	}

	private void deplacer() {
		for (int i = 0; i < path.size(); i++) {
			controleurPrincipal.setPrecOriAttaque(controleurPrincipal.getOriAttaque());
			if (i == 0) {
				controleurPrincipal.setOriAttaque(controleurPrincipal.getOrientation(entiteEnCours.isEnDeplacement() ? lastPosFixe : entiteEnCours.getCaracSpatiale().getPosition(), path.get(i)));
			} else {
				controleurPrincipal.setOriAttaque(controleurPrincipal.getOrientation(path.get(i - 1), path.get(i)));
			}
			Boolean critique = controleurPrincipal.isCoupCritique(entiteEnCours.getCaracSpatiale().getOrientation(), controleurPrincipal.getOriAttaque());
			if (controleurPrincipal.getOriAttaque() != controleurPrincipal.getPrecOriAttaque()) {
				controleurPrincipal.addAction(new Action(path.get(i), entiteEnCours.getOrienter(), controleurPrincipal.getOriAttaque(), controleurPrincipal.getPrecOriAttaque(), critique));
				critique = controleurPrincipal.isCoupCritique(entiteEnCours.getCaracSpatiale().getOrientation(), controleurPrincipal.getOriAttaque());//todo utiliser l'orientation finale
			}
			controleurPrincipal.addAction(new Action(path.get(i), entiteEnCours.getDeplacer(), controleurPrincipal.getOriAttaque(), controleurPrincipal.getPrecOriAttaque(), critique));
		}
	}

	/**
	 * Passage en mode déplacement
	 *
	 */
	public void modeDeplacement() {
		entiteEnCours.setEtat(Mode.DEPLACEMENT);
		controleurPrincipal.controleurSort.setSortEnCours(null);
	}

	public void update(EntiteActiveVariable entite, Action action) {
		entite.setEnDeplacement(true);
		action.getSort().lancerSort(entite, map.getTabTuiles()[action.getGridPoint2().y][action.getGridPoint2().x], entite, action.getOriAttaque(), action.isCritique());
	}

	@Override
	public void nouveauTour(ControleurPrincipal controleur, EntiteActiveVariable _entiteEnCours, Object... objs) {
		entiteEnCours = _entiteEnCours;
		path = null;
		lastPosFixe = null;
		map.setTuileOccupe(false, entiteEnCours.getCaracSpatiale().getPosition().y, entiteEnCours.getCaracSpatiale().getPosition().x);
	}

	@Override
	public void finTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs) {
		map.setTuileOccupe(true, entiteEnCours.getCaracSpatiale().getPosition().y, entiteEnCours.getCaracSpatiale().getPosition().x);
	}

	@Override
	public void enTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs) {
	}

}
