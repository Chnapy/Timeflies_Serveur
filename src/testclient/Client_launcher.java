package testclient;

import Console.utils.ConsoleDisplay;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import Serializable.messages.login.LoginAnswer;
import Serializable.messages.login.LoginRequest;
import static General.utils.Utils.sha1;
import Serializable.messages.Message;
import Serializable.messages.combat.StartCombatRequest;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author alexandre
 * Client_launcher.java
 */
public class Client_launcher {

	public static void main(String[] args) {
		String serverAddress = "127.0.0.1";
		int port = 42666;

		String login;
		String pwd;

		Scanner sc = new Scanner(System.in);

		System.out.println("Launching test client for timeflies atuhentification server...");

		try {
			Socket socket = new Socket(InetAddress.getByName(serverAddress), port);
			System.out.println("Socket : " + socket);

			if (socket.isConnected()) {
				System.out.println("Connection successfull.");
			} else {
				System.out.println("Connection failed.");
				socket.close();
				return;
			}

			System.out.println("Loading streams...");
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			System.out.println("Streams loaded.");

			inRun.init(in);
			outRun.init(out);

			System.out.print("login : ");
			login = sc.nextLine();
			System.out.print("password : ");
			pwd = sha1(sc.nextLine());

			System.out.println("Connection attemp with the following informations : " + login + " " + pwd);

			inRun.start();
			outRun.start();
			outRun.add(new LoginRequest(login, pwd));

			while (inRun.th.isAlive() && outRun.th.isAlive());

//			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sc.close();
	}

	static class inRun {

		private static volatile ObjectInputStream in;
		private static Runnable run;
		public static Thread th;

		public static void init(ObjectInputStream i) {
			in = i;
			run = () -> {
				Object temp = null;
				Message mess;
				try {
					while ((temp = read()) != null) {
						mess = (Message) temp;
						temp = null;
						act(mess);
					}
				} catch (IOException | ClassNotFoundException ex) {
					try {
						in.close();
					} catch (IOException ex1) {
					}
					if (temp != null) {
						ConsoleDisplay.error("in : " + temp);
					}
					ex.printStackTrace();
				}
			};
		}
		
		private synchronized static Object read() throws IOException, ClassNotFoundException {
			return in.readObject();
		}

		private static void act(Message mess) throws IOException {

			switch (mess.getMessageType()) {
				case LOGIN:
					LoginAnswer ans = (LoginAnswer) mess;
					switch (ans.answer) {
						case SUCCESS:
							System.out.println("Authentification succesfull, your token is : " + ans.token);
							outRun.add(new StartCombatRequest());
							break;
						case FAIL:
							System.out.println("Authentification failed, try again.");
							break;
					}
				case ERROR:
					System.out.println("Error : something happen.");
					break;
				default:
					//can't happen
					break;
			}
		}

		public static void start() {
			th = new Thread(run);
			th.start();
		}

	}

	static class outRun {

		private static volatile ObjectOutputStream out;
		private static Runnable run;
		private static Thread th;
		private static final ArrayList<Message> toSend = new ArrayList<Message>();

		public static void init(ObjectOutputStream o) {
			out = o;
			run = () -> {
				while (true) {
					if (!toSend.isEmpty()) {
						try {
							write();
						} catch (IOException ex) {
							Logger.getLogger(Client_launcher.class.getName()).log(Level.SEVERE, null, ex);
						} catch (InterruptedException ex) {
							Logger.getLogger(Client_launcher.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			};
		}

		private synchronized static void write() throws IOException, InterruptedException {
//			synchronized(inRun.th) {
			out.writeObject(toSend.remove(0));
//			}
		}

		public synchronized static void add(Message mess) {
			toSend.add(mess);
		}

		public static void start() {
			th = new Thread(run);
			th.start();
		}

	}
}
