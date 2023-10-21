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
					stack.push(stack.pop() + stack.pop());
					break;
				case "-":
					stack.push(-stack.pop() + stack.pop());
					break;
				case "*":
					stack.push(stack.pop() * stack.pop());
					break;
				case "/":
					double divisor = stack.pop();
					stack.push(stack.pop() / divisor);
					break;
				case "^":
					double exponent = stack.pop();
					stack.push(Math.pow(stack.pop(), exponent));
					break;
				case "π":
					stack.push(Math.PI);
					break;
				case "√":
					stack.push(Math.sqrt(stack.pop()));
					break;
				case "e":
					stack.push(Math.E);
					break;
				case "sin":
					stack.push(Math.sin(stack.pop()));
					break;
				case "cos":
					stack.push(Math.cos(stack.pop()));
					break;
				case "tan":
					stack.push(Math.tan(stack.pop()));
					break;
				case "ln":
					stack.push(Math.log(stack.pop()));
					break;
				default:
					stack.push(Double.parseDouble(token));
					break;
			}
		}
		return stack.pop();
	}
}