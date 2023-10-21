package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class Server {
	final int SERVER_PORT = 51740;

	public static void main(String[] args) {
		// Create a new server and run it
		Server server = new Server();
		server.run();
	}

	private void run() {
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
			while (true) {

				try (Socket socket = serverSocket.accept();
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

					String line = in.readLine();
					if (line != null) {
						double result = calculation(line);
					}
				} catch (IOException e) {
					System.out.println("Server: socket ex.: " + e);
				}
			}
		} catch (IOException e) {
			System.out.println("Server: server socket ex.: " + e);
		}
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	private double calculation(String source) {

		Stack<String> words = new Stack<>();
		Collections.addAll(words, source.split("\\s"));

		return 0.0;
	}
}