/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.InterfacesBD;

import Console.utils.ConsoleDisplay;
import Database.utils.DBMapper;
import static Database.utils.DBMapper.QueryType.SELECT;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InterfaceBDLog.java
 *
 */
public class InterfaceBDLog {

	public static boolean isLoginCorrect(String login, String pwd) {
		ResultSet res;
		try {
			res = DBMapper.executeQuery("select * from joueur natural join infoscompte where lower(pseudo)=? and mdp=?", SELECT, login.toLowerCase(), pwd);
			return res.next();
		} catch (SQLException e) {
			ConsoleDisplay.debug(e);
		}

		return false;
	}
	
	public static long getIdJoueur(String pseudo) {
		long id = -1;
		try {
			ResultSet rs = DBMapper.executeQuery("select idjoueur from joueur where lower(pseudo)=?", SELECT, pseudo.toLowerCase());
			if(rs.next())
				id = rs.getLong("idjoueur");
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id;
	}
	
	public static ResultSet getAllInfosJoueur(String pseudo) {
		ResultSet rs = null;
		try {
			rs = DBMapper.executeQuery("select * from joueur natural join infoscompte where lower(pseudo)=?", SELECT, pseudo.toLowerCase());
		} catch (SQLException ex) {
			Logger.getLogger(InterfaceBDAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

}
