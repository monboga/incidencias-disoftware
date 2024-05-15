package src.javasqlriskmanager.controllers.serverscontrollers;

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
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Department;
import src.javasqlriskmanager.singletons.DepartmentSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class CatServersController implements Initializable {

    @FXML
    TableView<Department> tbl_Server;

    @FXML
    private TableColumn<Department, Long> col_id;
    @FXML
    private TableColumn<Department, String> col_servidor;
    @FXML
    private TableColumn<Department, String> col_descripcion;
    @FXML
    private TableColumn<Department, String> col_precio;
    @FXML
    private TableColumn<Department, Long> col_garantia;

    DepartmentSingleton departmentSingleton;

    @FXML
    void setDepartmentList()  {

        String getQuery = "SELECT * FROM Departments";
        ObservableList<Department> departmentList = FXCollections.observableArrayList();

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(getQuery);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Long ID = rs.getLong("ID");
                String Name = rs.getString("Name");
                String Email = rs.getString("Email");
                String Phone = rs.getString("Phone");
                Long ID_DepType = rs.getLong("ID_DepType");
                Department department = new Department(ID, Name, Email, Phone,ID_DepType);
                if(department!=null)
                    departmentList.add(department);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        tbl_Server.setItems(departmentList);

        tbl_Server.setRowFactory( tv -> {
            TableRow<Department> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Department rowData = row.getItem();
                    departmentSingleton = DepartmentSingleton.getInstance();
                    departmentSingleton.setDepartment(rowData);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_id.setCellValueFactory(new PropertyValueFactory<Department, Long>("ID")); //Nombre segun como se llama en el model
        col_servidor.setCellValueFactory(new PropertyValueFactory<Department, String>("Name"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<Department, String>("Email"));
        col_precio.setCellValueFactory(new PropertyValueFactory<Department, String>("Phone"));
        col_garantia.setCellValueFactory(new PropertyValueFactory<Department, Long>("ID_DepType"));
        setDepartmentList();
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DetalleServidor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Detalle de Servidor");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    @FXML
    protected void creaServer() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-servers.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Nueva Server");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }
}