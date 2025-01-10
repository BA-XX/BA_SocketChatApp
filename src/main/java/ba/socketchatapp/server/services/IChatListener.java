package ba.socketchatapp.server.services;

public interface IChatListener extends Runnable {
    void notify(String message);
}
