/*
 * 
 * 
 * 
 */
package HorsCombat.Modele;

import HorsCombat.Modele.InterfacesBD.InterfaceBDLog;
import HorsCombat.Modele.Reseau.Serveur;
import Console.utils.ConsoleDisplay;
import static HorsCombat.Modele.InterfacesBD.InterfaceBDGestionPersos.*;
import static HorsCombat.Modele.InterfacesBD.InterfaceBDLog.getAllInfosJoueur;
import HorsCombat.Modele.Reseau.Client;
import static Main.Utils.sha1;
import Combat.entite.classe.ClasseEntite;
import Combat.entite.classe.ClassePersonnage;
import Combat.sort.classe.Sort;
import Serializable.HorsCombat.GestionPersos.AllClassePerso;
import Serializable.HorsCombat.HCPersonnage;
import Serializable.HorsCombat.HCSort;
import Serializable.HorsCombat.Niveau;
import Serializable.Log.Log.AnswerLogs;
import Serializable.Log.Log.AskLogs;
import Serializable.Log.Log.InfosCompte;
import Combat.sort.classe.SortActif;
import Combat.sort.classe.SortPassif;
import Serializable.HorsCombat.HorsCombat.DonneePerso;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HCModele.java
 *
 */
public class HCModele {

	public static final Serveur SERVEUR = new Serveur();

	public static void init() {
		ClasseData.loadAllEntites();
		try {
			MapsData.loadAllMaps();
		} catch (IOException ex) {
			Logger.getLogger(HCModele.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
//	public static ArrayList<DonneePerso> getDonnee

	public static long creaPerso(long idJoueur, int idClasse, String nomDonne) throws SQLException {

		long idEntite = addEntite(idClasse, idJoueur, nomDonne);

		ResultSet rs = getAllClasseSortFromEntite(idClasse);
		while (rs.next()) {
			addSort(rs.getLong("idclassesort"), idEntite);
		}
		return idEntite;
	}

	public static AllClassePerso getAllClassePerso() throws SQLException {
		int size = 0;
		for (ClasseEntite ce : ClasseData.ALL_ENTITES.values()) {
			if (ce instanceof ClassePersonnage) {
				size++;
			}
		}
		HCPersonnage[] persos = new HCPersonnage[size];

		int i = 0;
		for (ClasseEntite ce : ClasseData.ALL_ENTITES.values()) {
			if (ce instanceof ClassePersonnage) {
				persos[i] = getHCPersonnageFromClasseEntite((ClassePersonnage) ce, -1,
						ce.nomClasse, 0, new HashMap<Integer, Niveau>());
				i++;
			}
		}
		return new AllClassePerso(persos);
	}

	public static AnswerLogs getClientData(AskLogs askLogs) throws SQLException {
		boolean accepted = InterfaceBDLog.isLoginCorrect(askLogs.pseudo, sha1(askLogs.mdp));
		if (accepted) {
			AnswerLogs answer = getPlayerAnswerLogs(askLogs.pseudo);

			for (Client c : Serveur.ALL_CLIENTS) {
				if (c.infosCompte != null
						&& c.infosCompte.idjoueur == answer.infosCompte.idjoueur) {
					c.deconnexion();
					break;
				}
			}
			return answer;
		}
		return new AnswerLogs(false, null, null, null);
	}

	private static AnswerLogs getPlayerAnswerLogs(String pseudo) throws SQLException {
		ResultSet rsInfos = getAllInfosJoueur(pseudo);
		ResultSet rsEntites = getAllInfosEntitesJoueur(pseudo);

		InfosCompte ic;
		HCPersonnage[] persos;
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

			if (!(ClasseData.ALL_ENTITES.get(rsEntites.getInt("idclasse")) instanceof ClassePersonnage)) {
				ConsoleDisplay.error("La classe entité d'ID " + rsEntites.getInt("idclasse") + " est passive mais possède un joueur !");
			}
			ClassePersonnage cea = (ClassePersonnage) ClasseData.ALL_ENTITES.get(rsEntites.getInt("idclasse"));

			HashMap<Integer, Niveau> sortsNiveaux = new HashMap();
			ResultSet rsSort;
			Sort sa;
			for (SortActif sort : cea.tabSortActif) {
				sa = sort;
				rsSort = getSortNiveau(sa.idClasseSort, idEntite);
				if (rsSort.next()) {
					sortsNiveaux.put(sa.idClasseSort, new Niveau(rsSort.getInt("niveau"), rsSort.getInt("xp")));
				} else {
					sortsNiveaux.put(sa.idClasseSort, new Niveau());
				}
			}

			for (SortPassif sort : cea.tabSortPassif) {
				sa = sort;
				rsSort = getSortNiveau(sa.idClasseSort, idEntite);
				if (rsSort.next()) {
					sortsNiveaux.put(sa.idClasseSort, new Niveau(rsSort.getInt("niveau"), rsSort.getInt("xp")));
				} else {
					sortsNiveaux.put(sa.idClasseSort, new Niveau());
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
			String nomDonne, int persoNiveau, HashMap<Integer, Niveau> sortsNiveaux) {

		HCSort[] hcsa = new HCSort[cea.tabSortActif.length];
		SortActif sa;
		for (int i = 0; i < hcsa.length; i++) {
			sa = cea.tabSortActif[i];
			hcsa[i] = new HCSort(sa.idClasseSort, sa.nom, sa.description, sortsNiveaux.getOrDefault(sa.idClasseSort, new Niveau(0, 0)), sa.tempsAction, sa.cooldown, sa.fatigue);
		}

		HCSort[] hcsp = new HCSort[cea.tabSortPassif.length];
		SortPassif sp;
		for (int i = 0; i < hcsp.length; i++) {
			sp = cea.tabSortPassif[i];
			hcsp[i] = new HCSort(sp.idClasseSort, sp.nom, sp.description, sortsNiveaux.getOrDefault(sp.idClasseSort, new Niveau(0, 0)));
		}

		return new HCPersonnage(identite, cea.idClasse, nomDonne, cea.nomClasse, cea.caracPhysiqueMax, persoNiveau, hcsa, hcsp);
	}

	public static HCSort getHCSortFromClasseSort(Sort sa, Niveau niveau) {
		return new HCSort(sa.idClasseSort, sa.nom, sa.description, niveau);
	}
	/*
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
	 */
}
