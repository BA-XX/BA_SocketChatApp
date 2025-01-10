package ba.socketchatapp.server.services;

import ba.socketchatapp.outils.Outils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatListenerImpl implements IChatListener {

    private final Socket socket;
    private final IChatService chatService;

    public ChatListenerImpl(Socket socket, IChatService chatService) {
        this.socket = socket;
        this.chatService = chatService;

        notify("Bienvenue sur le chat");
        Outils.log("Utilisateur with @ip : " + socket.getInetAddress().getHostAddress() + " est connecte");
    }

    @Override
    public void notify(String message) {
        try {
            Outils.sendMessage(socket.getOutputStream(), message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while (true) {

                String message = Outils.readMessage(br);

                if (message != null) {
                    chatService.notifyAll(message);
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            //throw new RuntimeException(e);
        } finally {
            chatService.removeUserListener(this);
            Outils.log("Utilisateur with @ip : " + socket.getInetAddress().getHostAddress() + " est deconnecte");

        }
    }

}
