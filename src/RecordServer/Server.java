package src.RecordServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6000);
            ArrayList<String> times = new ArrayList<String>();
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();

                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

                String playerName = input.readLine();
                times.add(playerName);
                displayTimes(times);

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    static void displayTimes(SortedMap<String, double[]> plMap) {
        for (Map.Entry<String, double[]> entry : plMap.entrySet()) {
            System.out.println(entry.getKey() + ":");
            double[] time = entry.getValue();
            for (int i = 0; i < time.length - 1; i++) {
                System.out.println(i + " - " + time[i] + ", ");
            }
            System.out.println(time.length - 1 + " - " + time[time.length - 1]);
        }
    }

    static void displayTimes(ArrayList<String> times) {
        for (String string : times) {
            System.out.println(string);
        }
    }
}