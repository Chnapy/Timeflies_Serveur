package Console.handlers.commands.impl;

import CombatHandler.CombatHandler;
import Console.handlers.CommandHandler;
import Connection.handlers.ConnectionsHandler;
import Console.handlers.commands.Command;
import Console.utils.ConsoleDisplay;
import General.utils.ThreadManager;
import HorsCombat.Modele.HCModele;

/**
 * @author alexandre
 * StopCmd.java
 */
public class StopCmd extends Command {

	private final static String CMD_TRIGGER = "stop";

	private ConnectionsHandler cHandler;
	private CommandHandler cmdHandler;

	public StopCmd(ConnectionsHandler handler, CommandHandler commandHandler) {
		this.cHandler = handler;
		this.cmdHandler = commandHandler;
	}

	@Override
	public void handle(String[] args) {
		ConsoleDisplay.notice("Stopping...");

		CombatHandler.arret();
//		cHandler.stopListen();
		cmdHandler.requestStop();
		HCModele.SERVEUR.stopServeur();
		ThreadManager.killAllThreads();
	}

	@Override
	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}

}
