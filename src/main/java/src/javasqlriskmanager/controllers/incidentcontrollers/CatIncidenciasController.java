package src.javasqlriskmanager.controllers.incidentcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.controllers.LoginController;
import src.javasqlriskmanager.models.Incident;
import src.javasqlriskmanager.singletons.IncidentSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;
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
    public Boolean empleado = false;

    @FXML
    TableView<Incident> tbl_incidencias;

    @FXML
    private TableColumn<Incident, Long> col_id; //variable de columna del id
    @FXML
    private TableColumn<Incident, String> col_Title; //variable de columna del titlo
    @FXML
    private TableColumn<Incident, String> col_Description;
    @FXML
    private TableColumn<Incident, LocalDate> col_Created;
    @FXML
    private TableColumn<Incident, LocalDate> col_Update;

    //nuevas columnas FXML agregadas para Costo y Garantia
    @FXML
    private TableColumn<Incident, Float> col_Cost;
    @FXML
    private TableColumn<Incident, Long> col_Warrantly;

    @FXML
    private TableColumn<Incident, String> col_Status;
    @FXML
    private TableColumn<Incident, String> col_Severity; //variable de columna severidad uso??
    @FXML
    private TableColumn<Incident, String> col_Department; //variable de columna departamento  uso??

    @FXML
    private Button btnBack;

    IncidentSingleton incidentSingleton;

    @FXML
    void setIncidentList()  {

        String getQuery = "SELECT i.ID, i.Title, i.Description, i.CreatedAt, i.UpdateDate, (ser.Price * 0.15) AS Cost, i.Warrantly AS Warrantly, ist.Name AS IncStatusName, st.Name AS Status, d.Name AS DepartmentName\n" +
                "FROM Incidents i \n" +
                "LEFT JOIN Departments d ON i.ID_Department = d.ID\n" +
                "LEFT JOIN Incident_Severity_Types st ON i.ID_severity = st.ID\n" +
                "LEFT JOIN Incident_Status ist ON i.ID_Status = ist.ID\n" +
                "LEFT JOIN Servers ser ON i.ID_Servers = ser.ID;";
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


                String cost = rs.getString("Cost");
//                System.out.println("cost = " + cost);
                Long warrantly = rs.getLong("Warrantly");
//                System.out.println("warrantly = " + warrantly);


                String name_status = rs.getString("IncStatusName");
                String name_severity = rs.getString("Status");
                String name_department = rs.getString("DepartmentName");
                Incident incident = new Incident(title,id,description,createdAt,updateDate,name_status, name_severity, name_department, cost, warrantly);
                if(incident!=null)
                    incidentList.add(incident);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        tbl_incidencias.setItems(incidentList);

        tbl_incidencias.setRowFactory( tv -> {
            TableRow<Incident> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Incident rowData = row.getItem();
                    incidentSingleton = IncidentSingleton.getInstance();
                    incidentSingleton.setIncident(rowData);
                    //System.out.println(rowData.toString());

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

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_id.setCellValueFactory(new PropertyValueFactory<Incident, Long>("id")); //Nombre segun como se llama en el model
        col_Title.setCellValueFactory(new PropertyValueFactory<Incident, String>("title"));
        col_Description.setCellValueFactory(new PropertyValueFactory<Incident, String>("description"));
        col_Created.setCellValueFactory(new PropertyValueFactory<Incident, LocalDate>("createdAt"));
        col_Update.setCellValueFactory(new PropertyValueFactory<Incident, LocalDate>("updateDate"));
        col_Cost.setCellValueFactory(new PropertyValueFactory<Incident, Float>("id_server"));
        col_Warrantly.setCellValueFactory(new PropertyValueFactory<Incident, Long>("warrantly"));
        col_Status.setCellValueFactory(new PropertyValueFactory<Incident, String>("id_status"));
        col_Severity.setCellValueFactory(new PropertyValueFactory<Incident, String>("id_severity"));
        col_Department.setCellValueFactory(new PropertyValueFactory<Incident, String>("id_department"));
        setIncidentList();

        if(LoginController.sesionSingleton.isAdmin())
            btnBack.setText("Atrás");
        else
            btnBack.setText("Salir");
    }

    public void irMenuPrincipal() throws IOException {

        if(LoginController.sesionSingleton.isAdmin()){
            principalStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            principalStage.setTitle("Menú Principal");
            principalStage.setScene(scene);
            principalStage.setResizable(false);
            principalStage.show();
        } else{
            principalStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            principalStage.setTitle("Inicio de sesión");
            principalStage.setScene(scene);
            principalStage.setResizable(false);
            principalStage.show();
        }
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
