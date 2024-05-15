package src.javasqlriskmanager.controllers.serverscontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Department;
import src.javasqlriskmanager.models.Rol;
import src.javasqlriskmanager.models.Server;
import src.javasqlriskmanager.singletons.DepartmentSingleton;
import src.javasqlriskmanager.singletons.RolSingleton;
import src.javasqlriskmanager.singletons.ServerSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;
import src.javasqlriskmanager.singletons.UserSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class DetalleServersController implements Initializable {


    @FXML
    TextField name;

    @FXML
    TextField description;

    @FXML
    TextField price;

    @FXML
    TextField warranty;

    @FXML
    TextField totalCost;

    @FXML
    Button editButton;

    @FXML
    Button saveButton;

    public void editServer() {
        // Habilitar la edición de campos
        name.setDisable(false);
        description.setDisable(false);
        price.setDisable(false);
        warranty.setDisable(false);
        totalCost.setDisable(false);
        editButton.setDisable(true);
        saveButton.setDisable(false);
    }

    public void saveServerChanges() {
        // Obtener los valores actualizados de los campos

        // Crear una conexión a la base de datos
        Connection con = ConnectToDB.connectToDB();

        if (con == null) {
            // Manejar la conexión nula o errores de conexión
            System.out.println("Error al conectar a la base de datos.");
            return;
        }

        // Construir la sentencia SQL UPDATE
        String updateQuery = "UPDATE Departments SET Name = ?, Email = ?, Phone = ?, ID_DepType = ? WHERE ID = ?";

        try {
            // Crear un PreparedStatement para ejecutar la sentencia UPDATE
            PreparedStatement pstmt = con.prepareStatement(updateQuery);

            // Establecer los valores de los parámetros de la sentencia UPDATE
            pstmt.setString(1, name.getText());
            pstmt.setString(1, description.getText());
            pstmt.setString(2, price.getText());
            pstmt.setString(3, warranty.getText());
            pstmt.setString(4, totalCost.getText());
            pstmt.setLong(4, DepartmentSingleton.getInstance().getDepartment().getID());


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
            name.setDisable(true);
            description.setDisable(true);
            price.setDisable(true);
            warranty.setDisable(true);
            totalCost.setDisable(true);
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatServidores.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de servidores");
        principalStage.setScene(scene);
        principalStage.setResizable(false);principalStage.setResizable(false);
        principalStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Server server = ServerSingleton.getInstance().getServer();

        name.setText(server.getServer());
        description.setText(server.getDescription());
        price.setText(server.getPrice());
        warranty.setText(String.valueOf(server.getWarranty()));
        totalCost.setText(server.getTotalCost());

        // Deshabilitar la edición de campos
        name.setDisable(true);
        description.setDisable(true);
        price.setDisable(true);
        warranty.setDisable(true);
        totalCost.setDisable(true);

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

            try {
                Connection con = ConnectToDB.connectToDB();

                String query = "UPDATE Incidents SET ID_Department = NULL WHERE ID_Department = ?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setLong(1, DepartmentSingleton.getInstance().getDepartment().getID());
                preparedStatement.execute();

                query = "UPDATE Users SET ID_Department = NULL WHERE ID_Department = ?";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setLong(1, DepartmentSingleton.getInstance().getDepartment().getID());
                preparedStatement.execute();

                query = "DELETE FROM Departments WHERE ID = ?";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setLong(1, DepartmentSingleton.getInstance().getDepartment().getID());
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
