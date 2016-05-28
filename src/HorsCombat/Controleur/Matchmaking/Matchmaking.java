/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.Matchmaking;

import Combat.Combat;
import HorsCombat.Modele.MapsData;
import static Main.ThreadManager.EXEC;
import HorsCombat.Modele.Reseau.Client;
import Serializable.HorsCombat.HorsCombat.DonneeJoueur;
import Serializable.HorsCombat.HorsCombat.TypeCombat;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.HorsCombat.SalonCombat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Matchmaking.java
 *
 */
public class Matchmaking {

	private static final ArrayList<Salon> salons = new ArrayList();
	private static final ArrayList<Combat> combats = new ArrayList();
//	private static final MoteurMM moteur = new MoteurMM();

	public static void init() {
//		EXEC.submit(moteur);
	}

	public static void removeClient(Client client) {
		if (client.salon != null) {
			int size = client.salon.removeClient(client);
			if (size == 0) {
				removeSalon(client.salon);
			}
		}
		if (client.combat != null) {
			client.combat.removeClient(client);
		}
	}

	public static void removeSalon(Salon salon) {
		salons.remove(salon);
	}

	public static void clientToSalon(TypeCombat type, Client client, DonneeJoueur dj) {
		Salon s = getBestSalon(type);
		if (s == null) {
			s = createSalon(type, MapsData.getRandomMap());
		}
		s.addClient(client, dj);
	}

	private static Salon createSalon(TypeCombat type, MapSerializable mapS) {
		Salon sa = new Salon(type, mapS);
		salons.add(sa);
		return sa;
	}

	private static Salon getBestSalon(TypeCombat type) {
		Salon choice = null;
		for (Salon s : salons) {
			if (s.type.equals(type)
					&& (choice == null || (choice.pclients.size() < s.pclients.size() && !s.isFull()))) {
				choice = s;
			}
		}
		return choice;
	}

	private static Combat creerCombat(Salon s) {
		return s.creerCombat();
	}

	public static void newPret(SalonCombat.EstPret estPret, Client client) {
		client.salon.newPret(estPret, client);
		if (client.salon.isAllReady()) {
			Combat c = creerCombat(client.salon);
			combats.add(c);
			EXEC.submit(c);
		}
	}

	private static class MoteurMM implements Runnable {

		private static final int INTERVAL = 5000;

		public MoteurMM() {

		}

		@Override
		public void run() {
			try {
				act();
				Thread.sleep(INTERVAL);
			} catch (InterruptedException ex) {
				Logger.getLogger(Matchmaking.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		private void act() {
			for (Salon s : salons) {
				if (s.isFull() || s.pclients.size() > 1) {
					creerCombat(s);
				}
			}
		}
	}

}
