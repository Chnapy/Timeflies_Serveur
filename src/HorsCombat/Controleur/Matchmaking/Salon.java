/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.Matchmaking;

import HorsCombat.Modele.Client.Client;
import Serializable.Combat.AskCombat;
import Serializable.Combat.InfosCombat;
import Serializable.Personnages.HCPersonnage;
import java.util.ArrayList;

/**
 * Salon.java
 *
 */
public class Salon {

	public static final int MAXCLIENTS = 8;

	public final AskCombat.TypeCombat type;
	public final ArrayList<PlayableClient> pclients;

	public Salon(AskCombat.TypeCombat type) {
		this.type = type;
		this.pclients = new ArrayList();
	}

	public void addClient(Client client, ArrayList<HCPersonnage> persos) {
		PlayableClient p = new PlayableClient(client, persos);
		pclients.add(p);
		pclients.stream().forEach((pc) -> {
			if (pc != p) {
				pc.client.sendToServer(new InfosCombat.NewJoueur(p.client.infosCompte.pseudo, p.persos.size()));
			} else {
				p.client.sendToServer(new InfosCombat.PartieTrouvee(getAllPseudos(), getAllNbrPersos()));
			}
		});
	}
	
	public void lancerCombat() {
		
	}

	private ArrayList<String> getAllPseudos() {
		ArrayList<String> allPseudos = new ArrayList();
		pclients.stream().forEach((p) -> {
			allPseudos.add(p.client.infosCompte.pseudo);
		});
		return allPseudos;
	}

	private ArrayList<Integer> getAllNbrPersos() {
		ArrayList<Integer> allNbrPersos = new ArrayList();
		pclients.stream().forEach((p) -> {
			allNbrPersos.add(p.persos.size());
		});
		return allNbrPersos;
	}

	public boolean isFull() {
		return !(pclients.size() < MAXCLIENTS);
	}

	public static class PlayableClient {

		public final Client client;
		public final ArrayList<HCPersonnage> persos;

		public PlayableClient(Client client, ArrayList<HCPersonnage> persos) {
			this.client = client;
			this.persos = persos;
		}

	}

}
