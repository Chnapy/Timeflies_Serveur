package Console.utils;

/**
 * @author alexandre
 * ConsoleDisplay.java
 * If you need to display something, use this.
 */
public class ConsoleDisplay {

	private final static String PROGRAM_NAME = "Timeflies authentification server";
	private final static String VERSION = "0.1";
	private final static String AUTHOR = "A.Gaspard Cilia";

	public static boolean debug = false;

	/**
	 * Displays program's "splash screen".
	 */
	public static void splash() {
		System.out.println();
		System.out.println(PROGRAM_NAME + " v" + VERSION + ".");
		System.out.println("By " + AUTHOR + " 2015, all rights reserved.");
		System.out.println();
	}

	/**
	 * Displays an error notice.
	 *
	 * @param notice
	 *               Text to display.
	 */
	public static void error(String notice) {
		System.err.println(notice);
	}

	/**
	 * Displays something.
	 *
	 * @param notice
	 *               Text to display.
	 */
	public static void notice(String notice) {
		System.out.println(notice);
	}

	/**
	 * Advise user that something is starting. You shouldn't display anything
	 * after that unless you used
	 * success() or fail().
	 *
	 * @param elementName
	 */
	public static void start(String elementName) {
		System.out.print("Starting " + elementName + "...\t\t\t\t");
	}

	public static void success() {
		System.out.println("[OK]");
	}

	public static void fail() {
		System.out.println("[FAIL]");
	}

	/**
	 * print a stacktrace.
	 *
	 * @param e
	 *          Exception that you need to display the stacktrace.
	 */
	public static void printStack(Exception e) {
		if (debug) {
			e.printStackTrace();
		}
	}

	/**
	 * Display s if the debug is true.
	 *
	 * @param s
	 */
	public static void debug(String s) {
		if (debug) {
			System.out.println("Debug : " + s);
		}
	}

	/**
	 * Display e if the debug is true.
	 *
	 * @param e
	 */
	public static void debug(Throwable e) {
		if (debug) {
			e.printStackTrace();
		}
	}

}
