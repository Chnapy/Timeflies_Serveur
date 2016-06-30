/*
 * 
 * 
 * 
 */
package Combat.entite.classe;

import Serializable.HorsCombat.CaracteristiquePhysiqueMax;


/**
 * ClassePersonnage.java
 * 
 */
public class ClassePersonnage extends ClasseEntiteActive {

	public ClassePersonnage(int id, 
			CaracteristiquePhysiqueMax cPhysique, int tempsDeplacement) {
		super(id, cPhysique, tempsDeplacement);
	}

}
