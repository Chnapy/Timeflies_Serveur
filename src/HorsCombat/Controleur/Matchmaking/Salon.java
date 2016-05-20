/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.Matchmaking;

import HorsCombat.Modele.Client.Client;
import Serializable.Combat.AskCombat;
import Serializable.Combat.InfosCombat;
import Serializable.Combat.InfosCombat.DonneeJoueur;
import java.util.ArrayList;

/**
 * Salon.java
 *
 */
public class Salon {

	public static final int MAXCLIENTS = 8;

	public final String nomMap;
	public final AskCombat.TypeCombat type;
	public final ArrayList<PlayableClient> pclients;

	public Salon(AskCombat.TypeCombat type, String nomMap) {
		this.type = type;
		this.pclients = new ArrayList();
		this.nomMap = nomMap;
	}

	public void addClient(Client client, DonneeJoueur dj) {
		PlayableClient p = new PlayableClient(client, dj);
		pclients.add(p);
		pclients.stream().forEach((pc) -> {
			if (pc != p) {
				pc.client.sendToServer(new InfosCombat.NewJoueur(dj));
			} else {
				p.client.sendToServer(new InfosCombat.PartieTrouvee(getAllDonnees(), nomMap));
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
