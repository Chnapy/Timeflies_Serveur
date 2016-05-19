/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.Matchmaking;

import CombatHandler.Combat.Combat;
import CombatHandler.CombatStartPack;
import static General.utils.ThreadManager.EXEC;
import HorsCombat.Modele.Client.Client;
import MoteurJeu.gameplay.caracteristique.CaracteristiquePhysique;
import MoteurJeu.gameplay.core.Joueur;
import MoteurJeu.gameplay.entite.Personnage;
import MoteurJeu.gameplay.sort.SortActif;
import MoteurJeu.general.Orientation;
import Serializable.Combat.AskCombat;
import Serializable.Combat.AskCombat.TypeCombat;
import Serializable.Personnages.HCPersonnage;
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
	private static final MoteurMM moteur = new MoteurMM();
	
	public static void init() {
		EXEC.submit(moteur);
	}

	public static void addNewClient(Client client, AskCombat pack) {
		switch (pack.type) {
			case SOLO:
				clientToSolo(client, pack.persos);
				break;
		}
	}

	public static void clientToSolo(Client client, ArrayList<HCPersonnage> persos) {
		Salon s = getBestSalon(TypeCombat.SOLO);
		if (s == null) {
			s = createSalon(TypeCombat.SOLO);
		}
		s.addClient(client, persos);
	}

	private static Salon createSalon(TypeCombat type) {
		Salon sa = new Salon(type);
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
	
	private static void lancerCombat(Salon s) {
//		Joueur[] joueurs = new Joueur[s.pclients.size()];
//		Personnage[] persos = new Personnage[s.pclients.get(0).persos.size()];
//		for(int i = 0; i < persos.length; i++) {
//			persos[i] = getPersonnage(s.pclients.get(0).persos.get(i));
//		}
//		
//		Combat c = new Combat(new CombatStartPack(CombatStartPack.getTestMap(), null));
	}
	
//	private static Personnage getPersonnage(HCPersonnage hcp) {
//		
//		Personnage p;
//		CaracteristiquePhysique cp = new CaracteristiquePhysique(hcp.vitalite, hcp.tempsA, hcp.tempsS, hcp.fatigue, hcp.vitesse);
//		SortActif[] sas = new SortActif[hcp.sortsActifs.length];
//		hcp.sortsActifs[0].
//		p = new Personnage(hcp.nom, hcp.classe, 0, 0, Orientation.EST, cp)
//		
//	}

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
			for(Salon s : salons) {
				if(s.isFull() || s.pclients.size() > 1) {
					lancerCombat(s);
				}
			}
		}
	}

}
