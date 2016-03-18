package Connection.handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Database.settings.SettingsManager;
import Console.utils.ConsoleDisplay;

public class ConnectionsHandler implements Runnable {

	private ServerSocket serverSocket;
	private LinkedList<AuthHandler> handlers;
	private volatile boolean endRequest;

	private ExecutorService exec;

	private final static int MAX_POOL_SIZE = 16;

	public ConnectionsHandler() throws Exception {
		ConsoleDisplay.start("connection handler");
		try {
			int port = SettingsManager.getNetworkSettings().getPort();
			serverSocket = new ServerSocket(port);

			handlers = new LinkedList<AuthHandler>();
			endRequest = false;

			exec = Executors.newFixedThreadPool(MAX_POOL_SIZE);
		} catch (Exception e) {
			ConsoleDisplay.fail();
			throw e;
		}
		ConsoleDisplay.success();
	}

	@Override
	public void run() {
		AuthHandler crt;
		while (!endRequest) {
			try {
				crt = new AuthHandler(serverSocket.accept());
				handlers.add(crt);
				exec.submit(crt);
			} catch (SocketException e) {
				//TODO only handle bad socket closing.
			} catch (Exception e) {
				ConsoleDisplay.printStack(e);
			}
		}
	}

	public void stopListen() {
		if (handlers.size() != 0) {
			stopHandlers();
		}
		exec.shutdown();
		endRequest = true;
		try {
			serverSocket.close();
		} catch (IOException e) {
		}
	}

	private void stopHandlers() {
		AuthHandler crt;

		while ((crt = handlers.pop()) != null) {
			crt.requestEnd();
		}
	}
}
