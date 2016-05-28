/*
 * 
 * 
 * 
 */
package Combat.entite;

import Serializable.InCombat.donnee.InEntite;
import Serializable.InCombat.donnee.InEquipe;
import java.util.ArrayList;

/**
 * Equipe.java
 *
 */
public class Equipe {

	private static final String[] EQUIPE_COLOR_CODE = {
		"16BAFB",
		"EE4C20",
		"F5E321",
		"6605CA",
		"62B701",
		"34120B",
		"058A85",
		"13B317",
		"D3559B",
		"6C2942",};

	public final int numero;
	public final String colorCode;
	public final ArrayList<EntiteActiveJouable> entites;

	public Equipe(int numero) {
		this(numero, getColorCode(numero), new ArrayList<EntiteActiveJouable>());
	}

	public Equipe(int numero, ArrayList<EntiteActiveJouable> entites) {
		this(numero, getColorCode(numero), entites);
	}

	public Equipe(int numero, String colorCode, ArrayList<EntiteActiveJouable> entites) {
		this.numero = numero;
		this.colorCode = colorCode;
		this.entites = entites;
	}
	
	public void addEntite(EntiteActiveJouable entite) {
		entites.add(entite);
	}

	public static String getColorCode(int numero) {
		return EQUIPE_COLOR_CODE[numero % EQUIPE_COLOR_CODE.length];
	}
	
	public InEquipe toInEquipe() {
		ArrayList<InEntite> lie = new ArrayList();
		entites.forEach((e) -> {
			lie.add(e.toInEntite());
		});
		return new InEquipe(numero, colorCode, lie);
	}

}
