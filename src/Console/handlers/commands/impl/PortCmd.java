package Console.handlers.commands.impl;

import Console.handlers.commands.Command;
import Console.utils.ConsoleDisplay;
import Main.Data;

/**
 * @author alexandre
 * PortCmd.java
 */
public class PortCmd extends Command {

	private final static String CMD_TRIGGER = "port";

	@Override
	public void handle(String[] args) {
		ConsoleDisplay.notice("Current port : " + Data.SERV_PORT);
	}

	@Override
	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}

}
