/*
 * 
 * 
 * 
 */
package Console;

import Console.handler.args.ArgsHandler;
import Console.handlers.CommandHandler;
import Console.utils.ConsoleInput;
import static Main.ThreadManager.EXEC;

/**
 * Console.java
 *
 */
public class Console {

	public static void init(String[] args) {
		ArgsHandler.init(args);
		ConsoleInput.init();
	}

	public static void initCmd() {
		CommandHandler cmdHandler = new CommandHandler();
		EXEC.submit(cmdHandler);
	}

}
