/*
 * 
 * 
 * 
 */
package Console;

import Connection.handlers.ConnectionsHandler;
import Console.handler.args.ArgsHandler;
import Console.handlers.CommandHandler;
import Console.utils.ConsoleInput;
import static General.utils.ThreadManager.EXEC;

/**
 * Console.java
 *
 */
public class Console {

	public static void init(String[] args) {
		ArgsHandler.init(args);
		ConsoleInput.init();
	}

	public static void initCmd(ConnectionsHandler connectionHandler) {
		CommandHandler cmdHandler = new CommandHandler(connectionHandler);
		EXEC.submit(cmdHandler);
	}

}
