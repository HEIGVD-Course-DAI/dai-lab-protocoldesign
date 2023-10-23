package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

public class Server {
	final int SERVER_PORT = 1234;

	public static void main(String[] args) {
		// Create a new server and run it
		Server server = new Server();
		server.run();
	}

	private void run() {
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {
			while (true) {
				// Create a passive socket to wait for new connections
				System.out.println("Server socket is up");

				// Create an active socket once we get a client
				try (Socket socket = serverSocket.accept();
						var in = new BufferedReader(
								new InputStreamReader(
										socket.getInputStream(), UTF_8));
						var out = new BufferedWriter(
								new OutputStreamWriter(
										socket.getOutputStream(), UTF_8))) {

					out.write(getWelcomeMessage() + "\n");
					out.flush();
					// wait for a client to be connected with the server
					String line;
					while ((line = in.readLine()) != null) {
						out.write(line + "\n");
						out.flush();
					}
				} catch (IOException e) {
					System.out.println("Server: server socket ex.: " + e);
				}
			}
		} catch (IOException e) {
			System.out.println("Server: server socket ex.: " + e);
		}
	}

	private String getWelcomeMessage() {
		return "Welcome to SuperCalculationServer !\n" +
				"\n" +
				"You can do the following calculations.\n" +
				"- Sum: <operand 1> + <operand 2>\n" +
				"- Substraction: <operand 1> - <operand 2>\n" +
				"- Multiplication: <operand 1> * <operand 2>\n" +
				"- Division: <operand 1> / <operand 2>";
	}

}