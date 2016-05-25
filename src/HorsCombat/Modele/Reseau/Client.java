/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.Reseau;

import Console.utils.ConsoleDisplay;
import HorsCombat.Controleur.ClientControleur.GestionPersosControleur;
import HorsCombat.Controleur.ClientControleur.LogControleur;
import HorsCombat.Controleur.Matchmaking.Matchmaking;
import HorsCombat.Controleur.Matchmaking.Salon;
import HorsCombat.Controleur.ClientControleur.SalonControleur;
import static HorsCombat.Modele.Reseau.Client.ClientState.ACCEPTED;
import static HorsCombat.Modele.Reseau.Client.ClientState.FIRST;
import static HorsCombat.Modele.Reseau.Client.ClientState.LOGGED;
import Serializable.HorsCombat.GestionPersos;
import Serializable.HorsCombat.HCPersonnage;
import Serializable.HorsCombat.SalonCombat;
import Serializable.Log.Log;
import Serializable.Log.Log.CheckVersion;
import Serializable.Log.Log.InfosCompte;
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
	public final long idReseau;

	public InfosCompte infosCompte;
	public HCPersonnage[] persos;
	public Salon salon;

	public ClientState state;

	private final LogControleur logC;
	private final GestionPersosControleur gestionPersosC;
	private final SalonControleur salonC;

	public Client(Socket soc, long idReseau) throws IOException {
		this.servClient = new ServClient(soc, this);
		this.idReseau = idReseau;
		infosCompte = null;
		persos = null;
		salon = null;
		state = FIRST;

		logC = new LogControleur(this);
		gestionPersosC = new GestionPersosControleur(this);
		salonC = new SalonControleur(this);
	}

	public void sendToClient(Object o) {
		try {
			servClient.send(o);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void receiveFromClient(Object pack) {
		if (pack instanceof Log) {
			if (state.equals(ACCEPTED) || pack instanceof CheckVersion) {
				logC.receiveFromClient((Log) pack);
			}
		} else if (pack instanceof SalonCombat) {
			if (state.equals(LOGGED)) {
				salonC.receiveFromClient((SalonCombat) pack);
			}
		} else if (pack instanceof GestionPersos) {
			if (state.equals(LOGGED)) {
				gestionPersosC.receiveFromClient((GestionPersos) pack);
			}
		}
	}

	public void deconnexion() {
		errln("DECONNEXION");

		state = FIRST;
		if (salon != null) {
			Matchmaking.removeClient(this, salon);
		}
		Serveur.removeClient(this);
		try {
			servClient.close();
		} catch (IOException ex) {
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

	public static enum ClientState {
		FIRST, //Vient de se connecter au serveur
		ACCEPTED, //La version du client est acceptée
		LOGGED	//Le client est loggé
	}

}
