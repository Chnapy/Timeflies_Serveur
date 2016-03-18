/*
 * 
 * 
 * 
 */
package CombatHandler;

import CombatHandler.Combat.Combat;
import Console.utils.ConsoleDisplay;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CombatHandlerRun.java
 * 
 */
public class CombatHandlerRun implements Runnable {

	private LinkedList<Combat> combats;
	private volatile boolean endRequest;

	private ExecutorService exec;

	private final static int MAX_POOL_SIZE = 16;

	public CombatHandlerRun() throws Exception {
		ConsoleDisplay.start("combat handler");
		try {
			combats = new LinkedList<Combat>();
			endRequest = false;

			exec = Executors.newFixedThreadPool(MAX_POOL_SIZE);
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}
		ConsoleDisplay.success();
	}
	
	public void newCombat() {
		Combat cb = new Combat(CombatStartPack.getTestPack());
		combats.add(cb);
		exec.submit(cb);
	}

	@Override
	public void run() {
		newCombat();
	}
	
	public void arret() {
		combats.forEach((c) -> {
			c.arret();
		});
		exec.shutdown();
	}

}
