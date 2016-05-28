
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
import Serializable.InCombat.donnee.InEntite;
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
		this(classe, client, -1, classe.nomClasse, niveau, equipe);
	}

	public EntiteActiveJouable(ClasseEntiteActive classe, Client client, long id, String nomDonne, int niveau, Equipe equipe) {
		super(classe, client, id, niveau, equipe);
		this.nomDonne = nomDonne;
	}
	
	@Override
	public InEntite toInEntite() {
		ArrayList<InSortActif> lisa = new ArrayList();
		for(SortActif sa : classe.tabSortActif) {
			lisa.add(new InSortActif(sa.idClasseSort, sa.nom, sa.description, sa.tempsAction, sa.cooldown, sa.fatigue, sa.getZonePortee(), sa.getZoneAction()));
		}
		
		ArrayList<InSortPassif> lisp = new ArrayList();
		for(SortPassif sp : classe.tabSortPassif) {
			lisp.add(new InSortPassif(sp.idClasseSort, sp.nom, sp.description));
		}
			
		return new InEntite(id, nomDonne, classe.nomClasse, niveau, position, orientation, caracs, lisp, lisa);
	}

}
