/*
 * 
 * 
 * 
 */
package Combat.sort.classe;

import Combat.sort.effet.Effet;
import Serializable.InCombat.zone.Zone;

/**
 * SortActif.java
 * Représente un sort pouvant être lancé par une entité active.
 *
 */
public abstract class SortActif extends Sort {

	//Zone de portée
	private final Zone zonePortee;

	//Zone d'action
	private final Zone zoneAction;

	//Temps d'action demandé pour l'exécution du sort
	public final int tempsAction;

	//Nombre de tours avant réutilisation possible
	public final int cooldown;

	public final int fatigue;

	public SortActif(int idClasseSort, int idClasseEntite, Effet[] effets,
			Zone zportee, Zone zaction, int tempsAction, int _cooldown, int _fatigue) {

		super(idClasseSort, idClasseEntite, effets);

		zonePortee = zportee;
		zoneAction = zaction;
		this.tempsAction = tempsAction;
		cooldown = _cooldown;
		fatigue = _fatigue;
	}

	public int getTempsAction() {
		return tempsAction;
	}

	public Zone getZonePortee() {
		return zonePortee;
	}

	public Zone getZoneAction() {
		return zoneAction;
	}

	public int getCooldown() {
		return cooldown;
	}

}
