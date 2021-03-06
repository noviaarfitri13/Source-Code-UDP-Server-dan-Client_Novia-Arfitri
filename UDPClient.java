package udpclient;

import java.io.*;
import java.net.*;

public class UDPClient {

    private static InetAddress host;
    private static final int PORT = 1234;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String args[]) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID Not Found");
            System.exit(1);
        }
        run();
    }

    private static void run() {
        try {
            dgramSocket = new DatagramSocket();
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String message = "", response = "";
            do {
                System.out.println("Enter message:");
                message = userEntry.readLine();
                if (!message.equals("CLOSE")) {
                    outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
                    dgramSocket.send(outPacket);
                    buffer = new byte[256];
                    inPacket = new DatagramPacket(buffer, buffer.length);
                    dgramSocket.receive(inPacket);
                    response = new String(inPacket.getData(), 0, inPacket.getLength());
                    System.out.println("SERVER :" + response);
                }
            } while (!message.equals("CLOSE"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Closing Connection");
            dgramSocket.close();
        }
    }

}
