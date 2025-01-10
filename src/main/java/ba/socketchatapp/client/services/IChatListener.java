package ba.socketchatapp.client.services;

public interface IChatListener {

    void onMessageReceived(String message);

    void onConnectionStatusChanged();

}
