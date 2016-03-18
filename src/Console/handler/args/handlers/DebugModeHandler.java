package Console.handler.args.handlers;

import Console.handler.args.GenericArgsHandler;
import Console.utils.ConsoleDisplay;

/**
 * @author alexandre
 * DebugModeHandler.java
 */
public class DebugModeHandler extends GenericArgsHandler {

	public DebugModeHandler(String[] args) {
		super(args, "-d");
	}

	@Override
	protected void handleArgs() {
		ConsoleDisplay.debug = true;
	}

}
