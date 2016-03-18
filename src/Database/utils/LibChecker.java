package Database.utils;

import Console.utils.ConsoleDisplay;

/**
 * @author alexandre
 * LibChecker.java
 * Check dependencies.
 */
public class LibChecker {

	public static void check() throws Exception {
		ConsoleDisplay.start("library checker");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}
		ConsoleDisplay.success();
	}

}
