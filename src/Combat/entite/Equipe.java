/*
 * 
 * 
 * 
 */
package Combat.entite;

import java.util.ArrayList;

/**
 * Equipe.java
 * 
 */
public class Equipe {
	
	public final int numero;
	public final String colorCode;
	public final ArrayList<EntiteJouable> entites;

	public Equipe(int numero, String colorCode, ArrayList<EntiteJouable> entites) {
		this.numero = numero;
		this.colorCode = colorCode;
		this.entites = entites;
	}

}
