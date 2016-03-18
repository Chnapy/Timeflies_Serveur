package General.utils;

import Console.utils.ConsoleDisplay;
import java.util.ArrayList;

/**
 * @author alexandre
 * ThreadManager.java
 */
public class ThreadManager extends ArrayList<Thread> {

	private static ThreadManager currentInstance;

	public static void init() {
		ConsoleDisplay.start("Thread manager");
		try {
			currentInstance = new ThreadManager();
			ConsoleDisplay.success();
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}

	}

	public static ThreadManager getCurrentInstance() {
		return currentInstance;
	}

	public void killAllThreads() {
		for (Thread thread : this) {
			if (thread.isAlive()) {
				thread.interrupt();
			}
		}
	}
}
