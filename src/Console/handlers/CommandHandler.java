package Console.handlers;

import CombatHandler.CombatHandlerRun;
import Connection.handlers.ConnectionsHandler;
import java.util.HashMap;

import Console.handlers.commands.Command;
import Console.handlers.commands.impl.PortCmd;
import Console.handlers.commands.impl.StopCmd;
import Console.handlers.commands.impl.TokenListCmd;
import Console.utils.ConsoleDisplay;
import Console.utils.ConsoleInput;

/**
 * @author alexandre
 * CommandHandler.java
 */
public class CommandHandler implements Runnable {

	private ConsoleInput cInput;

	private ConnectionsHandler connectionHandler;
	private CombatHandlerRun combatHandlerRun;
	private HashMap<String, Command> commands;
	private boolean cont;

	public CommandHandler(ConnectionsHandler ch, CombatHandlerRun chr) {
		cInput = ConsoleInput.getCurrentInst();
		this.connectionHandler = ch;
		this.combatHandlerRun = chr;
		commands = new HashMap<String, Command>();
		initCommand();
		cont = true;
	}

	private void initCommand() {
		StopCmd stop = new StopCmd(connectionHandler, combatHandlerRun, this);
		PortCmd port = new PortCmd();
		TokenListCmd tok = new TokenListCmd();

		commands.put(stop.getCommandTrigger(), stop);
		commands.put(port.getCommandTrigger(), port);
		commands.put(tok.getCommandTrigger(), tok);
	}

	private void handle() {
		try {
			String[] input = cInput.nextLine().split(" ");
			String cmd = input[0];
			commands.get(cmd.toLowerCase()).handle(input);

		} catch (NullPointerException e) {
			ConsoleDisplay.notice("Wrong input.");
		}

	}

	public void requestStop() {
		cont = false;
	}

	@Override
	public void run() {
		while (cont) {
			handle();
		}
	}

}
