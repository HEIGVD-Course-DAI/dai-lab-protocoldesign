import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int port = 42069;

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {

            out.println("AVAILABLE OPERATIONS ADD (ex. ADD 10 20) MUL (ex. MUL 30 4 66)");

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                String[] tokens = inputLine.split(" ");
                String command = tokens[0];
                int[] operands = new int[tokens.length - 1];

                for (int i = 0; i < operands.length; i++)
                {
                    operands[i] = Integer.parseInt(tokens[i + 1]);
                }

                int result;

                switch (command)
                {
                    case "ADD":
                        result = 0;
                        for (int operand : operands)
                        {
                            result += operand;
                        }
                        break;
                    case "MUL":
                        result = 1;
                        for (int operand : operands)
                        {
                            result *= operand;
                        }
                        break;
                    case "QUIT":
                        out.println("CLOSED");
                        return;
                    default:
                        out.println("ERROR: Invalid command");
                        continue;
                }

                out.println(result);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
