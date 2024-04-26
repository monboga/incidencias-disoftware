package src.javasqlriskmanager.controllers.rolcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Rol;
import src.javasqlriskmanager.models.Usuario;
import src.javasqlriskmanager.singletons.RolSingleton;
import src.javasqlriskmanager.singletons.SesionSingleton;
import src.javasqlriskmanager.singletons.UserSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;
import src.javasqlriskmanager.singletons.UserSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class DetalleRolController implements Initializable {

    @FXML
    TextField id;

    @FXML
    TextField name;

    @FXML
    Button editButton;

    @FXML
    Button saveButton;

    public void editarUsuario() {
        // Habilitar la edición de campos
        name.setDisable(false);

        // Deshabilitar el botón "Editar" y habilitar el botón "Guardar"
        editButton.setDisable(true);
        saveButton.setDisable(false);
    }

    public void guardarCambios() {
        // Obtener los valores actualizados de los campos
        Rol rol = RolSingleton.instance.getRol();
        rol.setID((long) Integer.parseInt(id.getText()));
        String userName = name.getText();

        // Crear una conexión a la base de datos
        Connection con = ConnectToDB.connectToDB();

        if (con == null) {
            // Manejar la conexión nula o errores de conexión
            System.out.println("Error al conectar a la base de datos.");
            return;
        }

        // Construir la sentencia SQL UPDATE
        String updateQuery = "UPDATE User_Roles SET Name = ? WHERE ID = ?";

        try {
            // Crear un PreparedStatement para ejecutar la sentencia UPDATE
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, userName);
            pstmt.setLong(2, rol.getID());


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
            id.setDisable(true);
            name.setDisable(true);
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatRoles.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de roles");
        principalStage.setScene(scene);
        principalStage.setResizable(false);principalStage.setResizable(false);
        principalStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rol rol = RolSingleton.instance.getRol();

        id.setText(rol.getID().toString());
        name.setText(rol.getName());

        id.setDisable(true);
        name.setDisable(true);

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

            if(RolSingleton.getInstance().getRol().getID().equals(SesionSingleton.getInstance().getUsuario().getID_Role())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No puedes eliminar el rol de adminisitrador.");
                alert.showAndWait();
            } else {
                try {
                    Connection con = ConnectToDB.connectToDB();

                    String query = "UPDATE Users SET ID_Role = NULL WHERE ID_Role = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setLong(1, RolSingleton.getInstance().getRol().getID());
                    preparedStatement.execute();

                    query = "DELETE FROM User_Roles WHERE ID = ?";
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setLong(1, RolSingleton.getInstance().getRol().getID());
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
