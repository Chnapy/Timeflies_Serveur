package Console.handlers.commands.impl;

import Console.handlers.CommandHandler;
import Console.handlers.commands.Command;
import Console.utils.ConsoleDisplay;
import Main.ThreadManager;
import HorsCombat.Modele.HCModele;

/**
 * @author alexandre
 * StopCmd.java
 */
public class StopCmd extends Command {

	private final static String CMD_TRIGGER = "stop";

	private CommandHandler cmdHandler;

	public StopCmd(CommandHandler commandHandler) {
		this.cmdHandler = commandHandler;
	}

	@Override
	public void handle(String[] args) {
		ConsoleDisplay.notice("Stopping...");

//		CombatHandler.arret();
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
