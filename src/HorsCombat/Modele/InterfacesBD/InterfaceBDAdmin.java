/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.InterfacesBD;

import Database.utils.DBMapper;
import static Database.utils.DBMapper.QueryType.DELETE;
import static Database.utils.DBMapper.QueryType.INSERT;
import static Database.utils.DBMapper.QueryType.SELECT;
import Main.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InterfaceBDAdmin.java
 *
 */
public class InterfaceBDAdmin {

	public static void init() {
		long id;
//		removeJoueur("TestPseudo1");
//		id = addNewJoueur("TestPseudo1", "123456", "test@mail.com");
//		id = addClasseEntite("Boucher", 158, 32000, 4500, 101, 2);
//		id = addClasseSort(2, "SortKiTu", "Desc", 1, 10000, 0);
//		id = addEntite(2, 6, "Test2Entite");
//		id = addSort(2, 6);
//		System.out.println(id);
	}

	public static long addNewJoueur(String pseudo, String mdp, String mail) {
		mdp = Utils.sha1("123456");
		long idJoueur = -1;
		try {
			DBMapper.executeQuery("insert into infoscompte values(default, ?, ?)", INSERT, mail, mdp);
			ResultSet rs = DBMapper.executeQuery("select idinfoscompte from infoscompte order by idinfoscompte desc limit 1", SELECT);
			if (rs.next()) {
				long idic = rs.getLong("idinfoscompte");
				DBMapper.executeQuery("insert into joueur values(default, ?, ?, ?, ?, ?)", INSERT, idic, pseudo, 0, 0, 0);
				rs = DBMapper.executeQuery("select idjoueur from joueur order by idjoueur desc limit 1", SELECT);
				if (rs.next()) {
					idJoueur = rs.getLong("idjoueur");
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idJoueur;
	}

	public static long addClasseEntite(String nom, int vitalite, int ta, int ts, int vitesse, int fatigue) {
		long idClasse = -1;
		try {
			DBMapper.executeQuery("insert into classeentite values(default, ?)", INSERT, nom);
			ResultSet rs = DBMapper.executeQuery("select idclasse from classeentite order by idclasse desc limit 1", SELECT);
			if (rs.next()) {
				idClasse = rs.getLong("idclasse");
				DBMapper.executeQuery("insert into caracphysique values(default, ?, ?, ?)", INSERT, idClasse, 1, vitalite);
				DBMapper.executeQuery("insert into caracphysique values(default, ?, ?, ?)", INSERT, idClasse, 2, ta);
				DBMapper.executeQuery("insert into caracphysique values(default, ?, ?, ?)", INSERT, idClasse, 3, ts);
				DBMapper.executeQuery("insert into caracphysique values(default, ?, ?, ?)", INSERT, idClasse, 4, vitesse);
				DBMapper.executeQuery("insert into caracphysique values(default, ?, ?, ?)", INSERT, idClasse, 5, fatigue);
			}
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idClasse;
	}
	
	public static long addClasseSort(long idClasseEntite, String nomSort, String description, int cooldown, int tempsAction, int type) {
		long idClasseSort = -1;
		try {
			DBMapper.executeQuery("insert into classesort values(default, ?, ?, ?, ?, ?, ?)", INSERT, nomSort, description, idClasseEntite, cooldown, tempsAction, type);
			ResultSet rs = DBMapper.executeQuery("select idclassesort from classesort order by idclassesort desc limit 1", SELECT);
			if (rs.next()) {
				idClasseSort = rs.getLong("idclassesort");
			}
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idClasseSort;
	}

	public static void removeJoueur(String pseudo) {
		try {
			ResultSet rs = DBMapper.executeQuery("select idinfoscompte from infoscompte natural join joueur where lower(pseudo)=?", SELECT, pseudo.toLowerCase());
			DBMapper.executeQuery("delete from joueur cascade where lower(pseudo)=?", DELETE, pseudo.toLowerCase());
			if (rs.next()) {
				long idic = rs.getLong("idinfoscompte");
				DBMapper.executeQuery("delete from infoscompte cascade where idinfoscompte=?", DELETE, idic);
			}
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
