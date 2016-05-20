/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.sort.base;

import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.effet.placement.Rotation;
import MoteurJeu.gameplay.entite.variable.EntiteActiveVariable;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.general.Orientation;
import MoteurJeu.gameplay.sort.zone.Carre;
import MoteurJeu.gameplay.sort.zone.ZoneAction;

/**
 * Orienter.java
 *
 */
public class Orienter extends SortActif {

	private static final int TEMPS_ACTION = 0;

	public Orienter() {
		super(-1, -1,
				"Orienter", 
				"Oriente l'entité",
				new Effet[]{
					new Effet(new Declencheur[]{
						new Rotation()
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
//		System.out.println(cible.getNom());
		super.lancerSort(cible, cible2, lanceur, oriAttaque, critique);
		lanceur.notifierObserveurs(new Object[]{
			lanceur.getCaracSpatiale().getOrientation(),
			TEMPS_ACTION
		});
	}

}
