/*
 * 
 * 
 * 
 */
package Combat.entite;

import Combat.entite.classe.ClasseEntite;
import HorsCombat.Modele.Reseau.Client;
import Serializable.Position;

/**
 * EntiteJouable.java
 * 
 * @param <C>
 */
public class EntiteJouable<C extends ClasseEntite> {
	
	public final C classe;
	public final Client client;
	
	public final long id;
	public final String nomDonne;
	public Equipe equipe;
	public Position position;

	public EntiteJouable(C classe, Client client, long id, String nomDonne, Equipe equipe) {
		this.classe = classe;
		this.client = client;
		this.id = id;
		this.nomDonne = nomDonne;
		this.equipe = equipe;
		this.position = null;
	}

}
