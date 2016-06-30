
/*
 * 
 * 
 * 
 */
package Combat.entite;

import Combat.entite.classe.ClasseEntiteActive;
import Combat.sort.classe.SortActif;
import Combat.sort.classe.SortPassif;
import HorsCombat.Modele.Reseau.Client;
import Serializable.InCombat.donnee.InEntiteActive;
import Serializable.InCombat.donnee.InSortActif;
import Serializable.InCombat.donnee.InSortPassif;
import java.util.ArrayList;

/**
 * EntiteActiveJouable.java
 *
 */
public class EntiteActiveJouable extends Entite<ClasseEntiteActive> {

	public final String nomDonne;

	public EntiteActiveJouable(ClasseEntiteActive classe, Client client, int niveau, Equipe equipe) {
		this(classe, client, -1, null, niveau, equipe);
	}

	public EntiteActiveJouable(ClasseEntiteActive classe, Client client, long id, String nomDonne, int niveau, Equipe equipe) {
		super(classe, client, id, niveau, equipe);
		this.nomDonne = nomDonne;
	}
	
	@Override
	public InEntiteActive toInEntite() {
		ArrayList<InSortActif> lisa = new ArrayList();
		for(SortActif sa : classe.tabSortActif) {
			lisa.add(new InSortActif(sa.idClasseSort, -1, sa.tempsAction, sa.cooldown, sa.fatigue, sa.getZonePortee(), sa.getZoneAction()));
		}
		
		ArrayList<InSortPassif> lisp = new ArrayList();
		for(SortPassif sp : classe.tabSortPassif) {
			lisp.add(new InSortPassif(sp.idClasseSort, -1));
		}
		
		return new InEntiteActive(id, classe.idClasse, client.infosCompte.idjoueur, nomDonne, niveau, position, orientation, caracs, lisp, lisa, classe.tempsDeplacement);
	}

}
