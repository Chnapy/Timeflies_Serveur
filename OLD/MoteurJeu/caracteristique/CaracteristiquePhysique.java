/*
 * 
 * 
 * 
 */
package MoteurJeu.caracteristique;

import java.util.Arrays;

/**
 * CaracteristiquePhysique.java
 Gère l'ensemble des caractéristiques physiques de l'entité.
 *
 */
public class CaracteristiquePhysique {

	//Tableau des caractéristiques possédées
	private final Caracteristique[] listCaracteristiques;
	
	private final CaracteristiquePhysiqueMax cpMax;
	

	/**
	 * Représente l'ensemble des caractéristiques possédées : vitalité, temps
	 * d'action, temps supplémentaire, fatigue, vitesse d'action
	 *
	 * @param cpMax
	 * @param initiative
	 */
	public CaracteristiquePhysique(CaracteristiquePhysiqueMax cpMax, int initiative) {
		this.cpMax = cpMax;
		listCaracteristiques = new Caracteristique[] {
			new Vitalite(cpMax.vitalite),
			new TempsAction(cpMax.tempsaction),
			new TempsSup(cpMax.tempssup),
			new VitesseAction(cpMax.vitesse),
			new Fatigue(cpMax.fatigue),
			new Initiative(initiative)
		};
	}

	/**
	 * Ajoute une valeur à la valeur actu de la caractéristique donnée
	 *
	 * @param c
	 * @param gain
	 */
	public void add(Carac c, int gain) {
		get(c).add(gain);
		majTempsActionParFatigue(c);
	}

	/**
	 * Redéfini le temps d'action d'après la fatigue
	 *
	 * @param c
	 */
	private void majTempsActionParFatigue(Carac c) {
		if (c.equals(Carac.FATIGUE)) {
			((TempsAction) get(Carac.TEMPSACTION)).setTotal(
					((TempsAction) get(Carac.TEMPSACTION)).getTempsBase()
					- ((((TempsAction) get(Carac.TEMPSACTION)).getTempsBase() * get(c).getActu()) / 100)
			);
		}
	}

	/**
	 * Enleve une valeur à la valeur actu de la caractéristique donnée
	 *
	 * @param c
	 * @param perte
	 */
	public void supp(Carac c, int perte) {
		get(c).supp(perte);
		majTempsActionParFatigue(c);
	}

	/**
	 * Défini la valeur actu de la caractéristique donnée
	 *
	 * @param c
	 * @param valeur
	 */
	public void setActu(Carac c, int valeur) {
		get(c).setActu(valeur);
		majTempsActionParFatigue(c);
	}

	/**
	 * Défini la valeur totale de la caractéristique donnée
	 *
	 * @param c
	 * @param valeur
	 */
	public void setTotal(Carac c, int valeur) {
		get(c).setTotal(valeur);
	}

	/**
	 *
	 * @param c
	 * @return la caracteristique de type c
	 */
	public Caracteristique get(Carac c) {
		switch (c) {
			case VITALITE:
				return listCaracteristiques[0];
			case TEMPSACTION:
				return listCaracteristiques[1];
			case TEMPSSUPP:
				return listCaracteristiques[2];
			case VITESSEACTION:
				return listCaracteristiques[3];
			case FATIGUE:
				return listCaracteristiques[4];
			case INITIATIVE:
				return listCaracteristiques[5];
			default:
				throw new Error("Enumeration non gérée.");
		}
	}

	/**
	 * hashCode
	 *
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(listCaracteristiques);
		return result;
	}

	/**
	 * equals en fonction de listCaracteristique
	 *
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CaracteristiquePhysique other = (CaracteristiquePhysique) obj;
		return Arrays.equals(listCaracteristiques, other.listCaracteristiques);
	}

}
