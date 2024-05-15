package src.javasqlriskmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.models.Usuario;
import src.javasqlriskmanager.models.Rol;

import java.io.IOException;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class CatUsuariosController {

    @FXML
    private TableView<Usuario> tbl_Usuarios;
    @FXML
    private TableColumn<Usuario, Long> col_id;
    @FXML
    private TableColumn<Usuario, String> col_nombre;
    @FXML
    private TableColumn<Usuario, String> col_email;
    @FXML
    private TableColumn<Usuario, String> col_posicion;
    @FXML
    private TableColumn<Usuario, Rol> col_rol;
    @FXML
    private TableColumn<Usuario, Long> col_departamento;
    @FXML
    private TableColumn<Usuario, String> col_pass;

    @FXML
    public void initialize() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col_posicion.setCellValueFactory(new PropertyValueFactory<>("Position"));
        col_rol.setCellValueFactory(new PropertyValueFactory<>("Role"));
        col_departamento.setCellValueFactory(new PropertyValueFactory<>("ID_Department"));
        col_pass.setCellValueFactory(new PropertyValueFactory<>("Password"));

        // Aquí puedes añadir datos de prueba o cargar datos reales en la tabla
        tbl_Usuarios.setItems(getUsuarios());
    }

    private ObservableList<Usuario> getUsuarios() {
        // Simulación de datos
        Rol administrador = new Rol(1L, "Administrador");
        Rol empleado = new Rol(2L, "Empleado");
        Rol gerente = new Rol(3L, "Gerente");

        return FXCollections.observableArrayList(
                new Usuario(1L, "John Doe", "john@example.com", "Developer", administrador.getID(), 1L, "password123"),
                new Usuario(2L, "Jane Smith", "jane@example.com", "Analyst", empleado.getID(), 2L, "password456"),
                new Usuario(3L, "Alice Johnson", "alice@example.com", "Manager", gerente.getID(), 3L, "password789")
        );
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
}
