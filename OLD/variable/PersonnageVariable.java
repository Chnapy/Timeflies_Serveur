/*
 * 
 * 
 * 
 */
package Combat.entite.variable;

import Combat.entite.classe.ClassePersonnage;

/**
 * PersonnageVariable.java
 * 
 */
public class PersonnageVariable extends EntiteActiveVariable {
	
	public final String nomDonne;

	public PersonnageVariable(ClassePersonnage entite, String nomDonne, int initiative) {
		super(entite, initiative);
		this.nomDonne = nomDonne;
	}

}
