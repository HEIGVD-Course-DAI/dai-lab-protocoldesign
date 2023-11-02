import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    public static void main(String[] args)
    {
        String host = "localhost";
        int port = 42069;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println(in.readLine());

            String userInput;

            while ((userInput = stdIn.readLine()) != null)
            {
                out.println(userInput);

                String serverResponse = in.readLine();
                System.out.println(serverResponse);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
