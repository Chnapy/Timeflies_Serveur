/*
 * 
 * 
 * 
 */
package HorsCombat.Controleur.ClientControleur;

import HorsCombat.Modele.HCModele;
import HorsCombat.Modele.Reseau.Client;
import static HorsCombat.Modele.Reseau.Client.ClientState.ACCEPTED;
import static HorsCombat.Modele.Reseau.Client.ClientState.LOGGED;
import Main.ClientControleur;
import Main.Data;
import Serializable.Log.Log;
import Serializable.Log.Log.AnswerLogs;
import Serializable.Log.Log.AskLogs;
import Serializable.Log.Log.CheckVersion;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LogControleur.java
 *
 */
public class LogControleur extends ClientControleur<Log> {

	public LogControleur(Client client) {
		super(client);
	}

	private void checkVersion(Log.CheckVersion cv) {
		boolean accepted = Arrays.equals(cv.version, Data.VERSION_CLIENT);
		Log.ResultVersion result = new Log.ResultVersion(accepted);
		client.sendToClient(result);

		if (accepted) {
			client.state = ACCEPTED;
		} else {

		}
	}

	private void connexion(AskLogs pack) {
		try {
			AnswerLogs al = HCModele.getClientData(pack);
			if (al.accepted) {
				client.infosCompte = al.infosCompte;
				client.persos = al.persos;
				client.state = LOGGED;
			}
			client.sendToClient(al);
		} catch (SQLException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void receiveFromClient(Log pack) {
		if (pack instanceof CheckVersion) {
			checkVersion((CheckVersion) pack);
		} else if (pack instanceof AskLogs) {
			connexion((AskLogs) pack);
		}
	}
}
