package Console.handler.args;

/**
 * @author alexandre
 * GenericArgsHandler.java
 */
public abstract class GenericArgsHandler {

	private final String param;
	protected String[] args;

	public GenericArgsHandler(String[] args, String param) {
		this.param = param;
		this.args = args;

		if (isCalled()) {
			handleArgs();
		}
	}

	private boolean isCalled() {
		for (String arg : args) {
			if (arg.equals(param)) {
				return true;
			}
		}

		return false;

	}

	protected abstract void handleArgs();
}
