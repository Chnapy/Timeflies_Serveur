/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.InterfacesBD;

import Database.utils.DBMapper;
import static Database.utils.DBMapper.QueryType.INSERT;
import static Database.utils.DBMapper.QueryType.SELECT;
import static HorsCombat.Modele.InterfacesBD.InterfaceBDLog.getIdJoueur;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InterfaceBDGestionPersos.java
 *
 */
public class InterfaceBDGestionPersos {
	
	public static ResultSet getAllClasseSortFromEntite(int idClasseEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from classesort where idclasseentite=?", SELECT, idClasseEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	public static ResultSet getPersoNivClasse(long idEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select victoires, defaites, nom from entite natural join classeentite where identite=?", SELECT, idEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	public static ResultSet getSortNiveau(int idClasseSort, long idEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select niveau, xp from sort natural join classesort where idclassesort=? and identite=?", SELECT, idClasseSort, idEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	public static ResultSet getAllInfosEntitesJoueur(String pseudo) {
		long idJoueur = getIdJoueur(pseudo);
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select identite, idclasse, nomdonnee, victoires, defaites from entite natural join classeentite where idjoueur=?", SELECT, idJoueur);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	public static ResultSet getAllCaracEntite(long idClasseEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from caracphysique where idclasseentite=? order by idcaracphysiquenom asc", SELECT, idClasseEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	public static ResultSet getAllSortsEntite(long idEntite) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from sort natural join classesort where identite=?", SELECT, idEntite);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	public static ResultSet getAllClasseEntites() {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from classeentite", SELECT);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	public static ResultSet getAllClasseSorts() {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from classesort", SELECT);
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
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
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return idSort;
	}

}
