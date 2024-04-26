module src.javasqlriskmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.dotenv;
    requires annotations;

    opens src.javasqlriskmanager to javafx.fxml;
    exports src.javasqlriskmanager;
    exports src.javasqlriskmanager.controllers;
    opens src.javasqlriskmanager.controllers to javafx.fxml;

    opens src.javasqlriskmanager.models to javafx.base;
    exports src.javasqlriskmanager.controllers.incidentcontrollers;
    opens src.javasqlriskmanager.controllers.incidentcontrollers to javafx.fxml;
    exports src.javasqlriskmanager.controllers.rolcontrollers;
    opens src.javasqlriskmanager.controllers.rolcontrollers to javafx.fxml;
    exports src.javasqlriskmanager.controllers.departmentcontrollers;
    opens src.javasqlriskmanager.controllers.departmentcontrollers to javafx.fxml;
    exports src.javasqlriskmanager.controllers.userscontrollers;
    opens src.javasqlriskmanager.controllers.userscontrollers to javafx.fxml;
}