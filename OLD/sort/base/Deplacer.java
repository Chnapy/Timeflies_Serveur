/*
 * 
 * 
 * 
 */
package Combat.sort.base;

import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.effet.placement.Deplacement;
import Combat.entite.variable.EntiteActiveVariable;
import Combat.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import Combat.sort.SortActif;
import Combat.sort.zone.Carre;
import Combat.sort.zone.ZoneAction;
import MoteurJeu.general.Orientation;

/**
 * Deplacer.java
 *
 */
public class Deplacer extends SortActif {

	private static final int TEMPS_ACTION = 500;

	public Deplacer() {
		super(-1, -1,
				"Deplacer", 
				"Permet de déplacer l'entité",
				new Effet[]{
					new Effet(new Declencheur[]{
						new Deplacement(1)
					})
				},
				new ZoneAction(new Carre(0, true)),
				new ZoneAction(new Carre(0, true)),
				0,//todo
				TEMPS_ACTION,
				0,
				0);
	}

	@Override
	public void lancerSort(EntiteVariable cible, Tuile cible2, EntiteActiveVariable lanceur, Orientation oriAttaque, boolean critique) {
		super.lancerSort(cible, cible2, lanceur, oriAttaque, critique);
		lanceur.notifierObserveurs(new Object[]{
			lanceur.getCaracSpatiale().getPosition(),
			TEMPS_ACTION
		});
	}

}
