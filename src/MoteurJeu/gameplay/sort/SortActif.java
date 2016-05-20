/*
 * 
 * 
 * 
 */
package MoteurJeu.gameplay.sort;

import MoteurJeu.gameplay.caracteristique.Carac;
import MoteurJeu.gameplay.effet.Effet;
import MoteurJeu.gameplay.entite.variable.EntiteActiveVariable;
import MoteurJeu.gameplay.entite.variable.EntiteVariable;
import MoteurJeu.gameplay.map.Tuile;
import MoteurJeu.gameplay.sort.zone.ZoneAction;
import MoteurJeu.general.Orientation;
import MoteurJeu.general.TypeDonnee;

/**
 * SortActif.java
 * Représente un sort pouvant être lancé par une entité active.
 *
 */
public abstract class SortActif extends Sort {

	//Zone de portée
	private ZoneAction zonePortee;

	//Zone d'action
	private ZoneAction zoneAction;

	//Temps d'action demandé pour l'exécution du sort
	public final int tempsAction;

	//Nombre de tours avant réutilisation possible
	public final int cooldown;
	
	public final int fatigue;

	//Cooldown actuel
	protected int cooldownActu;

	/**
	 *
	 * @param id
	 * @param idClasseEntite
	 * @param nom
	 * @param description
	 * @param effets
	 * @param zportee
	 * @param zaction
	 * @param index
	 * @param tempsAction
	 * @param _cooldown
	 * @param _fatigue
	 */
	public SortActif(int id, int idClasseEntite, String nom, String description, Effet[] effets,
			ZoneAction zportee, ZoneAction zaction,
			int index, int tempsAction, int _cooldown, int _fatigue) {

		super(id, idClasseEntite, nom, description, effets, index);

		zonePortee = zportee;
		zoneAction = zaction;
		this.tempsAction = tempsAction;
		cooldown = _cooldown;
		cooldownActu = 0;
		fatigue = _fatigue;
	}

	@Override
	public void lancerSort(EntiteVariable cibleEntite, Tuile cibleTuile, EntiteActiveVariable lanceur, Orientation oriAttaque, boolean critique) {
		super.lancerSort(cibleEntite, cibleTuile, lanceur, oriAttaque, critique);
		if(fatigue > 0) {
			lanceur.getCaracPhysique().add(Carac.FATIGUE, fatigue);
		}
	}

	public int getTempsAction() {
		return tempsAction;
	}

	public ZoneAction getZonePortee() {
		return zonePortee;
	}

	public ZoneAction getZoneAction() {
		return zoneAction;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getCooldownActu() {
		return cooldownActu;
	}

	public void setCooldownActu(int _cooldownactu) {
		cooldownActu = _cooldownactu;
		setChanged();
		notifyObservers(new Object[]{
			TypeDonnee.COOLDOWN,
			cooldownActu
		});
	}

	public void resetCooldown() {
		setCooldownActu(cooldown);
	}

	public void subCooldown() {
		if (cooldownActu > 0) {
			setCooldownActu(cooldownActu - 1);
		}
	}

}
