package ba.socketchatapp.client.services;

import ba.socketchatapp.outils.Outils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatServiceImpl implements IChatService {

    private IChatListener listener;

    private String ipAdress;
    private int port;

    private Socket socket;

    private ConnectionStatus connectionStatus;

    private Thread listeningThread;

    public ChatServiceImpl(String ipAdress, int port) {
        this.ipAdress = ipAdress;
        this.port = port;

        connectionStatus = ConnectionStatus.DISCONNECTED;
    }

    @Override
    public void sendMessage(String message) {

        if (connectionStatus == ConnectionStatus.CONNECTED) {
            try {
                Outils.sendMessage(socket.getOutputStream(), message);
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setListener(IChatListener listener) {
        this.listener = listener;
    }

    @Override
    public void start() {
        try {
            socket = new Socket(ipAdress, port);

            connectionStatus = ConnectionStatus.CONNECTED;
            listener.onConnectionStatusChanged();


            //  listening for messages
            listeningThread = new Thread(() -> {
                try {
                    BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    while (true) {

                        String message = Outils.readMessage(bf);

                        if (message == null) {
                            break;
                        }

                        listener.onMessageReceived(message);

                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                } finally {
                    stop();
                }
            });
            listeningThread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            listeningThread.interrupt();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connectionStatus = ConnectionStatus.DISCONNECTED;
            listener.onConnectionStatusChanged();
        }
    }

    @Override
    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }
}
