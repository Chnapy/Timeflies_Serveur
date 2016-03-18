/*
 * 
 * 
 * 
 */
package Connection;

import Connection.handlers.ConnectionsHandler;
import Console.handler.args.handlers.TestModeHandler;
import Console.handlers.CommandHandler;
import Console.utils.ConsoleDisplay;
import General.utils.ThreadManager;

/**
 * Connection.java
 *
 */
public class Connection {
	
	private static ConnectionsHandler connectionHandler;

	public static void init() throws Exception {

		connectionHandler = new ConnectionsHandler();

		//Test mode
		if (TestModeHandler.isTestMode()) {
			ConsoleDisplay.notice("Test mode enable.");
			ConsoleDisplay.notice("Test mode is not available anymore.");

			//Normal mode
		} else {
			Thread connectionHandlerThread = new Thread(connectionHandler);
			ThreadManager.getCurrentInstance().add(connectionHandlerThread);
			connectionHandlerThread.start();
			ConsoleDisplay.notice("Waiting for connections...");

		}
	}
	
	public static ConnectionsHandler getConnectionHandler() {
		return connectionHandler;
	}

}
