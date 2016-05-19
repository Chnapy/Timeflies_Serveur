/*
 * 
 * 
 * 
 */
package Serializable.Personnages;

import Serializable.Personnages.Sort.SortActif;
import Serializable.Personnages.Sort.SortPassif;
import java.io.Serializable;

/**
 * HCPersonnage.java
 *
 */
public class HCPersonnage implements Serializable {

	private static final long serialVersionUID = -7290110273359388765L;

	public final int id;
	public final String nom;
	public final String classe;
	public final int niveauS;
	public final int vitalite;
	public final int tempsA;
	public final int tempsS;
	public final int vitesse;
	public final int fatigue;
	public final SortActif[] sortsActifs;
	public final SortPassif[] sortsPassifs;

	public HCPersonnage(int id, String nom, String classe, int niveauS, int vitalite, int tempsA, int tempsS, int vitesse, int fatigue, SortActif[] sortsActifs, SortPassif[] sortsPassifs) {
		this.id = id;
		this.nom = nom;
		this.classe = classe;
		this.niveauS = niveauS;
		this.vitalite = vitalite;
		this.tempsA = tempsA;
		this.tempsS = tempsS;
		this.vitesse = vitesse;
		this.fatigue = fatigue;
		this.sortsActifs = sortsActifs;
		this.sortsPassifs = sortsPassifs;
	}

}
