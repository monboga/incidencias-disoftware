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
import src.javasqlriskmanager.models.Server;
import src.javasqlriskmanager.singletons.ServerSingleton;
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
    TableView<Server> tbl_Server;

    @FXML
    private TableColumn<Server, Long> col_id;
    @FXML
    private TableColumn<Server, String> col_servidor;
    @FXML
    private TableColumn<Server, String> col_descripcion;
    @FXML
    private TableColumn<Server, String> col_precio;
    @FXML
    private TableColumn<Server, Long> col_garantia;

    @FXML
    private TableColumn<Server, Long> col_costoTotal;

    ServerSingleton serverSingleton;

    @FXML
    void setServerList() {

        String getQuery = "SELECT * FROM Servers";
        ObservableList<Server> serverList = FXCollections.observableArrayList();

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(getQuery);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Long ID = rs.getLong("ID");
                String Server = rs.getString("Server");
                String Description = rs.getString("Description");
                String Price = rs.getString("Price");
                String warranty = rs.getString("Warranty");
                String TotalCost = rs.getString("TotalCost");
                Server server = new Server (ID, Server, Description, Price, Long.parseLong(warranty), TotalCost);
                    serverList.add(server);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        tbl_Server.setItems(serverList);

        tbl_Server.setRowFactory(tv -> {
            TableRow<Server> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Server rowData = row.getItem();
                    serverSingleton = ServerSingleton.getInstance();
                    serverSingleton.setServer(rowData);
                    // System.out.println(rowData.toString());

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
        col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_servidor.setCellValueFactory(new PropertyValueFactory<>("Server"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("Description"));
        col_precio.setCellValueFactory(new PropertyValueFactory<>("Price"));
        col_garantia.setCellValueFactory(new PropertyValueFactory<>("Warranty"));
        col_costoTotal.setCellValueFactory(new PropertyValueFactory<>("TotalCost"));
        setServerList();
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
        principalStage.setTitle("Nuevo Server");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }
}