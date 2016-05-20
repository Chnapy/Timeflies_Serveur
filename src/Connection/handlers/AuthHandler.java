package Connection.handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import Connection.checkers.LoginChecker;
import Connection.exceptions.AuthentificationException;
import General.messages.Message;
import General.messages.error.ErrorCodes;
import General.messages.error.ErrorMessage;
import General.messages.login.LoginAnswer;
import General.messages.login.LoginMessage;
import General.messages.login.LoginRequest;
import General.messages.refresh.RefreshMessage;
import General.messages.refresh.RefreshSessionAnswer;
import General.messages.refresh.RefreshSessionAnswer.Answer;
import General.messages.refresh.RefreshSessionRequest;
import General.messages.server_login.ServerLoginAnswer;
import General.messages.server_login.ServerLoginAnswer.AnswerType;
import General.messages.server_login.ServerLoginMessage;
import General.messages.server_login.ServerLoginRequest;
import Connection.storage.TokenBank;
import Console.utils.ConsoleDisplay;

public class AuthHandler implements Runnable {

	private final static int RETRY = 5;

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private TokenBank tokenBank;

	private boolean isAServer;
	private boolean endRequest;

	public AuthHandler(Socket socket) throws IOException {
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		tokenBank = null;
		isAServer = false;
		endRequest = false;
	}

	@Override
	public void run() {
		Message inMessage;
		try {
			inMessage = (Message) in.readObject();
			switch (inMessage.getMessageType()) {
				case LOGIN: //Login request
					handleLogin((LoginMessage) inMessage);
					break;
				case SERVER_LOGIN: //Server login request
					handleServerLogin((ServerLoginMessage) inMessage);
					break;
				default: //Can be a refresh request, but if the request is receive here, the socket is not auth.
					ConsoleDisplay.error("Unkhown or missused message.");
					break;
			}

		} catch (ClassNotFoundException e) {
			ConsoleDisplay.error("Error while reading input stream.");
			ConsoleDisplay.printStack(e);
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}

	}

	/**
	 * Handle a login request.
	 *
	 * @param m
	 *          Login request.
	 */
	private void handleLogin(LoginMessage m) {
//		LoginRequest request = (LoginRequest) m;
//		UUID token;
//
//		//login/pwd check
//		try {
//			//SUCCESS
//			if (LoginChecker.checkLogin(request)) {
//				token = TokenBank.getCurrentInstance().addToken(request.login, socket.getInetAddress());
//				out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.SUCCESS, token));
//				ConsoleDisplay.notice("A connection attempt is successful.");
//			}
//		} catch (AuthentificationException e) {
//			//FAIL
//			ConsoleDisplay.notice("A connection attempt failed : bad login/password.");
//			if (!sendMessage(new LoginAnswer(LoginAnswer.AnswerType.FAIL, null))) {
//				close();
//				return;
//			}
//		} catch (IOException e) {
//			//ERROR
//			if (!sendMessage(new LoginAnswer(LoginAnswer.AnswerType.ERROR, null))) {
//				close();
//				return;
//			}
//		} finally {
//			close();
//		}

	}

	/**
	 * Handle a server login request. When the server is auth, will launch
	 * handleRefresh() to auth and/or refresh tokens.
	 *
	 * @param m
	 */
	private void handleServerLogin(ServerLoginMessage m) {
//		ServerLoginRequest request = (ServerLoginRequest) m;
//		tokenBank = TokenBank.getCurrentInstance();
//
//		if (LoginChecker.checkServerLogin(request)) {
//			sendMessage(new ServerLoginAnswer(AnswerType.SUCCES));
//			handleRefresh();
//		} else {
//			sendMessage(new ServerLoginAnswer(AnswerType.BAD_INFOS));
//			close();
//		}
//		//TODO handle server login request w/ modification of database.
	}

	/**
	 * Check tokens validity and refresh them.
	 * Handles refresh, will send an error message (code 10) if the client is
	 * not registered as a server.
	 */
	private void handleRefresh() {
		//Check if the client can do that.
		if (!isAServer) {
			sendMessage(new ErrorMessage(ErrorCodes.NOT_A_SERVER, ErrorCodes.NOT_A_SERVER_txt));
			close();

		} else {
			boolean cont = true;
			RefreshMessage message;
			RefreshSessionRequest request;
			RefreshSessionAnswer answer;
			while (cont && !endRequest) {
				message = (RefreshMessage) readMessage();

				//Will stop communications.
				if (message.isEndOfCom()) {
					cont = false;
				} else {

					request = (RefreshSessionRequest) message;
					//Check token validity and refresh it.
					if (tokenBank.refreshToken(request.token)) {
						//The refresh is successful.
						answer = new RefreshSessionAnswer(Answer.SUCCESS);
					} else {
						//Failure.
						answer = new RefreshSessionAnswer(Answer.TIME_OUT);
					}

					sendMessage(answer);
				}
			}

			close();
		}
	}

	/**
	 * Send a message through the socket.
	 *
	 * @param m
	 *          Message to send.
	 * @return
	 *         True = the message has been send. False = Failed to deliver the message.
	 */
	private boolean sendMessage(Message m) {
		for (int i = 0; i < RETRY; i++) {
			try {
				out.writeObject(m);
				return true;
			} catch (IOException e) {

			}
		}

		return false;
	}

	/**
	 * Read the next message in socket.
	 *
	 * @return
	 *         Read message.
	 */
	private Message readMessage() {
		try {
			return (Message) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			return null;
		}
	}

	/**
	 * Close the current socket. If you use this, you should stop the current
	 * thread.
	 */
	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}

	public void requestEnd() {
		endRequest = true;
	}
}
