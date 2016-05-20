/*
 * 
 * 
 * 
 */
package MoteurJeu.test;

import MoteurJeu.gameplay.caracteristique.Carac;
import MoteurJeu.gameplay.effet.Balus;
import MoteurJeu.gameplay.effet.Declenchable;
import MoteurJeu.gameplay.effet.Declencheur;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.classe.ClasseEntite;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.sort.SortPassifEffets;
import MoteurJeu.general.Orientation;

/**
 * SortPassifEffetSoin.java
 *
 */
public class SortPassifEffetSoin extends SortPassifEffets {

	private final static String NOM = "SortPassifEffetSoin";
	private final static String DESCRIPTION = "Lors de la reception de degats >30, l'entite recois 20 soin.";
	private final static int INDEX = 2;

	public SortPassifEffetSoin(int id, int idClasseEntite, String nom, String description) {
		super(id, idClasseEntite,
				nom,
				description,
				new Effet[]{
					new Effet(new Declencheur[]{
						new Balus(Carac.VITALITE, 20)
					})
				},
				new Declenchable[]{
					new DeclenchablePourSortPassif()
				},
				INDEX);
	}

	@Override
	protected void actionApplyEffect(EntiteVariable lanceur, EntiteVariable cible, boolean isAvant, boolean ccritique) {
		if (isAvant) {
			return;
		}
		cible.recoitSort(getTabEffets(), lanceur, Orientation.NORD, ccritique);
	}

}
