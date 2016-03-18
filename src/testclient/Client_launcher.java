package testclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import General.messages.login.LoginAnswer;
import General.messages.login.LoginRequest;
import static General.utils.Utils.sha1;

/**
 * @author alexandre
 Client_launcher.java
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

			System.out.print("login : ");
			login = sc.nextLine();
			System.out.print("password : ");
			pwd = sha1(sc.nextLine());

			System.out.println("Connection attemp with the following informations : " + login + " " + pwd);

			out.writeObject(new LoginRequest(login, pwd));

			try {
				LoginAnswer ans = (LoginAnswer) in.readObject();

				switch (ans.answer) {
					case SUCCESS:
						System.out.println("Authentification succesfull, your token is : " + ans.token);
						break;
					case FAIL:
						System.out.println("Authentification failed, try again.");
						break;
					case ERROR:
						System.out.println("Error : something happen.");
						break;
					default:
						//can't happen
						break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sc.close();
	}
}
