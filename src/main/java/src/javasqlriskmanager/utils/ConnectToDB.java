package src.javasqlriskmanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectToDB {

    public static Connection connectToDB(){

        Dotenv dotenv = Dotenv.load();

        // Acceder a las variables de entorno
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            //connection = DriverManager.getConnection(url);
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        return connection;
    }
}
