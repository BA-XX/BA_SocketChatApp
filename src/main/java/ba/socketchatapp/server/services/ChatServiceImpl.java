package ba.socketchatapp.server.services;

import ba.socketchatapp.outils.Outils;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements IChatService {

    private List<IChatListener> chatListeners;

    {
        chatListeners = new ArrayList<>();
    }

    @Override
    public void notifyAll(String message) {
        chatListeners.forEach((listener) -> {
            listener.notify(message);
        });
    }

    @Override
    public void addUserListener(IChatListener listener) {
        chatListeners.add(listener);
    }

    @Override
    public void removeUserListener(IChatListener listener) {
        chatListeners.remove(listener);
    }

    @Override
    public void start() {
        try {

            ServerSocket server = new ServerSocket(9999);

            Outils.log("Server started on port 9999");

            while (true) {
                IChatListener listener = new ChatListenerImpl(server.accept(), this);
                addUserListener(listener);
                new Thread(listener).start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
