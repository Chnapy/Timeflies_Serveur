/*
 * 
 * 
 * 
 */
package Combat.entite.variable;

import Combat.entite.classe.ClasseEntitePassive;
import MoteurJeu.general.Mode;

/**
 * EntitePassiveVariable.java
 * 
 * @param <E>
 */
public class EntitePassiveVariable<E extends ClasseEntitePassive> extends EntiteVariable<E> {

	public EntitePassiveVariable(E entite) {
		super(entite, -1);
	}

	@Override
	public void jouerTour(long time) {
	}

	@Override
	public Mode getEtatNow() {
		return getEtat();
	}

}
