package ch.project.library.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBConnector {
    public DBConnector(){
    }

    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/library_management", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
