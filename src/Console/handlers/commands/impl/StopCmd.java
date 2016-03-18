package Console.handlers.commands.impl;

import CombatHandler.CombatHandlerRun;
import Console.handlers.CommandHandler;
import Connection.handlers.ConnectionsHandler;
import Console.handlers.commands.Command;
import Console.utils.ConsoleDisplay;
import General.utils.ThreadManager;

/**
 * @author alexandre
 * StopCmd.java
 */
public class StopCmd extends Command {

	private final static String CMD_TRIGGER = "stop";

	private ConnectionsHandler cHandler;
	private CombatHandlerRun combatHandlerRun;
	private CommandHandler cmdHandler;

	public StopCmd(ConnectionsHandler handler, CombatHandlerRun combatHandlerRun, CommandHandler commandHandler) {
		this.cHandler = handler;
		this.combatHandlerRun = combatHandlerRun;
		this.cmdHandler = commandHandler;
	}

	@Override
	public void handle(String[] args) {
		ConsoleDisplay.notice("Stopping...");

		combatHandlerRun.arret();
		cHandler.stopListen();
		cmdHandler.requestStop();
		ThreadManager.getCurrentInstance().killAllThreads();
	}

	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}

}
