package Console.handlers;

import java.util.HashMap;

import Console.handlers.commands.Command;
import Console.handlers.commands.impl.PortCmd;
import Console.handlers.commands.impl.StopCmd;
import Console.utils.ConsoleDisplay;
import Console.utils.ConsoleInput;

/**
 * @author alexandre
 * CommandHandler.java
 */
public class CommandHandler implements Runnable {

	private ConsoleInput cInput;

	private HashMap<String, Command> commands;
	private boolean cont;

	public CommandHandler() {
		cInput = ConsoleInput.getCurrentInst();
		commands = new HashMap<String, Command>();
		initCommand();
		cont = true;
	}

	private void initCommand() {
		StopCmd stop = new StopCmd(this);
		PortCmd port = new PortCmd();

		commands.put(stop.getCommandTrigger(), stop);
		commands.put(port.getCommandTrigger(), port);
	}

	private void handle() {
		try {
			String[] input = cInput.nextLine().split(" ");
			String cmd = input[0];
			commands.get(cmd.toLowerCase()).handle(input);

		} catch (NullPointerException e) {
			e.printStackTrace();
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
