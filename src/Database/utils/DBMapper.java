package Database.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Database.settings.DataBaseSettings;
import Database.settings.SettingsManager;
import Console.utils.ConsoleDisplay;

/**
 * @author alexandre
 * DBMapper.java
 * Database connection.
 */
public class DBMapper {

	private final static String DATE_PATTERN = "HH:mm:ss dd/MM/YY"; //postgres
	//public final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"; //mysql
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
	
	public final static String JDBC_CLASS = "org.postgresql.Driver";

	private static Connection crtConnection;

	public final static int DUPLICATE_P_KEY_ERROR_CODE = 1062;

	public static void init() throws Exception {

		ConsoleDisplay.start("database connection");

		try {
			getPgSQLConnection();
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}

		ConsoleDisplay.success();
	}

	public static Connection getPgSQLConnection() throws SQLException {
		try {
			Class.forName(JDBC_CLASS);
		} catch (ClassNotFoundException e) {
			ConsoleDisplay.printStack(e);
		}
		
		DataBaseSettings dbSettings = SettingsManager.getDataBaseSettings();

		if (crtConnection == null) {
			crtConnection = DriverManager.getConnection("jdbc:postgresql://" + dbSettings.getAddress() + ":" + dbSettings.getPort()
					+ "/" + dbSettings.getDbName(), dbSettings.getUsername(), dbSettings.getPassword());
		}

		return crtConnection;
	}

	public static ResultSet executeQuery(String query, QueryType type, Object... args) throws SQLException {

		Connection database;
		ResultSet result = null;

		try {
			database = getPgSQLConnection();
		} catch (SQLException e1) {
			ConsoleDisplay.printStack(e1);
		}
		
		PreparedStatement stat = crtConnection.prepareStatement(query);
		//Fill args
		for (int i = 0; i < args.length; i++) {
			stat.setObject(i + 1, args[i]);
		}

		switch (type) {
			case SELECT:
				result = stat.executeQuery();
				break;
			default: //The Select is the only action that returns a resultSet.
				stat.executeUpdate();
				break;
		}

		return result;

	}

	/**
	 * Return current time. This method is in DBMapper to be sure it's use with
	 * database interactions.
	 * The used pattern is "HH:mm:ss dd/MM/YY".
	 */
	public static String getTimeNow() {

		return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
	}

	/**
	 * Parse a string to a date. Caution : this method has been made to parse
	 * date from data base who follow the "yyyy-MM-dd HH:mm:ss" pattern.
	 *
	 * @param s
	 *          String to parse.
	 * @return
	 *         Date from parsed string.
	 */
	public static Date parseDate(String s) {

		try {
			return DATE_FORMAT.parse(s);
		} catch (ParseException e) {
			System.err.println("ERROR : Failed to parse time.");
			return null;
		}
	}

	public enum QueryType {

		SELECT, UPDATE, DELETE, INSERT;
	}

}
