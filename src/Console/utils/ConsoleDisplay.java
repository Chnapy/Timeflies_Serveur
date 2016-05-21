package Console.utils;

import java.sql.Timestamp;
import static General.utils.Utils.TIMEFORMAT;

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
		println("\n" + PROGRAM_NAME + " v" + VERSION + "."
				+ "\nBy " + AUTHOR + " 2015, all rights reserved."
				+ "\n");
	}

	/**
	 * Displays an error notice.
	 *
	 * @param notice
	 *               Text to display.
	 */
	public static void error(String notice) {
		errln(notice);
	}

	/**
	 * Displays something.
	 *
	 * @param notice
	 *               Text to display.
	 */
	public static void notice(String notice) {
		println(notice);
	}

	/**
	 * Advise user that something is starting. You shouldn't display anything
	 * after that unless you used
	 * success() or fail().
	 *
	 * @param elementName
	 */
	public static void start(String elementName) {
		print("Starting " + elementName + "...\t\t\t\t");
	}

	public static void success() {
		System.out.println("[OK]");
	}

	public static void fail() {
		System.err.println("[FAIL]");
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
			println("Debug : " + s);
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

	private static void println(String text) {
		if (text.isEmpty()) {
			System.out.println();
		} else {
			print(text + "\n");
		}
	}

	private static void print(String text) {
		System.out.print(getConsoleText(text));
	}

	private static void errln(String text) {
		System.err.println(getConsoleText(text));
	}

	private static String getConsoleText(String text) {
		String date = TIMEFORMAT.format(new Timestamp(System.currentTimeMillis()));
		return "[" + date + "] " + text;
	}

}
