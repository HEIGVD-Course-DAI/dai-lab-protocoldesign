package code.kevinAuberson_AdrianRogner.Client.src.main.java.ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class TextualTCPClient {

    private Socket socket = null;
    private String address = "localhost";
    private BufferedReader in;
    private BufferedWriter out;
    private int port;

    TextualTCPClient(String address, int port) {
        if (address != null)
            this.address = address;
        this.port = port;
    }

    public void openConnection() {
        try {
            this.socket = new Socket(address, port);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            System.out.println("Connection to server open on port :" + port);
            String info;
            while ((info = in.readLine()) != null) {
                if(info.equals("END_WELCOME"))
                    break;
                System.out.println(info);
            }
        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }

    public void sendRequest() {
        try {
                BufferedReader commandIn = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    System.out.print("Enter commands: ");
                    String command = commandIn.readLine();
                    out.write(command + "\n");
                    out.flush();
                    System.out.println(in.readLine());
                    if(command.equalsIgnoreCase("EXIT")) {
                        out.close();
                        in.close();
                        socket.close();
                        commandIn.close();
                        break;
                    }
                }
            } catch(IOException e){
                System.out.println("Client: exc.: " + e);
            }
        }
    }

