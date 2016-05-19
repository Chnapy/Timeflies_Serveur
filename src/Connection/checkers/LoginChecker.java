package Connection.checkers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import Connection.exceptions.AuthentificationException;
import General.messages.login.LoginRequest;
import General.messages.server_login.ServerLoginRequest;
import Console.utils.ConsoleDisplay;
import Database.utils.DBMapper;
import static Database.utils.DBMapper.QueryType.INSERT;
import static Database.utils.DBMapper.QueryType.SELECT;

public class LoginChecker {

	private final static String LOGIN_CHECK_QUERY = "SELECT * FROM joueur natural join infoscompte WHERE lower(pseudo) = ? AND mdp = ?;";
	private final static String ADD_UUID_QUERY = "INSERT INTO session VALUES(?, ?, ?);";

	private static boolean isLoginCorrect(String login, String pwd) {
		ResultSet res;
		try {
			res = DBMapper.executeQuery(LOGIN_CHECK_QUERY, SELECT, login.toLowerCase(), pwd);
			return res.next();
		} catch (SQLException e) {
			ConsoleDisplay.debug(e);
		}

		return false;
	}

	public static boolean checkLogin(LoginRequest request) throws AuthentificationException {
		if (isLoginCorrect(request.login, request.pwd)) {
			return true;
		} else {
			throw new AuthentificationException();
		}
	}

	public static boolean checkServerLogin(ServerLoginRequest request) {
		//TODO verify that the user is a server.
		return isLoginCorrect(request.login, request.pwd);
	}

	public static void addUUIDToDB(UUID token, String ip) {
		try {
			DBMapper.executeQuery(ADD_UUID_QUERY, INSERT, ip, token.toString(), DBMapper.getTimeNow());
		} catch (SQLException e) {
			ConsoleDisplay.error("Failed to inster UUID into database.");
			ConsoleDisplay.printStack(e);
		}
	}

}
