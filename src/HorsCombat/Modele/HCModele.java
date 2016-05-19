/*
 * 
 * 
 * 
 */
package HorsCombat.Modele;

import HorsCombat.Modele.Client.Client;
import Serializable.Logs.AnswerLogs;
import Serializable.Logs.AskLogs;
import Serializable.Personnages.HCPersonnage;
import Serializable.Personnages.Sort.Sort;
import Serializable.Personnages.Sort.SortActif;
import Serializable.Personnages.Sort.SortPassif;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * HCModele.java
 * 
 */
public class HCModele {
	
	public static final Serveur SERVEUR = new Serveur();
	public static final ArrayList<Client> ALLCLIENTS = SERVEUR.allClients;

	public static AnswerLogs getClientData(AskLogs askLogs) {
		return getExamplePack();
	}

	private static final AnswerLogs getExamplePack() {
		boolean accepted = true;
		String pseudo = "TestPseudo";
		int argent = 4578;
		double ratio = 1.8;

		SortActif sa1 = new SortActif("TestSA1", new Sort.Niveau(5, 214), "Description de TestSA1", 1500, 0, 8, 2);
		SortActif sa2 = new SortActif("TestSA2", new Sort.Niveau(2, 85), "Description de TestSA2", 1900, 2, 5, 1);
		SortActif sa3 = new SortActif("TestSA3", new Sort.Niveau(8, 785), "Description de TestSA3", 1100, 1, 6, 3);

		SortPassif sp1 = new SortPassif("TestSP1", new Sort.Niveau(4, 187), "Description de TestSP1");
		SortPassif sp2 = new SortPassif("TestSP2", new Sort.Niveau(0, 12), "Description de TestSP2");

		HCPersonnage perso1 = new HCPersonnage(12, "Perso1", "Classe1", 8, 125, 38000, 5000, 100, 0,
				new SortActif[]{sa1, sa2, sa3}, new SortPassif[]{sp1, sp2});
		HCPersonnage perso2 = new HCPersonnage(13, "Perso2", "Classe2", 2, 120, 35000, 4000, 110, 10,
				new SortActif[]{sa3}, new SortPassif[]{sp2});

		HashMap<String, int[]> equipes = new HashMap();
		equipes.put("Equipe1", new int[]{1, 2});
		equipes.put("Equipe2", new int[]{2});

		return new AnswerLogs(accepted, new AnswerLogs.InfosCompte(pseudo, argent, ratio), new HCPersonnage[]{
			perso1, perso2
		}, equipes);
	}

}
