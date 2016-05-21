/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.Client;

import Console.utils.ConsoleDisplay;
import HorsCombat.Controleur.Matchmaking.Matchmaking;
import HorsCombat.Controleur.Matchmaking.Salon;
import HorsCombat.Modele.HCModele;
import Serializable.Combat.AskCombat;
import Serializable.Combat.InfosCombat;
import Serializable.Combat.InfosCombat.CreaPerso;
import Serializable.Combat.InfosCombat.EstPret;
import Serializable.Logs.AnswerLogs;
import Serializable.Logs.AnswerLogs.InfosCompte;
import Serializable.Logs.AskLogs;
import Serializable.Personnages.HCPersonnage;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client.java
 *
 */
public class Client {

	public final ServClient servClient;
	public final long idReseau;
	public InfosCompte infosCompte;
	private HCPersonnage[] persos;
	public Salon salon;

	public Client(Socket soc, long idReseau) throws IOException {
		this.servClient = new ServClient(soc, this);
		this.idReseau = idReseau;
		infosCompte = null;
		persos = null;
		salon = null;
	}

	public void sendToClient(Object o) {
		try {
			servClient.send(o);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void receiveFromClient(Object pack) {
		if (pack instanceof AskLogs) {
			if (!isLogged()) {
				connexion((AskLogs) pack);
			}
		} else if (pack instanceof AskCombat) {
			if (isLogged()) {
				Matchmaking.addNewClient(this, (AskCombat) pack);
			}
		} else if (pack instanceof InfosCombat.EstPret) {
			Matchmaking.newPret((EstPret) pack, this, salon);
		} else if (pack instanceof InfosCombat.GetAllClassePerso) {
			sendAllClassePerso();
		} else if (pack instanceof InfosCombat.CreaPerso) {
			creaPerso(((CreaPerso) pack).idClasse, ((CreaPerso) pack).nomDonne);
		}
	}

	public void creaPerso(int idClasse, String nomDonne) {
		if (idClasse < 1) {
			ConsoleDisplay.error("Creation perso impossible. id " + idClasse);
			return;
		}
		try {
			long idEntite = HCModele.creaPerso(infosCompte.idjoueur, idClasse, nomDonne);
			
			sendToClient(new InfosCombat.IdCreaPerso((int) idEntite));
		} catch (SQLException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendAllClassePerso() {
		try {
			InfosCombat.AllClassePerso acp = HCModele.getAllClassePerso();
			sendToClient(acp);
		} catch (SQLException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void connexion(AskLogs pack) {
		try {
			AnswerLogs al = HCModele.getClientData(pack);
			if (al.accepted) {
				infosCompte = al.infosCompte;
				persos = al.persos;
			}
			sendToClient(al);
		} catch (SQLException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isLogged() {
		return infosCompte != null && infosCompte.pseudo != null;
	}

	public void deconnexion() {
		errln("DECONNEXION");

		if (salon != null) {
			Matchmaking.removeClient(this, salon);
		}
	}

	public void errln(String text) {
		ConsoleDisplay.error(getConsoleText(text));
	}

	public void println(String text) {
		ConsoleDisplay.notice(getConsoleText(text));
	}

	private String getConsoleText(String text) {
		if (infosCompte == null) {
			return "C" + idReseau + " " + text;
		} else {
			return "C" + idReseau + "-" + infosCompte.idjoueur + "-" + infosCompte.pseudo + " " + text;
		}
	}

}
