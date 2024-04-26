package src.javasqlriskmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import src.javasqlriskmanager.MainApplication;

import java.io.IOException;
import java.sql.SQLException;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class MainController {

    @FXML
    protected void onHelloButtonClick() throws SQLException, IOException {

        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-incident.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("CREAR INCIDENCIA");
        principalStage.setScene(scene);
        principalStage.show();
    }
}