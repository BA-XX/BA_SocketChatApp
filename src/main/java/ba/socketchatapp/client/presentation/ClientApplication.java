package ba.socketchatapp.client.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ba/socketchatapp/client/presentation/chat-app-client.fxml"));

        Scene scene = new Scene(loader.load(), 720, 480);
        stage.setTitle("SocketChatApp");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}