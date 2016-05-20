/*
 * 
 * 
 * 
 */
package HorsCombat.Modele;

import Console.utils.ConsoleDisplay;
import static Database.InterfaceDatabase.getAllCaracEntite;
import static Database.InterfaceDatabase.getAllClasseEntites;
import static Database.InterfaceDatabase.getAllClasseSorts;
import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysiqueMax;
import MoteurJeu.gameplay.entite.classe.ClasseEntite;
import MoteurJeu.gameplay.entite.classe.ClasseEntiteActive;
import MoteurJeu.gameplay.entite.classe.ClasseEntitePassive;
import MoteurJeu.gameplay.sort.Sort;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.gameplay.sort.SortPassif;
import MoteurJeu.test.SortEnvoutementBonus;
import MoteurJeu.test.SortEnvoutementEffet;
import MoteurJeu.test.SortInvocationActive;
import MoteurJeu.test.SortInvocationPassive;
import MoteurJeu.test.SortPassifBonusVitesseAction;
import MoteurJeu.test.SortPassifEffetSoin;
import MoteurJeu.test.SortQuiFaitMal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InitialData.java
 *
 */
public class InitialData {

	public static final HashMap<Integer, ClasseEntite> allEntites = new HashMap();
	public static final HashMap<Integer, Sort> allSorts = new HashMap();

	public static void loadAllEntites() {
		try {
			firstLoadEntites();
			loadAllSorts();
			lastLoadEntites();
		} catch (SQLException ex) {
			Logger.getLogger(InitialData.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private static void firstLoadEntites() throws SQLException {
		ResultSet rs = getAllClasseEntites();
		while (rs.next()) {
			ResultSet rs2 = getAllCaracEntite(rs.getInt("idclasse"));
			int[] caracs = new int[5];
			int i = 0;
			while (rs2.next()) {
				caracs[i] = rs2.getInt("valeur");
				i++;
			}
			CaracteristiquePhysiqueMax cpm = new CaracteristiquePhysiqueMax(caracs[0], caracs[1], caracs[2], caracs[3], caracs[4]);
			switch (rs.getInt("type")) {
				case 0:
					allEntites.put(rs.getInt("idclasse"),
							new ClasseEntiteActive(rs.getInt("idclasse"), rs.getString("nom"), cpm));
					break;
				case 1:
					allEntites.put(rs.getInt("idclasse"),
							new ClasseEntiteActive(rs.getInt("idclasse"), rs.getString("nom"), cpm));
					break;
				case 2:
					allEntites.put(rs.getInt("idclasse"),
							new ClasseEntitePassive(rs.getInt("idclasse"), rs.getString("nom"), cpm));
					break;
			}
		}
	}

	private static void loadAllSorts() throws SQLException {
		ResultSet rs = getAllClasseSorts();
		while (rs.next()) {
			allSorts.put(rs.getInt("idclassesort"),
					getSortById(rs.getInt("idclassesort"), rs.getInt("idclasseentite"), rs.getString("nom"),
							rs.getString("description"), rs.getInt("tempsaction"),
							rs.getInt("cooldown"), rs.getInt("fatigue")));
		}
	}

	private static Sort getSortById(int id, int idClasseEntite, String nom, String description, int tempsAction, int cooldown, int fatigue) {
		switch (id) {
			case 1:
				return new SortQuiFaitMal(id, idClasseEntite, nom, description, tempsAction, cooldown, fatigue);
			case 2:
				return new SortEnvoutementBonus(id, idClasseEntite, nom, description, tempsAction, cooldown, fatigue);
			case 3:
				return new SortEnvoutementEffet(id, idClasseEntite, nom, description, tempsAction, cooldown, fatigue);
			case 4:
				return new SortInvocationActive(id, idClasseEntite, nom, description, tempsAction, cooldown, fatigue);
			case 5:
				return new SortInvocationPassive(id, idClasseEntite, nom, description, tempsAction, cooldown, fatigue);
			case 6:
				return new SortPassifBonusVitesseAction(id, idClasseEntite, nom, description);
			case 7:
				return new SortPassifEffetSoin(id, idClasseEntite, nom, description);
			default:
				ConsoleDisplay.error("ID sort non reconnu : " + id + ". Utilisation de l'id 1");
				return getSortById(1, idClasseEntite, nom, description, tempsAction, cooldown, fatigue);
		}
	}

	public static ClasseEntite getEntiteFromClass(Class c) {
		for (ClasseEntite ce : allEntites.values()) {
			if (c.equals(ce.getClass())) {
				return ce;
			}
		}
		return null;
	}

	private static void lastLoadEntites() throws SQLException {
		allEntites.keySet().stream().forEach((id) -> {
			loadSortsForEntite(id);
		});
	}

	private static void loadSortsForEntite(int idEntite) {
		ArrayList<SortActif> sortsActif = new ArrayList();
		ArrayList<SortPassif> sortsPassif = new ArrayList();

		allSorts.values().stream().forEach((s) -> {
			if (s.idClasseEntite == idEntite) {
				if (s.isSortActif()) {
					sortsActif.add((SortActif) s);
				} else {
					sortsPassif.add((SortPassif) s);
				}
			}
		});

		SortPassif[] sp = new SortPassif[sortsPassif.size()];
		for (int i = 0; i < sp.length; i++) {
			sp[i] = sortsPassif.get(i);
		}
		allEntites.get(idEntite).setTabSortPassif(sp);

		if (allEntites.get(idEntite).isEntiteActif()) {
			SortActif[] sa = new SortActif[sortsActif.size()];
			for (int i = 0; i < sa.length; i++) {
				sa[i] = sortsActif.get(i);
			}
			((ClasseEntiteActive) allEntites.get(idEntite)).setTabSortActif(sa);
		}
	}

}
