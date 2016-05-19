/*
 * 
 * 
 * 
 */
package Database;

import Database.utils.DBMapper;
import static Database.utils.DBMapper.QueryType.DELETE;
import static Database.utils.DBMapper.QueryType.INSERT;
import static Database.utils.DBMapper.QueryType.SELECT;
import General.utils.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlaceholderData.java
 *
 */
public class PlaceholderData {

	public static void init() {
		long id;
//		removeJoueur("TestPseudo1");
//		id = addNewJoueur("TestPseudo1", "123456", "test@mail.com");
//		id = addClasseEntite("Barbare");
//		id = addClasseSort(2, "SortBarbare", "Desc");
//		id = addEntite(1, 6, "NomEntite");
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
				DBMapper.executeQuery("insert into joueur values(default, ?, ?)", INSERT, idic, pseudo);
				rs = DBMapper.executeQuery("select idjoueur from joueur order by idjoueur desc limit 1", SELECT);
				if (rs.next()) {
					idJoueur = rs.getLong("idjoueur");
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(PlaceholderData.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idJoueur;
	}
	
	public static long addEntite(long idClasse, long idJoueur, String nom) {
		long idEntite = -1;
		try {
			DBMapper.executeQuery("insert into entite values(default, ?, ?, ?, ?, ?)", INSERT, idClasse, nom, idJoueur, 0, 0);
			ResultSet rs = DBMapper.executeQuery("select identite from entite order by identite desc limit 1", SELECT);
			if (rs.next()) {
				idEntite = rs.getLong("identite");
			}
		} catch (SQLException ex) {
			Logger.getLogger(PlaceholderData.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idEntite;
	}
	
	public static long addSort(long idClasse, long idEntite) {
		long idSort = -1;
		try {
			DBMapper.executeQuery("insert into sort values(default, ?, ?, ?, ?)", INSERT, 0, idClasse, idEntite, 0);
			ResultSet rs = DBMapper.executeQuery("select idsort from sort order by idsort desc limit 1", SELECT);
			if (rs.next()) {
				idSort = rs.getLong("idsort");
			}
		} catch (SQLException ex) {
			Logger.getLogger(PlaceholderData.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idSort;
	}

	public static long addClasseEntite(String nom) {
		long idClasse = -1;
		try {
			DBMapper.executeQuery("insert into classeentite values(default, ?)", INSERT, nom);
			ResultSet rs = DBMapper.executeQuery("select idclasse from classeentite order by idclasse desc limit 1", SELECT);
			if (rs.next()) {
				idClasse = rs.getLong("idclasse");
			}
		} catch (SQLException ex) {
			Logger.getLogger(PlaceholderData.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idClasse;
	}
	
	public static long addClasseSort(long idClasseEntite, String nomSort, String description) {
		long idClasseSort = -1;
		try {
			DBMapper.executeQuery("insert into classesort values(default, ?, ?, ?)", INSERT, nomSort, description, idClasseEntite);
			ResultSet rs = DBMapper.executeQuery("select idclassesort from classesort order by idclassesort desc limit 1", SELECT);
			if (rs.next()) {
				idClasseSort = rs.getLong("idclassesort");
			}
		} catch (SQLException ex) {
			Logger.getLogger(PlaceholderData.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idClasseSort;
	}

	public static void removeJoueur(String pseudo) {
		try {
			ResultSet rs = DBMapper.executeQuery("select idinfoscompte from infoscompte natural join joueur where pseudo=?", SELECT, pseudo);
			DBMapper.executeQuery("delete from joueur cascade where pseudo=?", DELETE, pseudo);
			if (rs.next()) {
				long idic = rs.getLong("idinfoscompte");
				DBMapper.executeQuery("delete from infoscompte cascade where idinfoscompte=?", DELETE, idic);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PlaceholderData.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
