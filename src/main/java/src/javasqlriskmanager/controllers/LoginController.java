package src.javasqlriskmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Usuario;
import src.javasqlriskmanager.singletons.SesionSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class LoginController {

    @FXML
    TextField emailLogin;

    @FXML
    PasswordField passLogin;

    Usuario user = null;

    public static SesionSingleton sesionSingleton = SesionSingleton.getInstance();

    public void login() throws IOException, SQLException {
        boolean loginSuccess = false;


        if(!emailLogin.getText().isBlank() && !passLogin.getText().isBlank()) {
                String selectQuery = "SELECT * FROM Users WHERE email = ?";
            try {
                Connection con = ConnectToDB.connectToDB();
                PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
                preparedStatement.setString(1,emailLogin.getText());
                ResultSet rs = preparedStatement.executeQuery();

                while(rs.next()) {
                    if(rs.getString("Password").equals(passLogin.getText())) {
                        loginSuccess = true;
                        user = new Usuario(rs.getLong("ID"), rs.getString("Name"),rs.getString("Email"),rs.getString("Position"),
                                rs.getLong("ID_Role"),rs.getLong("ID_Department"),rs.getString("Password"));
                        sesionSingleton.setUsuario(user);
                    }
                }

                con.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        if(loginSuccess == true) {
            String selectQuery = "SELECT * FROM User_Roles WHERE ID = ?";
            try {
                Connection con = ConnectToDB.connectToDB();
                PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
                preparedStatement.setLong(1,user.getID_Role());
                ResultSet rs = preparedStatement.executeQuery();

                if(rs.next()) {
                    compareRoles(rs);
                    while(rs.next())
                        compareRoles(rs);
                } else {
                    sesionSingleton.setAdmin(false);
                    principalStage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatIncidencias.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    principalStage.setTitle("Catálogo de incidencias");
                    principalStage.setScene(scene);
                    principalStage.setResizable(false);
                    principalStage.show();
                }

                con.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No se pudo iniciar sesión, credenciales no válidas.");
                alert.showAndWait();
            }
        }

        public void compareRoles(ResultSet rs) throws IOException, SQLException {
            if(rs.getString("Name").equals("ADMIN") || rs.getString("Name").equals("DEFAULT")) {
                sesionSingleton.setAdmin(true);
                principalStage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-menu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                principalStage.setTitle("Menú Principal");
                principalStage.setScene(scene);
                principalStage.setResizable(false);
                principalStage.show();

            }else{
                sesionSingleton.setAdmin(false);
                principalStage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatIncidencias.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                principalStage.setTitle("Catálogo de incidencias");
                principalStage.setScene(scene);
                principalStage.setResizable(false);
                principalStage.show();
            }
        }

}
