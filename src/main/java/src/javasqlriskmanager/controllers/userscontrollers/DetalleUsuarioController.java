package src.javasqlriskmanager.controllers.userscontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Department;
import src.javasqlriskmanager.models.Usuario;
import src.javasqlriskmanager.singletons.IncidentSingleton;
import src.javasqlriskmanager.singletons.SesionSingleton;
import src.javasqlriskmanager.singletons.UserSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class DetalleUsuarioController implements Initializable {

    @FXML
    TextField idText;

    @FXML
    TextField name;

    @FXML
    TextField email;

    @FXML
    TextField password;

    @FXML
    TextField posicion;

    @FXML
    TextField rol;

    @FXML
    TextField department;

    @FXML
    Button editButton;

    @FXML
    Button saveButton;

    public void editarUsuario() {
        // Habilitar la edición de campos
        name.setDisable(false);
        email.setDisable(false);
        password.setDisable(false);
        posicion.setDisable(false);
        rol.setDisable(false);
        department.setDisable(false);

        // Deshabilitar el botón "Editar" y habilitar el botón "Guardar"
        editButton.setDisable(true);
        saveButton.setDisable(false);
    }

    public void guardarCambios() {
        // Obtener los valores actualizados de los campos
        Usuario usuario = UserSingleton.getInstance().getUsuario();
        usuario.setID((long) Integer.parseInt(idText.getText()));
        String userName = name.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();
        String userPosition = posicion.getText();
        long userRole = Long.parseLong(rol.getText());
        long userDepartment = Long.parseLong(department.getText());

        // Crear una conexión a la base de datos
        Connection con = ConnectToDB.connectToDB();

        if (con == null) {
            // Manejar la conexión nula o errores de conexión
            System.out.println("Error al conectar a la base de datos.");
            return;
        }

        // Construir la sentencia SQL UPDATE
        String updateQuery = "UPDATE Users SET Name = ?, Email = ?, Password = ?, Position = ?, ID_Role = ?, ID_Department = ? WHERE ID = ?";

        try {
            // Crear un PreparedStatement para ejecutar la sentencia UPDATE
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, userName);
            pstmt.setString(2, userEmail);
            pstmt.setString(3, userPassword);
            pstmt.setString(4, userPosition);
            pstmt.setLong(5, userRole);
            pstmt.setLong(6, userDepartment);
            pstmt.setLong(7, usuario.getID());


            // Ejecutar la sentencia SQL UPDATE
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // La actualización fue exitosa
                System.out.println("Los cambios se guardaron correctamente.");
            } else {
                // La actualización no afectó ninguna fila
                System.out.println("Ninguna fila se actualizó. Verifique el ID de usuario.");
            }

            // Cerrar el PreparedStatement y la conexión
            pstmt.close();
            con.close();

            // Deshabilitar la edición de campos y habilitar el botón "Editar"
            idText.setDisable(true);
            name.setDisable(true);
            email.setDisable(true);
            password.setDisable(true);
            posicion.setDisable(true);
            rol.setDisable(true);
            department.setDisable(true);
            editButton.setDisable(false);
            saveButton.setDisable(true);

        } catch (SQLException e) {
            // Manejar errores de SQL
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se pudo realizar la actuación de información. Revisar la consola para más información.");
            alert.showAndWait();
            System.err.println("Error al ejecutar la sentencia SQL: " + e.getMessage());
        }
    }

    public void irCatalogo() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatUsuarios.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de usuarios");
        principalStage.setScene(scene);
        principalStage.setResizable(false);principalStage.setResizable(false);
        principalStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuario = UserSingleton.getInstance().getUsuario();

        idText.setText(usuario.getID().toString());
        name.setText(usuario.getName());
        email.setText(usuario.getEmail());
        password.setText(usuario.getPassword());
        posicion.setText(usuario.getPosition());
        rol.setText(usuario.getID_Role().toString());
        department.setText(usuario.getID_Department().toString());

        // Deshabilitar la edición de campos al principio
        idText.setDisable(true);
        name.setDisable(true);
        email.setDisable(true);
        password.setDisable(true);
        posicion.setDisable(true);
        rol.setDisable(true);
        department.setDisable(true);

        // Habilitar el botón "Editar"
        editButton.setDisable(false);
        saveButton.setDisable(true);
    }

    public void delete() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de eliminar.");
        alert.setContentText("¿Desea confirmar proceder con la eliminación del registro?");
        alert.showAndWait();

        if(alert.getResult().getText().equals("Aceptar")) {

            if(UserSingleton.getInstance().getUsuario().getID().equals(SesionSingleton.getInstance().getUsuario().getID())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No puedes eliminar el usuario con con el que iniciaste sesión.");
                alert.showAndWait();
            } else {
                try {
                    Connection con = ConnectToDB.connectToDB();

                    String query = "UPDATE Incidents SET ID_CreatorUser = NULL WHERE ID_CreatorUser = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setLong(1, UserSingleton.getInstance().getUsuario().getID());
                    preparedStatement.execute();

                    query = "UPDATE Incidents SET ID_AssignedUser = NULL WHERE ID_AssignedUser = ?";
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setLong(1, UserSingleton.getInstance().getUsuario().getID());
                    preparedStatement.execute();

                    query = "DELETE FROM Users WHERE ID = ?";
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setLong(1, UserSingleton.getInstance().getUsuario().getID());
                    preparedStatement.execute();

                    con.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                irCatalogo();
            }
        }
    }
}
