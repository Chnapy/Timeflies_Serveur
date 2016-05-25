/*
 * 
 * 
 * 
 */
package HorsCombat;

import Combat.sort.classe.Sort;
import Combat.sort.SortVariable;

/**
 * NiveauSymbolique.java
 * Gère le niveau symbolique du personnage d'après :
 * - le niveau de ses sorts
 * Il est redéfini à chaque tour global.
 *
 */
public class NiveauSymbolique {

	/**
	 * Calcul du niveau symbolique :
	 * Récupération du niveau de chacun des sorts.
	 * Le niveau est calculé de cette manière :
	 * 15% : Moyenne des 3 niveaux de sort les plus faibles
	 * 25% : Moyenne des 3 niveaux de sort les plus forts
	 * 60% : Moyenne de l'ensemble des niveaux de sort
	 *
	 */
	private static int calculNiveau(SortVariable<Sort>[] sorts) {
		return -1;
	}

}
