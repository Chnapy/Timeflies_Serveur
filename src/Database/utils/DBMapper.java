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

	private static Connection database;

	private final static String DATE_PATTERN = "HH:mm:ss dd/MM/YY";
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

	public static void init() throws Exception {

		ConsoleDisplay.start("database connection");

		DataBaseSettings dbSettings = SettingsManager.getDataBaseSettings();

		try {
			database = DriverManager.getConnection("jdbc:postgresql://" + dbSettings.getAddress() + ":" + dbSettings.getPort()
					+ "/" + dbSettings.getDbName(), dbSettings.getUsername(), dbSettings.getPassword());
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}

		ConsoleDisplay.success();
	}

	public static Connection getDatabase() {
		return database;
	}

	public static ResultSet executeQuery(String query, Object... args) throws SQLException {
		if (database == null) {
			ConsoleDisplay.error("ERROR, Can't execute any query : no connection to database.");
			return null;
		}

		try {
			PreparedStatement stat = database.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				stat.setObject(i + 1, args[i]);
			}

			return stat.executeQuery();

		} catch (SQLException e) {
			ConsoleDisplay.error("ERROR : Statement creation failed.");
			throw e;
		}

	}

	/**
	 * Return current time. This method is in DBMapper to be sure it's use with
	 * database interactions.
	 * The used pattern is "HH:mm:ss dd/MM/YY".
	 */
	public static String getTime() {

		return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
	}

	/**
	 * Parse a string to a date. Caution : this method is made to parse date
	 * from data base who follow the "HH:mm:ss dd/MM/YY" pattern.
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
			ConsoleDisplay.error("ERROR : Failed to parse time.");
			ConsoleDisplay.printStack(e);
			return null;
		}
	}

}
