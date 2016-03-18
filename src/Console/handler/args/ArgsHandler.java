package Console.handler.args;

import Console.handler.args.handlers.DebugModeHandler;
import Console.handler.args.handlers.TestModeHandler;
import Console.utils.ConsoleDisplay;

/**
 * @author alexandre
 * ArgsHandler.java
 */
public class ArgsHandler {

	public static void init(String[] args) {
		ConsoleDisplay.start("arguments handler");

		new DebugModeHandler(args);
		new TestModeHandler(args);

		ConsoleDisplay.success();
		if(ConsoleDisplay.debug) {
			ConsoleDisplay.notice("- Debug is TRUE");
		}
		if(TestModeHandler.isTestMode()) {
			ConsoleDisplay.notice("- TestMode is TRUE");
		}
	}
}
