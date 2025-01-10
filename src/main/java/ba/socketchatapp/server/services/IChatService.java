package ba.socketchatapp.server.services;

public interface IChatService {

    void notifyAll(String message);

    void addUserListener(IChatListener listener);

    void removeUserListener(IChatListener listener);

    void start();

}
