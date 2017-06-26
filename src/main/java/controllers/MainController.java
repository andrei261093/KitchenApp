package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import tcpClient.TcpClient;

public class MainController {
    @FXML
    private TableView<?> orderTable;

    @FXML
    private TableView<?> productTable;
    private TcpClient tcpClient;

    @FXML
    public void initialize(){
        tcpClient = new TcpClient(this);
    }

    @FXML
    void markOrderAsDone(ActionEvent event) {
        //mark order as done
    }

    @FXML
    void removeOrderFromTable(ActionEvent event) {
        //remove order from list
    }
    @FXML
    public void connectToServer(ActionEvent actionEvent) {
        tcpClient.connect();
        tcpClient.sentToServer("connected - from kitchen");
    }
}
