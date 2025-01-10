package ba.socketchatapp.outils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class Outils {
    public static void log(String message) {
        System.out.println("[" + LocalDateTime.now() + "] " + message);
    }

    public static String readMessage(BufferedReader br) throws IOException {

        // TODO: add support for messages with multiple lines

        return br.readLine();
    }

    public static void sendMessage(OutputStream os, String message) throws IOException {
        message = message.replaceAll("[\n\r]", " ");
        os.write((message + "\n").getBytes());

        // TODO: add support for messages with multiple lines
    }
}
