package ch.heig.dai.lab.protocoldesign;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ComputeSession {
    public Boolean connect() {
        // Open TCP connection to server
        try {
            ;
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void disconnect() {
        // Close TCP connection to server
        try {
            socket.close();
        } catch (Exception e) {
            // Ignore
        }
    }

    public String compute(Operation operation) {
        // Send operation
        if (!send(operation)) {
            throw new RuntimeException("Failed to send operation");
        }

        // Read result
        String result = readResult();

        // Check result
        if (result == null) {
            throw new RuntimeException("Failed to read result");
        }

        return result;
    }

    private boolean send(Operation operation) {
        // Check if connected
        if (socket == null) {
            return false;
        }

        try {
            // Create output stream
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), charset);

            // Send operation
            out.write(operation.toString());
            out.flush();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private String readResult() {
        // Check if connected
        if (socket == null) {
            return null;
        }

        try {
            // Create input stream
            InputStreamReader in = new InputStreamReader(socket.getInputStream(), charset);

            // Read result
            StringBuilder result = new StringBuilder();
            int c;
            while ((c = in.read()) != lineSeparator) {
                result.append((char) c);
            }

            // Return result
            return result.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
