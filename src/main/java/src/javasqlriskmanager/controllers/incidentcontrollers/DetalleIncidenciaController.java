package src.javasqlriskmanager.controllers.incidentcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;;
import javafx.scene.control.*;
import org.w3c.dom.Text;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Department;
import src.javasqlriskmanager.models.Incident;
import src.javasqlriskmanager.singletons.DepartmentSingleton;
import src.javasqlriskmanager.singletons.IncidentSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class DetalleIncidenciaController implements Initializable {

    @FXML
    Label incidentDetail;

    @FXML
    TextField title;

    @FXML
    TextField idNumber;

    @FXML
    TextField status;

    @FXML
    TextArea description;

    @FXML
    TextField createdDate;

    @FXML
    TextField departament;

    @FXML
    TextField severity;

    @FXML
    TextField assignedUser;

    @FXML
    TextField creatorUser;

    @FXML
    TextField updateDate;

    @FXML
    Button editButton;

    @FXML
    Button saveButton;


    public void editarUsuario() {
        // Habilitar la edición de campos
        title.setDisable(false);
        description.setDisable(false);
        status.setDisable(false);
        createdDate.setDisable(false);
        departament.setDisable(false);
        severity.setDisable(false);
        assignedUser.setDisable(false);
        creatorUser.setDisable(false);
        updateDate.setDisable(false);

        // Deshabilitar el botón "Editar" y habilitar el botón "Guardar"
        editButton.setDisable(true);
        saveButton.setDisable(false);
    }

    public void guardarCambios() {
        // Obtener los valores actualizados de los campos
        Incident incident = IncidentSingleton.getInstance().getIncident();
        incident.setId((long) Integer.parseInt(idNumber.getText()));
        String usertitle = title.getText();
        String userdescription = description.getText();
        String usercreatedDate = createdDate.getText();
        String userUpdateDate = updateDate.getText();
        long userStatus = Long.parseLong(status.getText());
        long userSeverity = Long.parseLong(severity.getText());
        long userCreatorUser = Long.parseLong(creatorUser.getText());
        long userAssignedUser = Long.parseLong(assignedUser.getText());
        long userDepartament = Long.parseLong(departament.getText());

        // Crear una conexión a la base de datos
        Connection con = ConnectToDB.connectToDB();

        if (con == null) {
            // Manejar la conexión nula o errores de conexión
            System.out.println("Error al conectar a la base de datos.");
            return;
        }

        // Construir la sentencia SQL UPDATE
        String updateQuery = "UPDATE Incidents SET Title = ?, Description = ?, CreatedAt = ?, UpdateDate = ?, ID_Status = ?, ID_severity = ?, ID_CreatorUser = ?, ID_AssignedUser = ?, ID_Department = ? WHERE ID = ?";

        try {
            // Crear un PreparedStatement para ejecutar la sentencia UPDATE
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, usertitle);
            pstmt.setString(2, userdescription);
            pstmt.setString(3, usercreatedDate);
            pstmt.setString(4, userUpdateDate);
            pstmt.setString(5, String.valueOf(userStatus));
            pstmt.setString(6, String.valueOf(userSeverity));
            pstmt.setString(7, String.valueOf(userCreatorUser));
            pstmt.setString(8, String.valueOf(userAssignedUser));
            pstmt.setString(9, String.valueOf(userDepartament));
            pstmt.setLong(10, incident.getId());


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
            title.setDisable(true);
            description.setDisable(true);
            idNumber.setDisable(true);
            status.setDisable(true);
            createdDate.setDisable(true);
            departament.setDisable(true);
            severity.setDisable(true);
            assignedUser.setDisable(true);
            creatorUser.setDisable(true);
            updateDate.setDisable(true);

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatIncidencias.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de incidencias");
        principalStage.setScene(scene);
        principalStage.setResizable(false);principalStage.setResizable(false);
        principalStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Incident incident = IncidentSingleton.getInstance().getIncident();
        incidentDetail.setText("Detalle de incidencia #" + incident.getId());
        title.setText(incident.getTitle());
        idNumber.setText(incident.getId().toString());
        status.setText(incident.getId_status().toString());
        description.setText(incident.getDescription());
        createdDate.setText(incident.getCreatedAt().toString());
        departament.setText(incident.getId_department().toString());
        severity.setText(incident.getId_severity().toString());
        assignedUser.setText(incident.getId_assignedUser().toString());
        creatorUser.setText(incident.getId_creatorUser().toString());
        updateDate.setText(incident.getUpdateDate().toString());

        title.setDisable(true);
        description.setDisable(true);
        idNumber.setDisable(true);
        status.setDisable(true);
        createdDate.setDisable(true);
        departament.setDisable(true);
        severity.setDisable(true);
        assignedUser.setDisable(true);
        creatorUser.setDisable(true);
        updateDate.setDisable(true);

        // Habilitar el botón "Editar"
        editButton.setDisable(false);
        saveButton.setDisable(true);

    }

    public void delete(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de eliminar.");
        alert.setContentText("¿Desea confirmar proceder con la eliminación del registro?");
        alert.showAndWait();

        if(alert.getResult().getText().equals("Aceptar")) {
            String selectQuery = "DELETE FROM Incidents WHERE ID = ?";

            try {
                Connection con = ConnectToDB.connectToDB();
                PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
                preparedStatement.setLong(1,IncidentSingleton.getInstance().getIncident().getId());
                preparedStatement.execute();

                con.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

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
