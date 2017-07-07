package App;

import controllers.ConnectWindowController;
import controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    MainController mainController;
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainWindow.fxml"));
        Parent root2 = loader.load();
        primaryStage.setTitle("Licenta Andrei Iorga 2017");
        primaryStage.setScene(new Scene(root2, 1000, 550));
        primaryStage.getScene().getStylesheets().add("styles/styles.css");
        mainController = loader.getController();

        primaryStage.setOnCloseRequest(e -> {
            mainController.disconnect();
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
