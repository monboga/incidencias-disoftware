package src.javasqlriskmanager.controllers.rolcontrollers;

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
import src.javasqlriskmanager.models.Rol;
import src.javasqlriskmanager.singletons.RolSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class CatRolesController implements Initializable {

    @FXML
    TableView<Rol> tbl_Roles;

    @FXML
    private TableColumn<Rol, Long> col_id;
    @FXML
    private TableColumn<Rol, String> col_nombre;

    RolSingleton rolSingleton;

    @FXML
    void setRolList()  {

        String getQuery = "SELECT * FROM User_Roles";
        ObservableList<Rol> rolList = FXCollections.observableArrayList();

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(getQuery);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String Name = rs.getString("Name");
                Long id = rs.getLong("ID");
                Rol rol = new Rol(id, Name);
                if(rol!=null)
                    rolList.add(rol);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        tbl_Roles.setItems(rolList);

        tbl_Roles.setRowFactory( tv -> {
            TableRow<Rol> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Rol rowData = row.getItem();
                    rolSingleton = RolSingleton.getInstance();
                    rolSingleton.setRol(rowData);
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
        col_id.setCellValueFactory(new PropertyValueFactory<Rol, Long>("ID")); //Nombre segun como se llama en el model
        col_nombre.setCellValueFactory(new PropertyValueFactory<Rol, String>("Name"));
        setRolList();
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DetalleRol.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Detalle de Rol");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }
    @FXML
    protected void creaRol() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-roles.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Nuevo Rol");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

}
