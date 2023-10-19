package ch.heig.dai.lab.protocoldesign;


public class Client {
    final String SERVER_ADDRESS = "1.2.3.4";
    final int SERVER_PORT = 1234;
    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        system.out.println("Client running");

        try (Socket socket = new Socket("localhost", SERVER_PORT));
        var in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(),
                        StandardCharsets.UTF_8));
        var out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(),
                        StandardCharsets.UTF_8))){
            system.out.println("Connexion ok");
            system.out.println("Entree votre calcul : ");
            Scanner scanner = new Scanner(System.in);
            String ligne = scanner.nextLine();
            system.out.println(in.read(););
            out.write(calcul);
            out.flush();
            }
        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }

    }
}