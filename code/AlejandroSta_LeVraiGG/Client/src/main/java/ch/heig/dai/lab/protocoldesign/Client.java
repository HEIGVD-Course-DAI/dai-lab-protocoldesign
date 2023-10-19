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
        //Copy of given example on side:
        try (Socket socket = new Socket("localhost", 1234)) ;
        InputStream in = new BufferedInputStream(socket.getInputStream());
        OutputStream out = new BufferedOutputStream(socket.getOutputStream());){
            for (int i = 0; i < 10; i++) {
                out.write(i);
                out.flush();
                System.out.println("Echo: " + in.read());
            }
        } catch(IOException e){
            System.out.println("Client: exception : " + e);
        }
    }
}