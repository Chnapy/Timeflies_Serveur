/*
 * 
 * 
 * 
 */
package Database;

import Database.settings.SettingsManager;
import Database.utils.DBMapper;
import Database.utils.LibChecker;

/**
 * Database.java
 *
 */
public class Database {

	public static void init() throws Exception {

		LibChecker.check();
		SettingsManager.initSettings();
		DBMapper.init();
	}

}
