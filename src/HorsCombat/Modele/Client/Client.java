/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.Client;

import HorsCombat.Controleur.Matchmaking.Matchmaking;
import HorsCombat.Modele.HCModele;
import Serializable.Combat.AskCombat;
import Serializable.Logs.AnswerLogs;
import Serializable.Logs.AnswerLogs.InfosCompte;
import Serializable.Logs.AskLogs;
import Serializable.Personnages.HCPersonnage;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client.java
 *
 */
public class Client {

	public final ServClient servClient;
	public final long id;
	public InfosCompte infosCompte;
	private HCPersonnage[] persos;

	public Client(Socket soc, long id) throws IOException {
		this.servClient = new ServClient(soc, this);
		this.id = id;
		infosCompte = null;
		persos = null;
	}

	public void receiveFromClient(Object pack) {
		if (pack instanceof AskLogs) {
			connexion((AskLogs) pack);
		} else if (pack instanceof AskCombat) {
			if (isLogged()) {
				Matchmaking.addNewClient(this, (AskCombat) pack);
			}
		}
	}
	
	public void sendToServer(Object o) {
		try {
			servClient.send(o);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void connexion(AskLogs pack) {
		if (isLogged()) {
			return;
		}
		AnswerLogs al = HCModele.getClientData(pack);
		if (al.accepted) {
			infosCompte = al.infosCompte;
			persos = al.persos;
		}
		try {
			servClient.send(al);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isLogged() {
		return infosCompte != null && infosCompte.pseudo != null;
	}

}
