/*
 * 
 * 
 * 
 */
package MoteurJeu.controleur;

import MoteurJeu.gameplay.core.Joueur;
import MoteurJeu.gameplay.core.Timeline;
import MoteurJeu.general.Mode;
import MoteurJeu.gameplay.entite.variable.EntiteActiveVariable;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.entite.variable.PersonnageVariable;
import MoteurJeu.gameplay.map.Map;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.gameplay.sort.pileaction.Action;
import MoteurJeu.general.Array;
import MoteurJeu.general.GridPoint2;
import MoteurJeu.general.Orientation;
import MoteurJeu.general.Tourable;
import java.util.Observable;
import java.util.Observer;

/**
 * ControleurPrincipal.java ControleurPrincipal gérant le combat.
 *
 * Pseudo-code :
 *
 * - Récupération des joueurs (SERVEUR) - Récupération de la map (SERVEUR) -
 * Placement aléatoire des personnages (SERVEUR) - Affichage du tout (CLIENT) -
 * Placement des personnages par les joueurs (CLIENT) - Envoi du placement au
 * serveur (CLIENT) - Lancement du combat (CLIENT-SERVEUR)
 *
 */
public class ControleurPrincipal implements Observer, Tourable {

	//Controleur gérant les déplacements
	public final ControleurDeplacement controleurDeplacement;

	//Controleur gérant le lancement de sort
	public final ControleurSort controleurSort;

	//Map (modele)
	private final Map map;

	//Tableau des joueurs
	private final Joueur[] tabJoueurs;

	private final Array<EntiteVariable> listEntites;

	//Timeline (modele)
	private final Timeline timeline;

	//Entité jouant son tour
	private EntiteActiveVariable entiteEnCours;

	private Orientation oriAttaque;

	private Orientation precOriAttaque;

	public ControleurPrincipal(Map m, Joueur[] joueurs) {
		map = m;
		map.setControleur(this);
		tabJoueurs = joueurs;
		listEntites = new Array(getPersonnages(joueurs));
		timeline = new Timeline(listEntites);
		timeline.addObserver(this);
		controleurDeplacement = new ControleurDeplacement(this, map);
		controleurSort = new ControleurSort(this);

		listEntites.forEach((entite) -> {
			entite.addObserver(this);

			//La position de chaque joueur devient occupée
			map.setTuileOccupe(true, entite.getCaracSpatiale().getPosition().y, entite.getCaracSpatiale().getPosition().x);
		});
	}

	/**
	 * Lance le combat.
	 */
	public void lancer() {
		System.out.println("Lancement");
		timeline.start();
	}

	/**
	 * Arrete le combat.
	 */
	public void stop() {
		System.out.println("Arret en cours...");
		timeline.stop();
	}

	@Override
	public void nouveauTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs) {
		System.out.println("Nouveau tour");
		controleurDeplacement.nouveauTour(controleur, entiteEnCours, objs);
		controleurSort.nouveauTour(controleur, entiteEnCours, objs);
		oriAttaque = entiteEnCours.getCaracSpatiale().getOrientation();
	}

	@Override
	public void finTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs) {
		System.out.println("Fin tour");
		controleurDeplacement.finTour(controleur, entiteEnCours, objs);
		controleurSort.finTour(controleur, entiteEnCours, objs);
	}

	@Override
	public void enTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs) {
		controleurDeplacement.enTour(controleur, entiteEnCours, objs);
		controleurSort.enTour(controleur, entiteEnCours, objs);
	}

	/**
	 * Récupère les personnages de tous les joueurs.
	 *
	 * @param joueurs
	 * @return
	 */
	private static Array<PersonnageVariable> getPersonnages(Joueur[] joueurs) {
		Array<PersonnageVariable> listEntActive = new Array<PersonnageVariable>();
		for (Joueur j : joueurs) {
			listEntActive.addAll(j.getPersonnages());
		}
		return listEntActive;
	}

	/**
	 * Lors d'un clic sur une tuile, l'envoie depuis vMap. Fait déplacer
	 * l'entité active.
	 *
	 * @param x
	 * @param y
	 */
	public void clicSurTuile(int x, int y) {
		if (entiteEnCours.getEtat() == Mode.DEPLACEMENT) {
			controleurDeplacement.clicSurTuile(x, y);
		} else if (entiteEnCours.getEtat() == Mode.SORT) {
			//Lancement de sort sur toute la zone action
			controleurSort.clicSurTuile(x, y);
			controleurDeplacement.modeDeplacement();
		}
	}

	public void addAction(Action action) {
		entiteEnCours.addAction(action);
	}

	/**
	 * Lance les nouveaux tours, les fins tours
	 *
	 * Lance les actions sorts/déplacements
	 *
	 * @param o	  Timeline ClasseEntiteActive
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Timeline) {
			Timeline tl = (Timeline) o;
			switch (tl.getEtatTour()) {
				case COURS:
					enTour(this, entiteEnCours);
					break;
				case DEBUT:
					entiteEnCours = tl.getEntiteEnCours();
					nouveauTour(this, entiteEnCours);
					break;
				case FIN:
					finTour(this, entiteEnCours);
					break;
			}
		} else if (o instanceof EntiteActiveVariable) {
			EntiteActiveVariable entite = (EntiteActiveVariable) o;
			if (arg instanceof Action) {
				Action action = (Action) arg;
				if (action.getEtat() == Action.EtatAction.DEPLACEMENT) {
					controleurDeplacement.update(entite, action);
				} else if (action.getEtat() == Action.EtatAction.ROTATION) {
					action.getSort().lancerSort(entite, map.getTabTuiles()[action.getGridPoint2().y][action.getGridPoint2().x], entite, action.getOriAttaque(), action.isCritique());
				} else if (action.getEtat() == Action.EtatAction.SORT) {
					controleurSort.update(entite, action, map);
				}
			}
		}
	}

	/**
	 * Retourne le personnage présent sur la tuile.
	 *
	 * @param tuile
	 * @return le personnage présent sur la tuile. null si vide
	 */
	public EntiteVariable getPerso(Tuile tuile) {
		for (EntiteVariable entite : listEntites) {
			if (entite.getCaracSpatiale().getPosition().equals(tuile.getPosition())) {
				return entite;
			}
		}
		return null;
	}

	/**
	 * Récupère l'orientation de l'entité par rapport à un point donné.
	 *
	 * @param origine
	 * @param dest
	 * @return l'orientation de l'origine qui regarde vers le point
	 */
	public Orientation getOrientation(GridPoint2 origine, GridPoint2 dest) {
		if (origine.equals(dest)) {
			System.err.println(dest);
			throw new IllegalArgumentException("Les deux points sont egaux ! " + dest);
		}

		double vecX = dest.x - origine.x;
		double vecY = dest.y - origine.y;
//		System.out.println(vecX + " " + vecY);
		if (vecX > 0) {	//Est
			if (vecY > 0) {	//Sud
				if (vecX < vecY) {
					return Orientation.SUD;
				} else {
					return Orientation.EST;
				}
			} else {	//Nord
				if (vecX < vecY) {
					return Orientation.NORD;
				} else {
					return Orientation.EST;
				}
			}
		} else {	//Ouest
			if (vecY > 0) {	//Sud
				if (vecX < vecY) {
					return Orientation.SUD;
				} else {
					return Orientation.OUEST;
				}
			} else {	//Nord
				if (vecX > vecY) {
					return Orientation.NORD;
				} else {
					return Orientation.OUEST;
				}
			}
		}
	}

	public void addEntite(EntiteVariable entite) {
		timeline.addEntite(entite);
		listEntites.add(entite);
		entite.addObserver(this);
		map.setTuileOccupe(true, entite.getCaracSpatiale().getPosition().y, entite.getCaracSpatiale().getPosition().x);
	}

	public boolean isCoupCritique(Orientation oriCible, Orientation oriAttaque) {
		if (oriAttaque == null || oriCible == null || oriAttaque.equals(oriCible)) {
			return false;
		}
		return oriAttaque.invert().equals(oriCible);
	}

	public Orientation getOriAttaque() {
		return oriAttaque;
	}

	public void setOriAttaque(Orientation oriAttaque) {
		this.oriAttaque = oriAttaque;
	}

	public Orientation getPrecOriAttaque() {
		return precOriAttaque;
	}

	public void setPrecOriAttaque(Orientation precOriAttaque) {
		this.precOriAttaque = precOriAttaque;
	}

	public Map getMap() {
		return map;
	}
}
