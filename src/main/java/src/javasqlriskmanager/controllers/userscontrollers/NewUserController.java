package src.javasqlriskmanager.controllers.userscontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class NewUserController {

    @FXML
    TextField name;

    @FXML
    TextField email;

    @FXML
    TextField password;

    @FXML
    TextField posicion;

    @FXML
    ChoiceBox<String> listRoles;

    @FXML
    ChoiceBox<String> listDep;

    Map<String,Long> rolesMap = new HashMap<>();
    Map<String,Long> departamentsMap = new HashMap<>();

    @FXML
    protected void initialize() {
        loadDepartaments();
        loadRoles();
    }

    private void loadDepartaments() {
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

        listDep.setItems(departaments);
        if(departaments.size()>0)
            listDep.setValue(departaments.get(0));
    }

    private void loadRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM User_Roles";

        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roles.add(resultSet.getString("Name"));
                rolesMap.put(resultSet.getString("Name"),resultSet.getLong("ID"));
            }

            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
                e.printStackTrace();
        }

        listRoles.setItems(roles);
        if(roles.size()>0)
            listRoles.setValue(roles.get(0));
    }
    public void irAtras(ActionEvent actionEvent) throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatUsuarios.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de usuarios");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

    public void createUser() throws IOException {
        String insertQuery = "INSERT INTO Users " +
                "(Name, Email, Position, ID_Role, ID_Department, Password)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, name.getText());
            preparedStatement.setString(2, email.getText());
            preparedStatement.setString(3, posicion.getText());
            preparedStatement.setLong(4, rolesMap.get(listRoles.getValue()));
            preparedStatement.setLong(5,departamentsMap.get(listDep.getValue()));
            preparedStatement.setString(6,password.getText());
            preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
                e.printStackTrace();
        }

        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatUsuarios.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de usuarios");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();

    }
}
