/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.ClientControleur;

import Console.utils.ConsoleDisplay;
import HorsCombat.Modele.HCModele;
import HorsCombat.Modele.Reseau.Client;
import Main.ClientControleur;
import Serializable.HorsCombat.GestionPersos;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GestionPersosControleur.java
 *
 */
public class GestionPersosControleur extends ClientControleur<GestionPersos> {

	public GestionPersosControleur(Client client) {
		super(client);
	}

	private void sendAllClassePerso() {
		try {
			GestionPersos.AllClassePerso acp = HCModele.getAllClassePerso();
			client.sendToClient(acp);
		} catch (SQLException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void creaPerso(int idClasse, String nomDonne) {
		if (idClasse < 1) {
			ConsoleDisplay.error("Creation perso impossible. id " + idClasse);
			return;
		}
		try {
			long idEntite = HCModele.creaPerso(client.infosCompte.idjoueur, idClasse, nomDonne);

			client.sendToClient(new GestionPersos.IdCreaPerso((int) idEntite));
		} catch (SQLException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void receiveFromClient(GestionPersos pack) {
		if (pack instanceof GestionPersos.GetAllClassePerso) {
			sendAllClassePerso();
		} else if (pack instanceof GestionPersos.CreaPerso) {
			creaPerso(((GestionPersos.CreaPerso) pack).idClasse, ((GestionPersos.CreaPerso) pack).nomDonne);
		}
	}

}
