/*
 * 
 * 
 * 
 */
package CombatHandler;

import CombatHandler.Combat.Combat;
import Console.utils.ConsoleDisplay;
import static General.utils.ThreadManager.EXEC;
import java.util.LinkedList;

/**
 * CombatHandlerRun.java
 * 
 */
public class CombatHandlerRun {

	private LinkedList<Combat> combats;
	private volatile boolean endRequest;

	public CombatHandlerRun() throws Exception {
		ConsoleDisplay.start("combat handler");
		try {
			combats = new LinkedList<Combat>();
			endRequest = false;
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}
		ConsoleDisplay.success();
	}
	
	public void newCombat() {
		Combat cb = new Combat(CombatStartPack.getTestPack());
		combats.add(cb);
		EXEC.submit(cb);
	}
	
	public void arret() {
		combats.forEach((c) -> {
			c.arret();
		});
	}

}
