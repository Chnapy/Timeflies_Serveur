package Console.handlers.commands.impl;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import Console.handlers.commands.Command;
import Connection.storage.TokenBank;
import Connection.storage.UserInformation;
import Console.utils.ConsoleDisplay;
import Console.utils.ConsoleInput;

/**
 * @author alexandre
 * TokenListCmd.java
 */
public class TokenListCmd extends Command {

	private final static String CMD_TRIGGER = "tokenlist";

	@Override
	public void handle(String[] args) {
		ConcurrentHashMap<UUID, UserInformation> tokens = TokenBank.getCurrentInstance().getTokens();

		ConsoleDisplay.notice(tokens.size() + " tokens in bank.");
		ConsoleDisplay.notice("Display all ? (y/n)");

		if (ConsoleInput.getCurrentInst().nextLine().toLowerCase().charAt(0) == 'y') {
			for (UUID token : tokens.keySet()) {
				ConsoleDisplay.notice("->" + token + ", " + tokens.get(token));
			}
		}

		ConsoleDisplay.notice("");

	}

	@Override
	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}

}
