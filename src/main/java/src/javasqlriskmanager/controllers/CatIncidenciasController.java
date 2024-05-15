package src.javasqlriskmanager.controllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Incident;
import src.javasqlriskmanager.utils.ConnectToDB;
import src.javasqlriskmanager.utils.IncidentDTO;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class CatIncidenciasController implements Initializable {

    private Parent root;

    @FXML
    TableView<Incident> tbl_incidencias;

    @FXML
    private TableColumn<Incident, Long> col_id;
    @FXML
    private TableColumn<Incident, String> col_Title;
    @FXML
    private TableColumn<Incident, String> col_Description;
    @FXML
    private TableColumn<Incident, LocalDate> col_Created;
    @FXML
    private TableColumn<Incident, LocalDate> col_Update;
    @FXML
    private TableColumn<Incident, String> col_Status;
    @FXML
    private TableColumn<Incident, String> col_Severity;
    @FXML
    private TableColumn<Incident, String> col_Department;

    @FXML
    void setIncidentList()  {

        String getQuery = "SELECT i.ID, i.Title, i.Description, i.CreatedAt, i.UpdateDate, ist.Name, st.Name, d.Name\n" +
                "FROM Incidents i \n" +
                "LEFT JOIN Departments d ON i.ID_Department = d.ID\n" +
                "LEFT JOIN Incident_Severity_Types st ON i.ID_severity = st.ID\n" +
                "LEFT JOIN Incident_Status ist ON i.ID_Status = ist.ID;";
        ObservableList<Incident> incidentList = FXCollections.observableArrayList();

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(getQuery);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("Title");
                Long id = rs.getLong("ID");
                String description = rs.getString("Description");
                LocalDate createdAt = rs.getDate("CreatedAt").toLocalDate();
                LocalDate updateDate = rs.getDate("UpdateDate").toLocalDate();
                String name_status = rs.getString("ist.Name");
                String name_severity = rs.getString("st.Name");
                String name_department = rs.getString("d.Name");
                Incident incident = new Incident(title,id,description,createdAt,updateDate,name_status,name_severity,name_department);
                if(incident!=null)
                    incidentList.add(incident);
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tbl_incidencias.setItems(incidentList);

        tbl_incidencias.setRowFactory( tv -> {
            TableRow<Incident> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Incident rowData = row.getItem();
                    System.out.println(rowData.toString());

                    try {
                        irDetalle();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });

    }

    @FXML
    protected void creaIncident() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-incident.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Nueva Incidencia");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_id.setCellValueFactory(new PropertyValueFactory<Incident, Long>("id")); //Nombre segun como se llama en el model
        col_Title.setCellValueFactory(new PropertyValueFactory<Incident, String>("title"));
        col_Description.setCellValueFactory(new PropertyValueFactory<Incident, String>("description"));
        col_Created.setCellValueFactory(new PropertyValueFactory<Incident, LocalDate>("createdAt"));
        col_Update.setCellValueFactory(new PropertyValueFactory<Incident, LocalDate>("updateDate"));
        col_Status.setCellValueFactory(new PropertyValueFactory<Incident, String>("id_status"));
        col_Severity.setCellValueFactory(new PropertyValueFactory<Incident, String>("id_severity"));
        col_Department.setCellValueFactory(new PropertyValueFactory<Incident, String>("id_department"));
        setIncidentList();
    }

    public void irMenuPrincipal() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Menú Principal");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }



    public void irDetalle() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DetalleIncidencia.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Detalle de incidencia");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }
}
