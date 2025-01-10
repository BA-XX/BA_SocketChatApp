package ba.socketchatapp.client.presentation;

import ba.socketchatapp.client.services.ChatServiceImpl;
import ba.socketchatapp.client.services.ConnectionStatus;
import ba.socketchatapp.client.services.IChatListener;
import ba.socketchatapp.client.services.IChatService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatClientController implements IChatListener {

    private IChatService chatService;

    private ObservableList<String> messages;

    @FXML
    private Text connectionStatusText;

    @FXML
    private TextField serverIp;

    @FXML
    private TextField serverPort;

    @FXML
    private Button connectButton;

    @FXML
    private ListView<String> messagesListView;


    @FXML
    private TextArea messageTextField;

    @FXML
    private Button sendButton;

    @FXML
    void sendMessage(ActionEvent event) {
        String message = messageTextField.getText();
        if (!message.isBlank()) {
            chatService.sendMessage(message);
            messageTextField.clear();
        }
    }

    @FXML
    void connect(ActionEvent event) {

        if (chatService != null && chatService.getConnectionStatus() == ConnectionStatus.CONNECTED) {
            chatService.stop();
        } else {

            String ip = serverIp.getText();
            String port = serverPort.getText();

            // check if ip and port are empty if so set default values
            if (ip.isBlank()) {
                ip = "localhost";
            }
            if (port.isBlank()) {
                port = "9999";
            }

            chatService = new ChatServiceImpl(ip, Integer.parseInt(port));
            chatService.setListener(this);

            messages = FXCollections.observableArrayList();
            messagesListView.setItems(messages);
            chatService.start();
        }
    }

    @Override
    public void onMessageReceived(String message) {
        Platform.runLater(() -> messages.add(
                "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "] " + message));
    }

    @Override
    public void onConnectionStatusChanged() {
        updateConnectionStatus();
    }

    private void updateConnectionStatus() {

        switch (chatService.getConnectionStatus()) {
            case CONNECTED:

                connectButton.setText("Disconnect");

                connectionStatusText.setText("Connected");
                connectionStatusText.setStyle("-fx-fill: green");

                messageTextField.setDisable(false);
                sendButton.setDisable(false);

                break;
            case DISCONNECTED:

                connectButton.setText("Connect");

                connectionStatusText.setText("Disconnected");
                connectionStatusText.setStyle("-fx-fill: red");

                messageTextField.setDisable(true);
                sendButton.setDisable(true);
                break;
        }
    }
}
