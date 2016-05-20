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
 * InterfaceDatabase.java
 *
 */
public class InterfaceDatabase {

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
	
	public static long getIdJoueur(String pseudo) {
		long id = -1;
		try {
			ResultSet rs = DBMapper.executeQuery("select idjoueur from joueur where lower(pseudo)=?", SELECT, pseudo.toLowerCase());
			if(rs.next())
				id = rs.getLong("idjoueur");
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id;
	}
	
	public static ResultSet getAllInfosJoueur(String pseudo) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from joueur natural join infoscompte where lower(pseudo)=?", SELECT, pseudo.toLowerCase());
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}
	
	public static ResultSet getPersoNivClasse(long idEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select victoires, defaites, nom from entite natural join classeentite where identite=?", SELECT, idEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}
	
	public static ResultSet getSortNiveau(int idClasseSort, long idEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select niveau, xp from sort natural join classesort where idclassesort=? and identite=?", SELECT, idClasseSort, idEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}
	
	public static ResultSet getAllInfosEntitesJoueur(String pseudo) {
		long idJoueur = getIdJoueur(pseudo);
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select identite, idclasse, nomdonnee, victoires, defaites from entite natural join classeentite where idjoueur=?", SELECT, idJoueur);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}
	
	public static ResultSet getAllCaracEntite(long idClasseEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from caracphysique where idclasseentite=? order by idcaracphysiquenom asc", SELECT, idClasseEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}
	
	public static ResultSet getAllSortsEntite(long idEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from sort natural join classesort where identite=?", SELECT, idEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}
	
	public static ResultSet getAllClasseEntites() {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from classeentite", SELECT);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}
	
	public static ResultSet getAllClasseSorts() {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from classesort", SELECT);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
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
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idSort;
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
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(InterfaceDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
