/*
 * 
 * 
 * 
 */
package CombatHandler;

import General.utils.ThreadManager;

/**
 * CombatHandler.java
 *
 */
public class CombatHandler {
	
	private static CombatHandlerRun combatHandlerRun;

	public static void init() throws Exception {
		combatHandlerRun = new CombatHandlerRun();

		Thread combatHandlerThread = new Thread(combatHandlerRun);
		ThreadManager.getCurrentInstance().add(combatHandlerThread);
		combatHandlerThread.start();
	}

	public static CombatHandlerRun getCombatHandlerRun() {
		return combatHandlerRun;
	}

}
