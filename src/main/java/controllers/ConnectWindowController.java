package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import staticUtils.StaticUtilsVariables;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by andreiiorga on 28/06/2017.
 */
public class ConnectWindowController {
    @FXML
    private TextField serverIP;

    @FXML
    private TextField serverPort;


    private MainController mainController;

    @FXML
    private void initialize(){
        serverPort.setText(StaticUtilsVariables.SERVER_PORT + "");
        serverIP.setText(StaticUtilsVariables.SERVER_IP);
        serverPort.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    serverPort.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void ConnectAction(ActionEvent actionEvent) {
        StaticUtilsVariables.SERVER_IP = serverIP.getText();
        StaticUtilsVariables.SERVER_PORT = Integer.parseInt(serverPort.getText());
        mainController.connectToServer();

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
