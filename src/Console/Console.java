/*
 * 
 * 
 * 
 */
package Console;

import CombatHandler.CombatHandlerRun;
import Connection.handlers.ConnectionsHandler;
import Console.handler.args.ArgsHandler;
import Console.handlers.CommandHandler;
import Console.utils.ConsoleInput;
import General.utils.ThreadManager;

/**
 * Console.java
 *
 */
public class Console {

	public static void init(String[] args) {
		ArgsHandler.init(args);
		ConsoleInput.init();
	}

	public static void initCmd(ConnectionsHandler connectionHandler, CombatHandlerRun combatHandlerRun) {
		CommandHandler cmdHandler = new CommandHandler(connectionHandler, combatHandlerRun);
		Thread t = new Thread(cmdHandler);
		t.start();
		ThreadManager.getCurrentInstance().add(t);
	}

}
