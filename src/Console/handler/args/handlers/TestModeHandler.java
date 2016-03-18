package Console.handler.args.handlers;

import Console.handler.args.GenericArgsHandler;

/**
 * @author alexandre
 * TestModeHandler.java
 */
public class TestModeHandler extends GenericArgsHandler {

	private static boolean testMode = false;

	public TestModeHandler(String[] args) {
		super(args, "-t");
	}

	@Override
	protected void handleArgs() {
		testMode = true;
	}

	public static boolean isTestMode() {
		return testMode;
	}
}
