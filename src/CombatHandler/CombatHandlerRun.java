/*
 * 
 * 
 * 
 */
package CombatHandler;

import CombatHandler.Combat.Combat;
import Console.utils.ConsoleDisplay;
import MoteurJeu.gameplay.core.Joueur;
import MoteurJeu.gameplay.map.Map;
import Serializable.messages.combat.CombatMessage;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CombatHandlerRun.java
 *
 */
public class CombatHandlerRun implements Runnable {

	private LinkedList<Combat> combats;
	private static volatile LinkedList<Joueur> waitList = new LinkedList<Joueur>();
	private volatile boolean endRequest;

	private ExecutorService exec;

	private final static int MAX_POOL_SIZE = 16;

	public CombatHandlerRun() throws Exception {
		ConsoleDisplay.start("combat handler");
		try {
			combats = new LinkedList<Combat>();
			waitList = new LinkedList<Joueur>();
			endRequest = false;

			exec = Executors.newFixedThreadPool(MAX_POOL_SIZE);
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}
		ConsoleDisplay.success();
	}

	public void newCombat(Joueur... joueurs) {
		Combat cb = new Combat(new CombatStartPack(new Map(Map.getMapSerializable(new File("test.tfmap"))), joueurs));
		combats.add(cb);
		exec.submit(cb);
	}

	@Override
	public void run() {
		while (!endRequest) {
			if (waitList.size() > 1) {
				newCombat(waitList.pollFirst(), waitList.pollFirst());
			}
		}
	}

	public void arret() {
		combats.forEach((c) -> {
			c.arret();
		});
		exec.shutdown();
	}

	public static void handle(CombatMessage combatMessage, Joueur joueur) {
		waitList.add(joueur);
		ConsoleDisplay.notice("New player : " + joueur.getPseudo());
	}

}
