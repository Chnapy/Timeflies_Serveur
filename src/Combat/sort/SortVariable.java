/*
 * 
 * 
 * 
 */
package Combat.sort;

import Combat.sort.classe.Sort;
import Serializable.HorsCombat.Niveau;

/**
 * SortVariable.java
 * 
 * @param <S>
 */
public class SortVariable<S extends Sort> {
	
	public final S sort;
	private final Niveau niveau;
	
	public SortVariable(S sort) {
		this.sort = sort;
		niveau = new Niveau();
	}

	/**
	 * lance le sort sur la cible et la tuile
	 *
	 */
	public void lancerSort() {
	}

}
