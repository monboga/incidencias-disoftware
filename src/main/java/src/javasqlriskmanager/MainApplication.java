package src.javasqlriskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static Stage principalStage;
    @Override
    public void start(Stage stage) throws IOException {
        principalStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Inicio de sesión");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}