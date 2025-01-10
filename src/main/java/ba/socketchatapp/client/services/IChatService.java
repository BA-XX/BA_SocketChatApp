package ba.socketchatapp.client.services;

public interface IChatService {
    void sendMessage(String message);

    void setListener(IChatListener listener);

    void start();

    void stop();

    ConnectionStatus getConnectionStatus();
}
