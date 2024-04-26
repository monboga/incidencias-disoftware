package src.javasqlriskmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Usuario;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.sql.*;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class MainMenuController {

    @FXML
    private Label lblBienvenida;
    private LoginController controllerLogin;
    private Stage stage;
    private Long idUser;
    private Parent root;


    @FXML
    protected void  goToIncidents() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatIncidencias.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de incidencias");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    @FXML
    protected void goToUsers() throws IOException{
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatUsuarios.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de usuarios");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    @FXML
    protected void goToDeptos() throws IOException{
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatDepartamentos.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de departamentos");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    @FXML
    protected void goToRoles() throws IOException{
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatRoles.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de roles");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    public void init(Long id, Stage principalStage, LoginController loginController) {
        idUser = id;
        this.controllerLogin = loginController;
        this.stage = principalStage;

        String selectQuery = "SELECT * FROM Users WHERE ID = ?";
        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            preparedStatement.setString(1,id.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                String nombre = rs.getString("Name");
                lblBienvenida.setText("Bienvenido(a) " + nombre);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void salir() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Inicio de sesión");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }
}
