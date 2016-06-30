/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.ClientControleur;

import Console.utils.ConsoleDisplay;
import HorsCombat.Controleur.Matchmaking.Matchmaking;
import static HorsCombat.Modele.InterfacesBD.InterfaceBDGestionPersos.getPersoNivClasse;
import HorsCombat.Modele.Reseau.Client;
import Main.ClientControleur;
import Serializable.HorsCombat.HorsCombat;
import Serializable.HorsCombat.HorsCombat.DonneeJoueur;
import Serializable.HorsCombat.SalonCombat;
import Serializable.HorsCombat.SalonCombat.AskCombat;
import Serializable.HorsCombat.SalonCombat.EstPret;
import Serializable.HorsCombat.SalonCombat.RmJoueur;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SalonControleur.java
 *
 */
public class SalonControleur extends ClientControleur<SalonCombat> {

	public SalonControleur(Client client) {
		super(client);
	}

	private void newClientToSalon(AskCombat pack) {
		ArrayList<HorsCombat.DonneePerso> ldp = new ArrayList();
		pack.idPersos.forEach((id) -> {
			try {
				ResultSet rs = getPersoNivClasse(id);
				if (!rs.next()) {
					ConsoleDisplay.error("Perso introuvable " + id);
				}
				ldp.add(new HorsCombat.DonneePerso(id, rs.getLong("idclasse"), getNiveau(rs.getInt("victoires"),
						rs.getInt("defaites")), rs.getString("nomdonnee")));
			} catch (SQLException ex) {
				Logger.getLogger(Matchmaking.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		DonneeJoueur dj = new DonneeJoueur(client.infosCompte.idjoueur, client.infosCompte.niveauS, client.infosCompte.pseudo, false, ldp);

		Matchmaking.clientToSalon(pack.type, client, dj);
	}

	private void quitSalon(RmJoueur pack) {
		if (pack.idJoueur != client.infosCompte.idjoueur) {
			return;
		}
		
		client.salon.removeClient(client);
	}

	private static int getNiveau(int victoires, int defaites) {
		if (defaites == 0) {
			return victoires;
		}
		return (int) (victoires / defaites);
	}

	@Override
	public void receiveFromClient(SalonCombat pack) {
		if (pack instanceof AskCombat) {
			newClientToSalon((AskCombat) pack);
		} else if (pack instanceof EstPret) {
			Matchmaking.newPret((EstPret) pack, client);
		} else if (pack instanceof RmJoueur) {
			quitSalon((RmJoueur) pack);
		}
	}

}
