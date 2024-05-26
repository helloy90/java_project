package src.TimeRecord;

import java.io.*;
import java.net.*;

public class ServerRecord extends Thread {

    String entry;
    
    ServerRecord(String player, double[] time) {
        super();

        entry = player + ": ";

        for (int i = 0; i < time.length; i++) {
            entry += i + " - " + Double.toString(time[i]) + " ";
        }

    }

    public void run() {
        try {
            Socket socket = new Socket("localhost", 6000);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream output = new DataOutputStream(outputStream);

            output.writeChars(entry);

            socket.close();
        } catch (UnknownHostException exception) {
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
