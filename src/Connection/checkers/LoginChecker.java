package Connection.checkers;

import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.exceptions.AuthentificationException;
import Serializable.messages.login.LoginRequest;
import Serializable.messages.server_login.ServerLoginRequest;
import Database.utils.DBMapper;
import static Database.utils.DBMapper.QueryType.SELECT;

public class LoginChecker {

	private final static String LOGIN_CHECK_QUERY = "SELECT idjoueur, pseudo FROM joueur natural join infoscompte WHERE lower(pseudo) = ? AND mdp = ?;";
//	private final static String ADD_UUID_QUERY = "INSERT INTO --- VALUES(?, ?, ?);";

	private static LoginPack isLoginCorrect(String login, String pwd) throws SQLException {
		ResultSet data = DBMapper.executeQuery(LOGIN_CHECK_QUERY, SELECT, login.toLowerCase(), pwd);
		data.next();
		
		return new LoginPack(data.getInt("idjoueur"), data.getString("pseudo"));
	}

	public static LoginPack checkLogin(LoginRequest request) throws AuthentificationException {
		try {
			return isLoginCorrect(request.login, request.pwd);
		} catch (SQLException ex) {
			throw new AuthentificationException();
		}
	}

	public static boolean checkServerLogin(ServerLoginRequest request) {
		//TODO verify that the user is a server.
		try {
			isLoginCorrect(request.login, request.pwd);
			return true;
		} catch (SQLException ex) {
		}
		return false;
	}

//	public static void addUUIDToDB(UUID token, String ip) {
//		try {
//			DBMapper.executeQuery(ADD_UUID_QUERY, INSERT, ip, token.toString(), DBMapper.getTime());
//		} catch (SQLException e) {
//			ConsoleDisplay.error("Failed to inster UUID into database.");
//			ConsoleDisplay.printStack(e);
//		}
//	}
	
	public static class LoginPack {
		public final int id;
		public final String pseudo;
		
		public LoginPack(int _id, String _pseudo) {
			id = _id;
			pseudo = _pseudo;
		}
	}

}
