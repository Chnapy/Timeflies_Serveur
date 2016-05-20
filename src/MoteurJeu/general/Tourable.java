/*
 * 
 * 
 * 
 */

package MoteurJeu.general;

import MoteurJeu.controleur.ControleurPrincipal;
import MoteurJeu.gameplay.entite.variable.EntiteActiveVariable;

/**
 * Tourable
 * Interface
 */
public interface Tourable {
	
	//Lancé lorsqu'un tour d'une entité commence
	public void nouveauTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs);

	//Lancé lorsqu'un tour d'une entité finit
	public void finTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs);

	//Lancé à chaque frame pendant le tour d'une entité
	public void enTour(ControleurPrincipal controleur, EntiteActiveVariable entiteEnCours, Object... objs);

}
