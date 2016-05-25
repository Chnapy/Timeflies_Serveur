/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.Reseau;

import Console.utils.ConsoleDisplay;
import static Main.ThreadManager.EXEC;
import Main.Data;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serveur.java
 *
 */
public class Serveur {

	public static final ArrayList<Client> ALL_CLIENTS = new ArrayList();
	
	private ServerSocket s;
	private boolean isAlive;
	private long idReseauClients = 0;

	public Serveur() {
		try {
			s = new ServerSocket(Data.SERV_PORT);
			isAlive = true;
			ConsoleDisplay.notice("Socket serveur: " + s);

			ServNewClients snc = new ServNewClients(s);
			EXEC.submit(snc);

			ConsoleDisplay.notice("Serveur lancé");
		} catch (IOException ex) {
			Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void stopServeur() {
		ConsoleDisplay.notice("Arrêt du serveur ...");
		isAlive = false;
		try {
			ALL_CLIENTS.forEach((c) -> {
				try {
					c.servClient.close();
				} catch (IOException ex) {
					Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
			s.close();
		} catch (IOException ex) {
		}
	}

	public static void removeClient(Client client) {
		ALL_CLIENTS.remove(client);
	}

	class ServNewClients implements Runnable {

		private final ServerSocket s;

		public ServNewClients(ServerSocket s) {
			this.s = s;
		}

		@Override
		public void run() {
			ConsoleDisplay.notice("Attente de nouveaux clients...");
			while (isAlive) {
				try {
					Client sc = new Client(s.accept(), idReseauClients);
					idReseauClients++;
					ALL_CLIENTS.add(sc);
					EXEC.submit(sc.servClient);
				} catch (IOException ex) {
					System.err.println("bug snc" + isAlive);
				}
			}
			ConsoleDisplay.notice("Attente des clients stoppée");
		}

	}
}
