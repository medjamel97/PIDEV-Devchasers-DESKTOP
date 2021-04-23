package app.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnecteurBD {

    static Connection connection = null;
    static ResultSet resultSet = null;
    static Statement statement = null;

    public static void main(String[] args) {
        connection = driverBD();
    }

    public static Connection driverBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");   //protocole appel le packetge du driver du jdbc mysql
            String url = "jdbc:mysql://localhost:3306/db_khedemti";  //nom base.port.jdbc driver
            Connection connexion = DriverManager.getConnection(url, "root", "");
            return connexion;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors du chargement du pilote :" + e.getMessage());
            return null;
        }
    }
}
