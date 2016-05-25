/*
 * 
 * 
 * 
 */
package Main;

import Console.Console;
import Console.utils.ConsoleDisplay;
import Database.Database;
import HorsCombat.Modele.InterfacesBD.InterfaceBDAdmin;
import HorsCombat.Controleur.HCControleur;

/**
 * Main.java
 Main
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		ConsoleDisplay.splash();

		try {
			Console.init(args);
			Database.init();
			HCControleur.init();
			Console.initCmd();
			
			InterfaceBDAdmin.init();
		} catch (Exception e) {
			ConsoleDisplay.error("Failed to initialize. Stopping the program.");
			ConsoleDisplay.debug(e);
		}
	}

}
