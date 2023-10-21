package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import javax.naming.spi.DirStateFactory.Result;

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
					double result;
					if (line != null) {
						result = calculation(line);
						out.write(String.valueOf(result));
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

		Stack<Double> stack = new Stack<>();

		for (String token : source.split("\\s+")) {
			System.out.print(token + "\t");
			switch (token) {
				case "+":
					System.out.print("Operate\t\t");
					stack.push(stack.pop() + stack.pop());
					break;
				case "-":
					System.out.print("Operate\t\t");
					stack.push(-stack.pop() + stack.pop());
					break;
				case "*":
					System.out.print("Operate\t\t");
					stack.push(stack.pop() * stack.pop());
					break;
				case "/":
					System.out.print("Operate\t\t");
					double divisor = stack.pop();
					stack.push(stack.pop() / divisor);
					break;
				case "^":
					System.out.print("Operate\t\t");
					double exponent = stack.pop();
					stack.push(Math.pow(stack.pop(), exponent));
					break;
				default:
					System.out.print("Push\t\t");
					stack.push(Double.parseDouble(token));
					break;
			}
		}
		return stack.pop();
	}
}