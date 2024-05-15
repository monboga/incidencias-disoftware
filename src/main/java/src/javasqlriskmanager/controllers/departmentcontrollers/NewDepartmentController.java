package src.javasqlriskmanager.controllers.departmentcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import src.javasqlriskmanager.MainApplication;
import src.javasqlriskmanager.utils.ConnectToDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static src.javasqlriskmanager.MainApplication.principalStage;

public class NewDepartmentController {

    @FXML
    TextField name;

    @FXML
    TextField email;

    @FXML
    TextField phone;

    @FXML
    public void initialize(){

    }

//    public void loadDepTypes(){
//        ObservableList<String> departamentTypes = FXCollections.observableArrayList();
//        String selectQuery = "SELECT * FROM Department_Types";
//
//        try {
//            Connection con = ConnectToDB.connectToDB();
//            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                departamentTypes.add(resultSet.getString("Name"));
//                departamentTypesMap.put(resultSet.getString("Name"),resultSet.getLong("ID"));
//            }
//
//            con.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//                e.printStackTrace();
//        }
//
//        depType.setItems(departamentTypes);
//        if(departamentTypes.size()>0)
//            depType.setValue(departamentTypes.get(0));
//    }

    public void createDepartment() throws IOException {
        String insertQuery = "INSERT INTO Departments " +
                "(Name, Email, Phone)" +
                " VALUES (?, ?, ?)";
        try {
            Connection con = ConnectToDB.connectToDB();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, name.getText());
            preparedStatement.setString(2, email.getText());
            preparedStatement.setString(3, phone.getText());
            preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
                e.printStackTrace();
        }

        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatDepartamentos.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de departamentos");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();

    }

    public void irAtras(ActionEvent actionEvent) throws IOException {
        principalStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CatDepartamentos.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        principalStage.setTitle("Catálogo de departamentos");
        principalStage.setScene(scene);
        principalStage.setResizable(false);
        principalStage.show();
    }

}
