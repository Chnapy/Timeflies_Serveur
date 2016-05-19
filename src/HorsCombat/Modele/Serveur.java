/*
 * 
 * 
 * 
 */
package HorsCombat.Modele;

import static General.utils.ThreadManager.EXEC;
import HorsCombat.Modele.Client.Client;
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

	static final int TIMEOUT = 0;
	static final int PORT = 9999;
	private boolean isAlive;

	private ServerSocket s;
	public final ArrayList<Client> allClients;
	private long id = 0;

	public Serveur() {
		allClients = new ArrayList();
		try {
			s = new ServerSocket(PORT);
			isAlive = true;
			System.out.println("Socket serveur: " + s);

			ServNewClients snc = new ServNewClients(s);
			EXEC.submit(snc);

			System.out.println("Serveur lancé");
		} catch (IOException ex) {
			Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void stopServeur() {
		System.out.println("Arrêt du serveur (3s) ...");
		isAlive = false;
		try {
			Thread.sleep(TIMEOUT);
			allClients.forEach((c) -> {
				try {
					c.servClient.close();
				} catch (IOException ex) {
					Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
			s.close();
		} catch (InterruptedException | IOException ex) {
		}
	}

	public class ServNewClients implements Runnable {

		private final ServerSocket s;

		public ServNewClients(ServerSocket s) {
			this.s = s;
		}

		@Override
		public void run() {
			System.out.println("Attente de nouveaux clients...");
			while (isAlive) {
				try {
					Client sc = new Client(s.accept(), id);
					id++;
					allClients.add(sc);
					EXEC.submit(sc.servClient);
				} catch (IOException ex) {
					System.err.println("bug snc" + isAlive);
				}
			}
			System.out.println("Attente des clients stoppée");
		}

	}
}
