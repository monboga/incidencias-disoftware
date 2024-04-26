package src.javasqlriskmanager.controllers.userscontrollers;

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
import src.javasqlriskmanager.models.Usuario;
import src.javasqlriskmanager.singletons.UserSingleton;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class CatUsuariosController implements Initializable {

    @FXML
    TableView<Usuario> tbl_Usuarios;

    @FXML
    private TableColumn<Usuario, Long> col_id;
    @FXML
    private TableColumn<Usuario, String> col_nombre;
    @FXML
    private TableColumn<Usuario, String> col_email;
    @FXML
    private TableColumn<Usuario, String> col_posicion;
    @FXML
    private TableColumn<Usuario, Long> col_rol;
    @FXML
    private TableColumn<Usuario, Long> col_departamento;
    @FXML
    private TableColumn<Usuario, String> col_pass;

    UserSingleton userSingleton;

    @FXML
    void setUsuariosList()  {

        String getQuery = "SELECT * FROM Users";
        ObservableList<Usuario> usuarioList = FXCollections.observableArrayList();

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(getQuery);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long ID = rs.getLong("ID");
                String Name = rs.getString("Name");
                String Email = rs.getString("Email");
                String Position = rs.getString("Position");
                Long ID_Role = rs.getLong("ID_Role");
                Long ID_Department = rs.getLong("ID_Department");
                String Password = rs.getString("Password");
                Usuario usuario = new Usuario(ID,Name,Email, Position,ID_Role,ID_Department, Password);
                if(usuario!=null)
                    usuarioList.add(usuario);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
                e.printStackTrace();
        }

        tbl_Usuarios.setItems(usuarioList);

        tbl_Usuarios.setRowFactory( tv -> {
            TableRow<Usuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Usuario rowData = row.getItem();

                    userSingleton = UserSingleton.getInstance();
                    userSingleton.setUsuario(rowData);

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
        col_id.setCellValueFactory(new PropertyValueFactory<Usuario, Long>("ID")); //Nombre segun como se llama en el model
        col_nombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Name")); //Nombre segun como se llama en el model
        col_email.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Email")); //Nombre segun como se llama en el model
        col_posicion.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Position")); //Nombre segun como se llama en el model
        col_rol.setCellValueFactory(new PropertyValueFactory<Usuario, Long>("ID_Role")); //Nombre segun como se llama en el model
        col_departamento.setCellValueFactory(new PropertyValueFactory<Usuario, Long>("ID_Department")); //Nombre segun como se llama en el model
        col_pass.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Password")); //Nombre segun como se llama en el model
        setUsuariosList();
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DetalleUsuario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Detalle de Usuario");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }
    @FXML
    protected void creaUser() throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-users.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Nuevo Usuario");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

}
