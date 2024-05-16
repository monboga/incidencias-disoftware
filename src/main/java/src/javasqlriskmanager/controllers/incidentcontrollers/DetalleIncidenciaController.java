package src.javasqlriskmanager.controllers.incidentcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    TextField updateDate;

    @FXML
    Button editButton;

    @FXML
    Button saveButton;



    @FXML
    ChoiceBox<String> listStatus;

    @FXML
    ChoiceBox<String> listDepartment;

    @FXML
    ChoiceBox<String> listSeverity;

    Map<String,Long> severitiesMap = new HashMap<>();
    Map<String,Long> departamentsMap = new HashMap<>();
    Map<String,Long> statusMap = new HashMap<>();


    public void editarUsuario() {
        // Habilitar la edición de campos
        title.setDisable(false);
        description.setDisable(false);
        listStatus.setDisable(false);
        createdDate.setDisable(false);
        listDepartment.setDisable(false);
        listSeverity.setDisable(false);
        updateDate.setDisable(false);

        // Deshabilitar el botón "Editar" y habilitar el botón "Guardar"
        editButton.setDisable(true);
        saveButton.setDisable(false);
    }

    public void guardarCambios() {
        // Obtener los valores actualizados de los campos
        // obtiene la fecha y hora actual en LocalDateTime
        Incident incident = IncidentSingleton.getInstance().getIncident();
        incident.setId((long) Integer.parseInt(idNumber.getText()));
        String usertitle = title.getText();
        String userdescription = description.getText();
        String usercreatedDate = createdDate.getText();
        String userUpdateDate = updateDate.getText();

        //almaceno los datos de la lista desplegable en la base de datos
        String userStatus = String.valueOf(statusMap.get(listStatus.getValue()));
        String userSeverity = String.valueOf(severitiesMap.get(listSeverity.getValue()));
        String userDepartament = String.valueOf(departamentsMap.get(listDepartment.getValue()));

        // Crear una conexión a la base de datos
        Connection con = ConnectToDB.connectToDB();

        if (con == null) {
            // Manejar la conexión nula o errores de conexión
            System.out.println("Error al conectar a la base de datos.");
            return;
        }

        // Construir la sentencia SQL UPDATE
        String updateQuery = "UPDATE Incidents SET Title = ?, Description = ?, CreatedAt = ?, UpdateDate = ?, ID_Status = ?, ID_severity = ?, ID_Department = ? WHERE ID = ?";

        try {
            // Crear un PreparedStatement para ejecutar la sentencia UPDATE
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, usertitle);
            pstmt.setString(2, userdescription);



            pstmt.setString(3, usercreatedDate);
            pstmt.setObject(4, userUpdateDate);

            pstmt.setString(5, userStatus);
            pstmt.setString(6, userSeverity);
            pstmt.setString(7, userDepartament);
            pstmt.setLong(8, incident.getId());


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
            listStatus.setDisable(true);
            createdDate.setDisable(true);
            listDepartment.setDisable(true);
            listSeverity.setDisable(true);
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
        description.setText(incident.getDescription());
        createdDate.setText(incident.getCreatedAt().toString());
        updateDate.setText(incident.getUpdateDate().toString());

        // Load incident severities from the database and populate the ChoiceBox
        loadStatus();
        loadIncidentDepartaments();
        loadIncidentSeverities();

        title.setDisable(true);
        description.setDisable(true);
        idNumber.setDisable(true);
        listStatus.setDisable(true);
        createdDate.setDisable(true);
        listDepartment.setDisable(true);
        listSeverity.setDisable(true);
        updateDate.setDisable(true);

        // Habilitar el botón "Editar"
        editButton.setDisable(false);
        saveButton.setDisable(true);

    }

    private void loadStatus() {
        ObservableList<String> status = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM Incident_Status";

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                status.add(resultSet.getString("Name"));
                statusMap.put(resultSet.getString("Name"),resultSet.getLong("ID"));
            }

            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        listStatus.setItems(status);
        if (status.size() > 0) {
            listStatus.getSelectionModel().select(0);
        }
    }

    private void loadIncidentDepartaments() {
        ObservableList<String> departaments = FXCollections.observableArrayList();

        String selectQuery = "SELECT * FROM Departments";

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                departaments.add(resultSet.getString("Name"));
                departamentsMap.put(resultSet.getString("Name"),resultSet.getLong("ID"));
            }

            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        listDepartment.setItems(departaments);
        if(departaments.size()>0)
            listDepartment.setValue(departaments.get(0));
    }

    private void loadIncidentSeverities() {
        ObservableList<String> severities = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM Incident_Severity_Types";

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                severities.add(resultSet.getString("Name"));
                severitiesMap.put(resultSet.getString("Name"),resultSet.getLong("ID"));
            }

            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        listSeverity.setItems(severities);
        if(severities.size()>0)
            listSeverity.setValue(severities.get(0));
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
