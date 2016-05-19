package General.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author alexandre
 * ThreadManager.java
 */
public class ThreadManager {

	private static final int MAX_POOL_SIZE = 64;

	public static final ExecutorService EXEC = Executors.newFixedThreadPool(MAX_POOL_SIZE);

	public static void killAllThreads() {
		EXEC.shutdown();
	}
}
