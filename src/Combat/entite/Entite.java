/*
 * 
 * 
 * 
 */
package Combat.entite;

import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.Orientation;
import static Serializable.InCombat.Orientation.NORD;
import Combat.entite.classe.ClasseEntite;
import Combat.sort.classe.SortPassif;
import Combat.sort.envout.Envoutement;
import HorsCombat.Modele.Reseau.Client;
import Serializable.InCombat.donnee.InEntite;
import Serializable.InCombat.donnee.InSortPassif;
import Serializable.Position;
import java.util.ArrayList;

/**
 * Entite.java
 *
 * @param <C>
 */
public class Entite<C extends ClasseEntite> {

	public final C classe;
	public final Client client;

	public final long id;
	public final int niveau;
	public Equipe equipe;
	public Position position;
	public Orientation orientation;
	public CaracteristiquePhysique caracs;
	public final PileAction pileAction;
	public final ArrayList<Envoutement> envoutements;
	protected boolean alive;

	public Entite(C classe, Client client, long id, int niveau, Equipe equipe) {
		this.classe = classe;
		this.client = client;
		this.id = id;
		this.niveau = niveau;
		this.equipe = equipe;
		this.position = null;
		this.orientation = NORD;
		this.caracs = new CaracteristiquePhysique(classe.caracPhysiqueMax, niveau);
		this.pileAction = new PileAction();
		this.envoutements = new ArrayList();
		this.alive = true;
	}

	public boolean isEntiteActive() {
		return this instanceof EntiteActiveJouable;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean canPlay() {
		return isAlive() && isEntiteActive();
	}
	
	public InEntite toInEntite() {
		ArrayList<InSortPassif> lisp = new ArrayList();
		for(SortPassif sp : classe.tabSortPassif) {
			lisp.add(new InSortPassif(sp.idClasseSort, sp.nom, sp.description));
		}
		return new InEntite(id, classe.nomClasse, classe.nomClasse, niveau, position, orientation, caracs, lisp, null);
	}

}
