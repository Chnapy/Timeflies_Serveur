/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.Client;

import static HorsCombat.Modele.HCModele.SERVEUR;
import HorsCombat.Modele.Serveur;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServClient.java
 *
 */
public class ServClient implements Runnable {

	private final Socket socket;
	private final ObjectOutputStream out;
	private final ObjectInputStream in;
	private boolean clientIsAlive;
	private Client client;

	public ServClient(Socket soc, Client client) throws IOException {
		this.socket = soc;
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		clientIsAlive = true;
		this.client = client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		System.out.println("Nouveau client : n°" + client.id);
		int attente = 0;

		while (SERVEUR.isAlive() && clientIsAlive && !socket.isInputShutdown()) {
			try {
				readObject();
				attente = 0;
			} catch (IOException | ClassNotFoundException ex) {
				if (attente >= 10) {
					break;
				} else {
					attente++;
					System.out.println("C" + client.id + " - Attente du client " + attente + "/10");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex1) {
						Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex1);
					}
				}
			}
		}

		System.out.println("C" + client.id + " - Arret client");
		try {
			close();
		} catch (IOException ex) {
		}
	}

	public void send(Object objToSend) throws IOException {
		System.out.println("C" + client.id + " ENVOI : " + objToSend);
		out.writeObject(objToSend);
		out.flush();
		System.out.println("C" + client.id + " ENVOI REUSSI");
	}

	public Object readObject() throws IOException, ClassNotFoundException {
		Object o = in.readObject();
		System.out.println("C" + client.id + " - RECU : " + o);
		client.receiveFromClient(o);
		return o;
	}

	public void close() throws IOException {
		clientIsAlive = false;
		in.close();
		out.close();
		socket.close();
	}
}
