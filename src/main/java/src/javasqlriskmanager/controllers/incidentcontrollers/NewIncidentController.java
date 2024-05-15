package src.javasqlriskmanager.controllers.incidentcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.controllers.LoginController;
import src.javasqlriskmanager.singletons.SesionSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class NewIncidentController {

    @FXML
    TextField id_incidentTitle;

    @FXML
    TextArea id_incidentDesc;

    @FXML
    ChoiceBox<String> ListSeveridad;

    Map<String,Long> severitiesMap = new HashMap<>();
    Map<String,Long> departamentsMap = new HashMap<>();
    Map<String,Long> statusMap = new HashMap<>();

    @FXML
    protected void initialize() {
        // Load incident severities from the database and populate the ChoiceBox
        loadIncidentSeverities();
        loadIncidentDepartaments();
        loadStatus();
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

        ListSeveridad.setItems(severities);
        if(severities.size()>0)
            ListSeveridad.setValue(severities.get(0));
    }

    @FXML
    ChoiceBox<String> ListDep;

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

        ListDep.setItems(departaments);
        if(departaments.size()>0)
            ListDep.setValue(departaments.get(0));
    }

    private void loadStatus() {
        String selectQuery = "SELECT * FROM Incident_Status";

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                statusMap.put(resultSet.getString("Name"),resultSet.getLong("ID"));
            }

            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
                e.printStackTrace();
        }
    }

    @FXML
    protected void createIncident() throws IOException {

        String insertQuery = "INSERT INTO Incidents " +
                "(Title, Description, CreatedAt, UpdateDate, ID_Status, ID_Severity, ID_Department)" +
                " VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, id_incidentTitle.getText());
            preparedStatement.setString(2,id_incidentDesc.getText());
            preparedStatement.setDate(3,new Date(System.currentTimeMillis()));
            preparedStatement.setDate(4,new Date(System.currentTimeMillis()));
            preparedStatement.setLong(5,statusMap.get("ABIERTA"));
            preparedStatement.setLong(6,severitiesMap.get(ListSeveridad.getValue()));
            preparedStatement.setLong(7,departamentsMap.get(ListDep.getValue()));
            preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
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

    public void irAtras(ActionEvent actionEvent) throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatIncidencias.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de incidencias");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }
}
