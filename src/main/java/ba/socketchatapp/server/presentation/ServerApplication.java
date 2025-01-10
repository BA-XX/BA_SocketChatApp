package ba.socketchatapp.server.presentation;

import ba.socketchatapp.server.services.ChatServiceImpl;
import ba.socketchatapp.server.services.IChatService;

public class ServerApplication {
    public static void main(String[] args) {

        IChatService chatService = new ChatServiceImpl();
        chatService.start();

    }
}
