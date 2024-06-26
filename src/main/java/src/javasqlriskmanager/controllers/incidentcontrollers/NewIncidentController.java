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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class NewIncidentController {

    @FXML
    TextField id_incidentTitle;

    @FXML
    TextArea id_incidentDesc;

    //se agrego variable para acceder a la garantia
    @FXML
    TextField id_warrantly;

    //se agrego choicebox para la lista de servidores
    @FXML
    ChoiceBox<String> listServers;


    @FXML
    ChoiceBox<String> ListSeveridad;

    Map<String,Long> severitiesMap = new HashMap<>();
    Map<String,Long> departamentsMap = new HashMap<>();
    Map<String,Long> statusMap = new HashMap<>();

    //se agrego el mapeo para la lista de servidores
    Map<String, Long> serversMap = new HashMap<>();

    @FXML
    protected void initialize() {
        // Load incident severities from the database and populate the ChoiceBox
        loadIncidentSeverities();
        loadIncidentDepartaments();
        loadStatus();
        loadServers();
    }

    private void loadServers() {
        ObservableList<String> servers = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM Servers";

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                servers.add(resultSet.getString("Server"));
                serversMap.put(resultSet.getString("Server"),resultSet.getLong("ID"));
            }

            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        listServers.setItems(servers);
        if(servers.size()>0)
            listServers.setValue(servers.get(0));
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
        String selectedServer = listServers.getValue();

        Long selectedServerId = serversMap.get(selectedServer);

        String insertQuery = "INSERT INTO Incidents " +
                "(Title, Description, CreatedAt, UpdateDate, ID_Status, ID_Severity, ID_Department, ID_Servers, Warrantly)" +
                " VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, id_incidentTitle.getText());
            preparedStatement.setString(2,id_incidentDesc.getText());

            // obtiene la fecha y hora actual en LocalDateTime
            LocalDateTime currentDateTime = LocalDateTime.now();

            preparedStatement.setObject(3, java.sql.Timestamp.valueOf(currentDateTime)); // CreatedAt
            preparedStatement.setObject(4, java.sql.Timestamp.valueOf(currentDateTime)); // UpdateDate

            //preparedStatement.setDate(3,new Date(System.currentTimeMillis()));
            //preparedStatement.setDate(4,new Date(System.currentTimeMillis()));

            preparedStatement.setLong(5,statusMap.get("ABIERTA"));
            preparedStatement.setLong(6,severitiesMap.get(ListSeveridad.getValue()));
            preparedStatement.setLong(7,departamentsMap.get(ListDep.getValue()));

            //se agregan las columnas de nombre de servidor y de garantia en el INSERT;
            if (selectedServerId != null) {
                // El ID del servidor seleccionado es válido
                System.out.println("ID del servidor seleccionado: " + selectedServerId);

                // Asignar el ID del servidor al PreparedStatement
                preparedStatement.setLong(8, selectedServerId);
            } else {
                // Manejar el caso donde el nombre del servidor seleccionado no está en serversMap
                System.out.println("Nombre de servidor no encontrado en serversMap: " + selectedServer);
                // Puedes mostrar un mensaje de error al usuario o realizar otra acción apropiada
            }


            Long idWarrantly = Long.parseLong(id_warrantly.getText());
            preparedStatement.setLong(9, idWarrantly);
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
