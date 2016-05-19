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
 * CombatHandler.java
 *
 */
public class CombatHandler {

	private static LinkedList<Combat> combats;
	private static volatile boolean endRequest;

	public static void init() throws Exception {
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

	public static void arret() {
		combats.forEach((c) -> {
			c.arret();
		});
	}

}
