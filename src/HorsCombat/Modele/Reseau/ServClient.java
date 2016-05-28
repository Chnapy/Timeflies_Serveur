/*
 * 
 * 
 * 
 */
package HorsCombat.Modele.Reseau;

import static HorsCombat.Modele.HCModele.SERVEUR;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
		client.println("Nouveau client");

		while (SERVEUR.isAlive() && clientIsAlive && !socket.isInputShutdown()) {
			try {
				readObject();
			} catch (IOException | ClassNotFoundException ex) {
				clientIsAlive = false;
				client.deconnexion();
				break;
			}
		}

		client.println("Arret client");
		try {
			close();
		} catch (IOException ex) {
		}
	}

	public void send(Object objToSend) throws IOException {
		if (!clientIsAlive) {
			return;
		}
		client.println("ENVOI : " + objToSend);
		out.writeObject(objToSend);
		out.flush();
		client.println("ENVOI REUSSI");
	}

	public void readObject() throws IOException, ClassNotFoundException {
		if (!clientIsAlive) {
			return;
		}
		Object o = in.readObject();
		client.println("RECU : " + o);
		client.receiveFromClient(o);
	}

	public void close() throws IOException {
		clientIsAlive = false;
		in.close();
		out.close();
		socket.close();
	}

	public boolean isAlive() {
		return clientIsAlive;
	}
}
