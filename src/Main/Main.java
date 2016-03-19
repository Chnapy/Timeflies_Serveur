/*
 * 
 * 
 * 
 */
package Main;

import CombatHandler.CombatHandler;
import Connection.Connection;
import Connection.storage.TokenBank;
import Console.Console;
import Console.utils.ConsoleDisplay;
import Database.Database;
import General.utils.ThreadManager;

/**
 * Main.java
 * Main
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		ConsoleDisplay.splash();

		try {
			ThreadManager.init();

			Console.init(args);
			Database.init();
			TokenBank.init();
			CombatHandler.init();
			Connection.init();
			Console.initCmd(Connection.getConnectionHandler(), CombatHandler.getCombatHandlerRun());
		} catch (Exception e) {
			ConsoleDisplay.error("Failed to initialize. Stopping the program.");
			ConsoleDisplay.debug(e);
		}
	}

}
