
package gestionentrepot.utils;

import java.sql.*;

public class MyConnection {

    

    String url="jdbc:mysql://localhost:3306/db_khedemti";
    String login="root";
    String pwd="";
    Connection cnx;
    public static MyConnection instance;
    
    public MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // methode static 
    
    
    
      public Connection getCnx() {
        return cnx;
    }
    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }
    
}