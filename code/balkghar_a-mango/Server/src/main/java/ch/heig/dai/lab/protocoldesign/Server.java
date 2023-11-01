package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.Stack;

public class Server {
	private final int SERVER_PORT = 51740;
	private final String BASE_MESSAGE = "GCCP"; // stand for Green Cat Calculation Protocol
	private final String INFO_MESSAGE = "HELLO";
	private final String END_MESSAGE = "BYE";
	private final String SUPPORTED_OPERAIONS = "+ - * / ^ √ sin cos tan ln !";
	private final String MATHS_CONSTANTS = "π e";
	private final String WELCOME_MESSAGE = BASE_MESSAGE + " " + INFO_MESSAGE + " " + SUPPORTED_OPERAIONS + "\n";
	private final String ERROR_ONE = BASE_MESSAGE + " ERR 01\n";
	private final String ERROR_TWO = BASE_MESSAGE + " ERR 02\n";
	private final String ERROR_THREE = BASE_MESSAGE + " ERR 03\n";
	private final String ERROR_FOUR = BASE_MESSAGE + " ERR 04\n";
	private final String EASTER_EGG_CODE = "🟢🐱";
	private final String EASTER_EGG_TEXT = "⠀ ／l、\t MEOW \n" + //
			"（ﾟ､ ｡７\n" + //
			"⠀ l、ﾞ~ヽ\n" + //
			"  じしf_,)ノ\n";

	public static void main(String[] args) {
		// Create a new server and run it
		Server server = new Server();
		server.run();
	}

	private static double factorial(double n) {
		if (n == 0)
			return 1;
		else
			return (n * factorial(n - 1));
	}

	private void run() {
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
			while (true) {

				try (Socket socket = serverSocket.accept();
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

					String line;

					while ((line = in.readLine()) != null) {
						if (line.length() >= 8 && line.substring(0, 4).equals(BASE_MESSAGE)) {
							if (line.substring(5, 8).equals(END_MESSAGE)) {
								out.write(BASE_MESSAGE + " " + END_MESSAGE);

							} else if (line.length() >= 9 && line.substring(5, 9).equals(EASTER_EGG_CODE)) {
								out.write(EASTER_EGG_TEXT);
							} else if (line.length() >= 10 && line.substring(5, 10).equals(INFO_MESSAGE)) {
								out.write(WELCOME_MESSAGE);
							} else if (line.length() >= 8) {
								try {
									out.write(BASE_MESSAGE + " " + String.valueOf(calculation(line.substring(5))) + "\n");

								} catch (IllegalArgumentException e) {
									out.write(ERROR_ONE);
								}
							} else {
								out.write(ERROR_ONE);
							}
							out.flush();
						}
					}

				} catch (IOException e) {
					System.out.println("Server: socket ex.: " + e);
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("Server: server socket ex.: " + e);
			return;
		}
	}

	private double calculation(String source) {

		Stack<Double> stack = new Stack<>();

		for (String token : source.split("\\s+")) {
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
				case "!":
					stack.push(factorial(stack.pop()));
					break;
				default:
					try {
						stack.push(Double.parseDouble(token));
					} catch (Exception ex) {
						throw new IllegalArgumentException("This isn't a double :/");
					}
					break;
			}
		}
		return stack.pop();
	}
}