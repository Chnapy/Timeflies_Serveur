/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.Matchmaking;

import HorsCombat.Modele.Reseau.Client;
import Serializable.HorsCombat.HorsCombat.DonneeJoueur;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.HorsCombat.SalonCombat;
import Serializable.HorsCombat.SalonCombat.AskCombat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Salon.java
 *
 */
public class Salon {

	public static final int MAXCLIENTS = 8;

	public final MapSerializable mapS;
	public final AskCombat.TypeCombat type;
	public final ArrayList<PlayableClient> pclients;

	public Salon(AskCombat.TypeCombat type, MapSerializable mapS) {
		this.type = type;
		this.pclients = new ArrayList();
		this.mapS = mapS;
	}

	public int removeClient(Client client) {
		PlayableClient pc;
		for (Iterator<PlayableClient> ite = pclients.iterator(); ite.hasNext();) {
			pc = ite.next();
			pc.client.sendToClient(new SalonCombat.RmJoueur(client.infosCompte.idjoueur));
			if (pc.client == client) {
				ite.remove();
			}
		}
		client.salon = null;
		return pclients.size();
	}

	public void addClient(Client client, DonneeJoueur dj) {
		client.salon = this;
		PlayableClient p = new PlayableClient(client, dj);
		pclients.add(p);
		pclients.stream().forEach((pc) -> {
			if (pc != p) {
				pc.client.sendToClient(new SalonCombat.NewJoueur(dj));
			} else {
				p.client.sendToClient(new SalonCombat.PartieTrouvee(getAllDonnees(), mapS));
			}
		});
	}

	public void lancerCombat() {

	}

	private ArrayList<DonneeJoueur> getAllDonnees() {
		ArrayList<DonneeJoueur> allDonnees = new ArrayList();
		pclients.stream().forEach((p) -> {
			allDonnees.add(p.donneesJoueur);
		});
		return allDonnees;
	}

	public boolean isFull() {
		return !(pclients.size() < MAXCLIENTS);
	}

	public boolean isAllReady() {
		for (PlayableClient pc : pclients) {
			if (!pc.pret) {
				return false;
			}
		}
		return true;
	}

	public void newPret(SalonCombat.EstPret estPret, Client client) {
		pclients.stream().forEach((pc) -> {
			if (pc.client != client) {
				pc.client.sendToClient(estPret);
			} else {
				pc.pret = estPret.pret;
			}
		});
	}

	public static class PlayableClient {

		public final Client client;
		public final DonneeJoueur donneesJoueur;
		public boolean pret;

		public PlayableClient(Client client, DonneeJoueur donneesJoueur) {
			this.client = client;
			this.donneesJoueur = donneesJoueur;
			this.pret = false;
		}

	}

}
