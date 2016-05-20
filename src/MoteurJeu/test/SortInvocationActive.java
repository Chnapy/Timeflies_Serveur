package MoteurJeu.test;

import HorsCombat.Modele.InitialData;
import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.zone.Carre;
import MoteurJeu.gameplay.sort.zone.ZoneAction;

public class SortInvocationActive extends SortActif {

	public SortInvocationActive(int id, int idClasseEntite, String nom, String description, int tempsAction, int cooldown, int fatigue) {
		super(id, idClasseEntite,
				nom,
				description,
				new Effet[]{
					new Effet(new Declencheur[]{
						(InvocationMobileTest) InitialData.getEntiteFromClass(InvocationMobileTest.class)
					})
				},
				new ZoneAction(new Carre(1, true)),
				new ZoneAction(new Carre(0, true)),
				5,
				tempsAction,
				cooldown,
				fatigue
		);
	}

}
