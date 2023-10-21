package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.Stack;

public class Server {
	final int SERVER_PORT = 51740;
	final String BASE_MESSAGE = "GCCP";
	final String INFO_MESSAGE = "HELLO";
	final String END_MESSAGE = "BYE";
	final String EASTER_EGG_CODE = "🟢🐱";
	final String EASTER_EGG_TEXT = "                             .                                   \n" + //
			"                          ,''`.         _                        \n" + //
			"                     ,.,'''  '`--- ._,,'|                        \n" + //
			"                   ,'                   /                        \n" + //
			"              __.-'                    |                         \n" + //
			"           ''                ___   ___ |                         \n" + //
			"         ,'                  \\(|\\ /|)/ |                         \n" + //
			"        ,'                 _     _     `._                       \n" + //
			"       /     ,.......-\\    `.      __     `-.                    \n" + //
			"      /     ,' :  .:''`|    `:`.../:.`` ._   `._                 \n" + //
			"  ,..,'  _/' .: :'     |     |      '.    \\.    \\                \n" + //
			" /      ,'  :'.:  / \\  |     | / \\   ':.  . \\    \\               \n" + //
			"|      /  .: :' ,'  _)  \".._,;'  _)    :. :. \\    |              \n" + //
			" |     | :'.:  /   |   .,   /   |       :  :  |   |              \n" + //
			" |     |:' :  /____|  /  \\ /____|       :  :  |  ,'              \n" + //
			"  |   /    '         /    \\            :'   : |,/                \n" + //
			"   \\ |  '_          /______\\              , : |                  \n" + //
			"  _/ |  \\'`--`.    _            ,_   ,-'''  :.|         __       \n" + //
			" /   |   \\..   ` ./ `.   _,_  ,'  ``'  /'   :'|      _,''/       \n" + //
			"/   /'. :   \\.   _    [_]   `[_]  .__,,|   _....,--=/'  /:       \n" + //
			"|   \\_| :    `.-' `.    _.._     /     . ,'  :. ':/'  /'  `.     \n" + //
			"`.   '`'`.         `. ,.'   ` .,'     :'/ ':..':.    |  .:' `.   \n" + //
			"  \\.      \\          '               :' |    ''''      ''     `. \n" + //
			"    `''.   `|        ':     .      .:' ,|         .  ..':.      |\n" + //
			"      /'   / '\"-..._  :   .:'    _;:.,'  \\.     .:'   :. ''.    |\n" + //
			"     (._,.'        '`''''''''''''          \\.._.:      ':  ':   /\n" + //
			"________                                      '`- ._    ,:__,,-' \n" + //
			"  |                                                 ``''\n";

	public static void main(String[] args) {
		// Create a new server and run it
		Server server = new Server();
		server.run();
	}

	static double factorial(double n) {
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
						if (line.substring(0, 4).equals(BASE_MESSAGE)) {
							if (line.substring(5, 8).equals(END_MESSAGE)) {
								out.write(BASE_MESSAGE + " " + END_MESSAGE);
								out.flush();
								in.close();
								out.close();
							} else if (line.substring(5, 9).equals(EASTER_EGG_CODE)) {
								out.write(EASTER_EGG_TEXT);
							} else if (line.substring(5, 10).equals(INFO_MESSAGE)) {
								out.write("GCCP HELLO <OPERATIONS>\n" + " Example : GCCP HELLO + * / sin ln\n");
							} else {
								out.write(BASE_MESSAGE + " " + String.valueOf(calculation(line.substring(5))) + "\n");
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