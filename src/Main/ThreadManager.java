package Main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author alexandre
 * ThreadManager.java
 */
public class ThreadManager {

	public static final ExecutorService EXEC
			= Executors.newFixedThreadPool(Data.MAX_EXEC_THREADS);

	public static void killAllThreads() {
		EXEC.shutdown();
	}
}
