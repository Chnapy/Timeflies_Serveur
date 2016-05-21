/*
 * 
 * 
 * 
 */
package HorsCombat.Modele;

import Connection.checkers.LoginChecker;
import Console.utils.ConsoleDisplay;
import Database.InterfaceDatabase;
import static Database.InterfaceDatabase.getAllInfosEntitesJoueur;
import static Database.InterfaceDatabase.getAllInfosJoueur;
import static Database.InterfaceDatabase.getSortNiveau;
import static General.utils.Utils.sha1;
import HorsCombat.Modele.Client.Client;
import MoteurJeu.gameplay.entite.classe.ClasseEntite;
import MoteurJeu.gameplay.entite.classe.ClassePersonnage;
import MoteurJeu.gameplay.sort.Sort;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;
import Serializable.Combat.InfosCombat.AllClassePerso;
import Serializable.Logs.AnswerLogs;
import Serializable.Logs.AnswerLogs.InfosCompte;
import Serializable.Logs.AskLogs;
import Serializable.Personnages.HCPersonnage;
import Serializable.Personnages.Sort.HCSort;
import Serializable.Personnages.Sort.HCSort.HCNiveau;
import Serializable.Personnages.Sort.HCSortActif;
import Serializable.Personnages.Sort.HCSortPassif;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * HCModele.java
 *
 */
public class HCModele {

	public static final Serveur SERVEUR = new Serveur();
	public static final ArrayList<Client> ALLCLIENTS = SERVEUR.allClients;

	public static void init() {
		InitialData.loadAllEntites();
	}

	public static long creaPerso(long idJoueur, int idClasse, String nomDonne) throws SQLException {
		
		long idEntite = InterfaceDatabase.addEntite(idClasse, idJoueur, nomDonne);
		
		ResultSet rs = InterfaceDatabase.getAllClasseSortFromEntite(idClasse);
		while(rs.next()) {
			InterfaceDatabase.addSort(rs.getLong("idclassesort"), idEntite);
		}
		return idEntite;
	}

	public static AllClassePerso getAllClassePerso() throws SQLException {
		int size = 0;
		for (ClasseEntite ce : InitialData.allEntites.values()) {
			if (ce instanceof ClassePersonnage) {
				size++;
			}
		}
		HCPersonnage[] persos = new HCPersonnage[size];

		int i = 0;
		for (ClasseEntite ce : InitialData.allEntites.values()) {
			if (ce instanceof ClassePersonnage) {
				persos[i] = getHCPersonnageFromClasseEntite((ClassePersonnage) ce, -1,
						ce.nomClasse, 0, new HashMap<Integer, HCNiveau>());
				i++;
			}
		}
		return new AllClassePerso(persos);
	}

	public static AnswerLogs getClientData(AskLogs askLogs) throws SQLException {
		boolean accepted = LoginChecker.isLoginCorrect(askLogs.pseudo, sha1(askLogs.mdp));
		if (accepted) {
			return getPlayerAnswerLogs(askLogs.pseudo);
		}
		return new AnswerLogs(false, null, null, null);
	}

	private static AnswerLogs getPlayerAnswerLogs(String pseudo) throws SQLException {
		ResultSet rsInfos = getAllInfosJoueur(pseudo);
		ResultSet rsEntites = getAllInfosEntitesJoueur(pseudo);

		InfosCompte ic = null;
		HCPersonnage[] persos = null;
		HashMap<String, int[]> equipes = new HashMap();

		if (!rsInfos.next()) {
			ConsoleDisplay.error("InfosCompte introuvable : " + pseudo);
		}

		int argent = rsInfos.getInt("argent");
		double ratio;
		if (rsInfos.getDouble("defaites") == 0) {
			ratio = rsInfos.getDouble("victoires");
		} else {
			ratio = rsInfos.getDouble("victoires") / rsInfos.getDouble("defaites");
		}
		ic = new AnswerLogs.InfosCompte(rsInfos.getLong("idjoueur"), pseudo, argent, ratio);

		ArrayList<HCPersonnage> listPersos = new ArrayList();
		while (rsEntites.next()) {
			int idEntite = rsEntites.getInt("identite");
			String nomDonne = rsEntites.getString("nomdonnee");
			int niveau;
			if (rsEntites.getInt("defaites") == 0) {
				niveau = rsEntites.getInt("victoires");
			} else {
				niveau = (int) (rsEntites.getDouble("victoires") / rsInfos.getDouble("defaites"));
			}

			if (!(InitialData.allEntites.get(rsEntites.getInt("idclasse")) instanceof ClassePersonnage)) {
				ConsoleDisplay.error("La classe entité d'ID " + rsEntites.getInt("idclasse") + " est passive mais possède un joueur !");
			}
			ClassePersonnage cea = (ClassePersonnage) InitialData.allEntites.get(rsEntites.getInt("idclasse"));

			HashMap<Integer, HCNiveau> sortsNiveaux = new HashMap();
			ResultSet rsSort;
			Sort sa;
			for (SortActif sort : cea.tabSortActif) {
				sa = sort;
				rsSort = getSortNiveau(sa.id, idEntite);
				if (rsSort.next()) {
					sortsNiveaux.put(sa.id, new HCNiveau(rsSort.getInt("niveau"), rsSort.getInt("xp")));
				} else {
					sortsNiveaux.put(sa.id, new HCNiveau(0, 0));
				}
			}

			for (SortPassif sort : cea.tabSortPassif) {
				sa = sort;
				rsSort = getSortNiveau(sa.id, idEntite);
				if (rsSort.next()) {
					sortsNiveaux.put(sa.id, new HCNiveau(rsSort.getInt("niveau"), rsSort.getInt("xp")));
				} else {
					sortsNiveaux.put(sa.id, new HCNiveau(0, 0));
				}
			}
			listPersos.add(getHCPersonnageFromClasseEntite(cea, idEntite, nomDonne, niveau, sortsNiveaux));
		}
		persos = new HCPersonnage[listPersos.size()];
		for (int i = 0; i < persos.length; i++) {
			persos[i] = listPersos.get(i);
		}

		return new AnswerLogs(true, ic, persos, equipes);
	}

	public static HCPersonnage getHCPersonnageFromClasseEntite(ClassePersonnage cea, int identite,
			String nomDonne, int persoNiveau, HashMap<Integer, HCNiveau> sortsNiveaux) {

		HCSortActif[] hcsa = new HCSortActif[cea.tabSortActif.length];
		SortActif sa;
		for (int i = 0; i < hcsa.length; i++) {
			sa = cea.tabSortActif[i];
			hcsa[i] = new HCSortActif(sa.nom, sortsNiveaux.getOrDefault(sa.id, new HCNiveau(0, 0)),
					sa.description, sa.tempsAction, sa.cooldown, sa.fatigue);
		}

		HCSortPassif[] hcsp = new HCSortPassif[cea.tabSortPassif.length];
		SortPassif sp;
		for (int i = 0; i < hcsp.length; i++) {
			sp = cea.tabSortPassif[i];
			hcsp[i] = new HCSortPassif(sp.nom, sortsNiveaux.getOrDefault(sp.id, new HCNiveau(0, 0)),
					sp.description);
		}

		return new HCPersonnage(identite, cea.id, nomDonne, cea.nomClasse, persoNiveau,
				cea.caracPhysiqueMax.vitalite, cea.caracPhysiqueMax.tempsaction,
				cea.caracPhysiqueMax.tempssup, cea.caracPhysiqueMax.vitesse,
				cea.caracPhysiqueMax.fatigue, hcsa, hcsp);
	}

	public static HCSortActif getHCSortActifFromClasseSort(SortActif sa, HCNiveau niveau) {
		return new HCSortActif(sa.nom, niveau, sa.description,
				sa.tempsAction, sa.cooldown, sa.fatigue);
	}

	public static HCSortPassif getHCSortActifFromClasseSort(SortPassif sp, HCNiveau niveau) {
		return new HCSortPassif(sp.nom, niveau, sp.description);
	}

	private static AnswerLogs getExamplePack() {
		boolean accepted = true;
		String pseudo = "TestPseudo";
		int argent = 4578;
		double ratio = 1.8;

		HCSortActif sa1 = new HCSortActif("TestSA1", new HCSort.HCNiveau(5, 214), "Description de TestSA1", 1500, 0, 0);
		HCSortActif sa2 = new HCSortActif("TestSA2", new HCSort.HCNiveau(2, 85), "Description de TestSA2", 1900, 2, 0);
		HCSortActif sa3 = new HCSortActif("TestSA3", new HCSort.HCNiveau(8, 785), "Description de TestSA3", 1100, 1, 0);

		HCSortPassif sp1 = new HCSortPassif("TestSP1", new HCSort.HCNiveau(4, 187), "Description de TestSP1");
		HCSortPassif sp2 = new HCSortPassif("TestSP2", new HCSort.HCNiveau(0, 12), "Description de TestSP2");

		HCPersonnage perso1 = new HCPersonnage(12, 0, "Perso1", "Classe1", 8, 125, 38000, 5000, 100, 0,
				new HCSortActif[]{sa1, sa2, sa3}, new HCSortPassif[]{sp1, sp2});
		HCPersonnage perso2 = new HCPersonnage(13, 0, "Perso2", "Classe2", 2, 120, 35000, 4000, 110, 10,
				new HCSortActif[]{sa3}, new HCSortPassif[]{sp2});

		HashMap<String, int[]> equipes = new HashMap();
		equipes.put("Equipe1", new int[]{1, 2});
		equipes.put("Equipe2", new int[]{2});

		return new AnswerLogs(accepted,
				new AnswerLogs.InfosCompte(13, pseudo, argent, ratio),
				new HCPersonnage[]{
					perso1, perso2
				}, equipes);
	}

}
