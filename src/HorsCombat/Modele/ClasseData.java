/*
 * 
 * 
 * 
 */
package HorsCombat.Modele;

import static HorsCombat.Modele.InterfacesBD.InterfaceBDGestionPersos.getAllClasseEntites;
import static HorsCombat.Modele.InterfacesBD.InterfaceBDGestionPersos.getAllClasseSorts;
import Combat.sort.classe.Sort;
import Combat.sort.classe.SortPassif;
import static HorsCombat.Modele.InterfacesBD.InterfaceBDGestionPersos.getAllCaracEntite;
import Serializable.HorsCombat.CaracteristiquePhysiqueMax;
import Combat.entite.classe.ClasseEntite;
import Combat.entite.classe.ClasseEntiteActive;
import Combat.entite.classe.ClasseEntitePassive;
import Combat.entite.classe.ClassePersonnage;
import Combat.sort.classe.SortActif;
import Combat.sort.finalsort.SortTestAttaque;
import Combat.sort.finalsort.SortTestTP;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClasseData.java
 *
 */
public class ClasseData {

	public static final HashMap<Integer, ClasseEntite> ALL_ENTITES = new HashMap();
	public static final HashMap<Integer, Sort> ALL_SORTS = new HashMap();

	public static void loadAllEntites() {
		try {
			firstLoadEntites();
			loadAllSorts();
			lastLoadEntites();
		} catch (SQLException ex) {
			Logger.getLogger(ClasseData.class.getName()).log(Level.SEVERE, null, ex);
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
					ALL_ENTITES.put(rs.getInt("idclasse"),
							new ClassePersonnage(rs.getInt("idclasse"), cpm, rs.getInt("tempsdeplacement")));
					break;
				case 1:
					ALL_ENTITES.put(rs.getInt("idclasse"),
							new ClasseEntiteActive(rs.getInt("idclasse"), cpm, rs.getInt("tempsdeplacement")));
					break;
				case 2:
					ALL_ENTITES.put(rs.getInt("idclasse"),
							new ClasseEntitePassive(rs.getInt("idclasse"), cpm));
					break;
			}
		}
	}

	private static void loadAllSorts() throws SQLException {
		ResultSet rs = getAllClasseSorts();
		while (rs.next()) {
			ALL_SORTS.put(rs.getInt("idclassesort"),
					getSortById(rs.getInt("idclassesort"), rs.getInt("idclasseentite"),
							rs.getInt("tempsaction"), rs.getInt("cooldown"),
							rs.getInt("fatigue"), rs.getInt("type")));
		}
	}

	private static void lastLoadEntites() throws SQLException {
		ALL_ENTITES.keySet().stream().forEach((id) -> {
			loadSortsForEntite(id);
		});
	}

	private static Sort getSortById(int id, int idClasseEntite, int tempsAction, int cooldown, int fatigue, int type) {
		switch (id) {
			case 1:
				return new SortTestAttaque(id, idClasseEntite, tempsAction, cooldown, fatigue);
			case 2:
				return new SortTestTP(id, idClasseEntite, tempsAction, cooldown, fatigue);
			default:
				return getSortById(1, idClasseEntite, tempsAction, cooldown, fatigue, type);
		}
	}

	public static ClasseEntite getEntiteFromClass(Class c) {
		for (ClasseEntite ce : ALL_ENTITES.values()) {
			if (c.equals(ce.getClass())) {
				return ce;
			}
		}
		return null;
	}

	private static void loadSortsForEntite(int idEntite) {
		ArrayList<SortActif> sortsActif = new ArrayList();
		ArrayList<SortPassif> sortsPassif = new ArrayList();

		ALL_SORTS.values().stream().forEach((s) -> {
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
		ALL_ENTITES.get(idEntite).setTabSortPassif(sp);

		if (ALL_ENTITES.get(idEntite).isEntiteActif()) {
			SortActif[] sa = new SortActif[sortsActif.size()];
			for (int i = 0; i < sa.length; i++) {
				sa[i] = sortsActif.get(i);
			}
			((ClasseEntiteActive) ALL_ENTITES.get(idEntite)).setTabSortActif(sa);
		}
	}

}
