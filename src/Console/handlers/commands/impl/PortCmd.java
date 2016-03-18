package Console.handlers.commands.impl;

import Console.handlers.commands.Command;
import Database.settings.SettingsManager;
import Console.utils.ConsoleDisplay;

/**
 * @author alexandre
 * PortCmd.java
 */
public class PortCmd extends Command {

	private final static String CMD_TRIGGER = "port";

	@Override
	public void handle(String[] args) {
		ConsoleDisplay.notice("Current port : " + SettingsManager.getNetworkSettings().getPort());
	}

	@Override
	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}

}
